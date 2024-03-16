package utility_classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificationClass {

    public static String convertArrayListToString(ArrayList<String> array) {
        //convert the observable list to a string            
        // Use StringBuilder for efficient string concatenation
        StringBuilder result = new StringBuilder();

        // Append the first element
        result.append(array.get(0));

        // Iterate through the rest of the elements
        for (int i = 1; i < array.size(); i++) {
            // Add a separator (e.g., comma) before each element
            result.append(", ").append(array.get(i));
        }
        String newString = result.toString();
        return newString;
    }

    public static String isBirthday(String email) throws SQLException {
        Connection con = DatabaseConn.connectDB();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM staff WHERE Date_Of_Birth = '" + String.valueOf(LocalDate.now()) + "'");
        //PreparedStatement ps = con.prepareStatement("SELECT * FROM staff");
        ResultSet result = ps.executeQuery();
        Session session = EmailSending.emailSend();
        ArrayList<String> birthdayPeople = new ArrayList<String>();
        while (result.next()) {
            birthdayPeople.add(result.getString("Staff_Name"));
        }
        if (birthdayPeople.isEmpty()) { //if noone's bday is the current day
            return null;
        }
        //send email
        try {

            //send email to staff
            String body = "We have a couple of bday celebs with us today. Why don't you wish " + convertArrayListToString(birthdayPeople) + " a happy birthday. They'd definitely appreciate.";

            Message message = (Message) new MimeMessage(session);

            message.setFrom(new InternetAddress("dumebi328@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Wish your colleagues a happy birthday!!");
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }

        return convertArrayListToString(birthdayPeople);
    }

    static ArrayList<String> low_prev_stock = new ArrayList<String>();
    static ArrayList<String> exp_prev_stock = new ArrayList<String>();
    

    public static String isExpiryDate(String email) throws SQLException {
        Connection con = DatabaseConn.connectDB();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE Expiry_Date <= DATE_ADD(CURDATE(), INTERVAL 5 DAY)");

        ResultSet result = ps.executeQuery();
        Session session = EmailSending.emailSend();
        ArrayList<String> expiringSoon = new ArrayList<String>();
        while (result.next()) {
            expiringSoon.add(result.getString("Product_Name"));
        }
        
        if (expiringSoon.isEmpty()) {
            return null;
        }
        //send email
        try {
            //send email to staff

            String body = "These goods will expire soon " + convertArrayListToString(expiringSoon) + ".";

            Message message = (Message) new MimeMessage(session);

            message.setFrom(new InternetAddress("dumebi328@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Expiry Warning!!");
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }

        return convertArrayListToString(expiringSoon);
    }

    public static void InitialiseCounters() throws SQLException {
        Connection con = DatabaseConn.connectDB();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE Quantity <= '10'");

        ResultSet result = ps.executeQuery();

        while (result.next()) {
            low_prev_stock.add(result.getString("Product_Name"));
        }
        ps = con.prepareStatement("SELECT * FROM products WHERE Expiry_Date <= DATE_ADD(CURDATE(), INTERVAL 5 DAY)");

        result = ps.executeQuery();

        while (result.next()) {
            exp_prev_stock.add(result.getString("Product_Name"));
        }
    }

    //alerts for low stock levels
    public static String LowStockLevels(String email) throws SQLException {
        Connection con = DatabaseConn.connectDB();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE Quantity <= '10'");

        ResultSet result = ps.executeQuery();
        Session session = EmailSending.emailSend();

        ArrayList<String> lowStock = new ArrayList<String>();

        while (result.next()) {
            //low_counter++;
            lowStock.add(result.getString("Product_Name"));
        }

        if (low_prev_stock.equals(lowStock)) {
            return null;
        } else {
            //send email
            low_prev_stock = lowStock;
            try {
                //send email to only sales person and inventory manager

                String body = "Approaching Low Stock for " + convertArrayListToString(lowStock) + ".";

                Message message = (Message) new MimeMessage(session);

                message.setFrom(new InternetAddress("dumebi328@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Low Stock Warning!!");
                message.setText(body);

                Transport.send(message);

            } catch (MessagingException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText(e.toString());
                alert.showAndWait();
            }
        }

        if (lowStock.isEmpty()) {
            return null;
        }

        return convertArrayListToString(lowStock);
    }
}
