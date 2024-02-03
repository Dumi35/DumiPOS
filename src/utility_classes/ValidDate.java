package utility_classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


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

        ArrayList<String> birthdayPeople = new ArrayList<String>();
        while (result.next()) {
            birthdayPeople.add(result.getString("Staff_Name"));
        }
        if(birthdayPeople.isEmpty()){ //if noone's bday is the current day
            return null;
        }
        return convertArrayListToString(birthdayPeople);
    }

}
