package utility_classes;

import components.alerts.AlertController;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import static javafx.application.Application.launch;
import javax.mail.Session;

public class ValidDate {

    //TOOLS FOR DATABASE
    //private Statement statement;
    //private static PreparedStatement ps;
    //TOOLS FOR EMAIL
    private Session session;

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

        ArrayList<String> birthdayPeople = new ArrayList<String>();
        while (result.next()) {
            birthdayPeople.add(result.getString("Staff_Name"));
        }
        return convertArrayListToString(birthdayPeople);
    }

//    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException{
//        launch(args);
//        isBirthday();
//
//    }
}
