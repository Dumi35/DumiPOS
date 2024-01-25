package POS_System_Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javax.mail.internet.ParseException;


/**
 *
 * @author dumid
 */
public class ValidDate { 
    private static boolean isValidDateFormat(String dateStr, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    //for dd/mm/yyyy format
    public static Date dateDayMonthYearConvert(String inputDateString){
        try{

            // Define the format of the input date string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Parse the input date string to LocalDate
            LocalDate localDate = LocalDate.parse(inputDateString, formatter);

            // Convert LocalDate to java.sql.Date
            Date sqlDate = Date.valueOf(localDate);

            // Print the result
            //System.out.println("Input Date: " + inputDateString);
            //System.out.println("Converted Date: " + sqlDate);
            //isValidDate=true;
            return sqlDate;
  
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    
    //for dd MMM yyyy format e.g 21st April
    public static Date dateInWordsConvert(String inputDateString) throws java.text.ParseException {       
        try{
            
            //String inputDateString = "21st April";

            // Parse the day and month from the input string
            int day = Integer.parseInt(inputDateString.split(" ")[0].replaceAll("\\D", ""));
            
            String month = inputDateString.split(" ")[1];
            
            String year = inputDateString.split(" ")[2];
            // Concatenate the day, month, and a sample year to create a valid date string
            String dateString = day + " " + month + " " + year;
            
            // Use SimpleDateFormat to parse the concatenated date string
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
            java.util.Date utilDate;
            utilDate = dateFormat.parse(dateString);

            // Convert java.util.Date to LocalDate
            LocalDate localDate = utilDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            // Convert LocalDate to java.sql.Date
            Date sqlDate = Date.valueOf(localDate);

            // Print the result
            //System.out.println("Input Date: " + inputDateString);
            //System.out.println("Converted Date: " + sqlDate);
            
            return sqlDate;
            
        }catch(NumberFormatException | java.text.ParseException e){
            System.out.println(e);
        }
        return null;
    }
    
    //retun result to the controller class
    public static Date parsedDateResult(String dateString) throws java.text.ParseException{
        boolean isDateFormat1 = isValidDateFormat(dateString, "d MMM yyyy"); //date in words
        boolean isDateFormat2 = isValidDateFormat(dateString, "dd/MM/yyyy");
        
        
        if(isDateFormat1){
           return dateInWordsConvert(dateString);
        }else if(isDateFormat2){
           return dateDayMonthYearConvert(dateString);  
        }else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Invalid date format");
//            alert.setContentText("Date must be  dd/mm/yyyy or d MMM YYYY e.g 2 Apr 2004");
//            alert.showAndWait();
        }
        return null;
    }
    
    public static void main(String[] args) throws java.text.ParseException {
        //System.out.println("date: "+ parsedDateResult("02/06/2004"));
    }
}
