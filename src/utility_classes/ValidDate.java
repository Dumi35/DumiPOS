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

public class ValidDate {

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

    public static String isBirthday() throws SQLException {
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
            ps=con.prepareStatement("SELECT * FROM staff");
            result = ps.executeQuery();
            
            while(result.next()){ //send email to each staff
                
            String body = "We have a couple of bday celebs with us today. Why don't you wish " + convertArrayListToString(birthdayPeople) + " a happy birthday. They'd definitely appreciate.";

            Message message = (Message) new MimeMessage(session);

            message.setFrom(new InternetAddress("dumebi328@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(result.getString("Email")));
            message.setSubject("Wish your colleagues a happy birthday!!");
            message.setText(body);

            Transport.send(message);
            }

        } catch (MessagingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        
    
    return convertArrayListToString(birthdayPeople);
}

}
