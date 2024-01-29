package model_classes;

import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;


public class Staff {
    private String StaffID;
    private String Name;
    private String Email;
    private String Phone_No;
    private Date DOB;
    private String Gender;
    private String Dept;
    private String Role;
    private byte[] Passport;
    private String Hash_Password;
    private String Salt;
    
    //create staff constructor
    public Staff(String StaffID, String Name, String Email,String Phone_No, Date DOB, String Gender,String Dept,String Role, byte [] Passport,String Hash_Password,String Salt){
        this.StaffID = StaffID;
        this.Name = Name;
        this.Email = Email;
        this.Phone_No = Phone_No;
        this.DOB = DOB;
        this.Gender = Gender;
        this.Dept = Dept;
        this.Role = Role;
        this.Passport = Passport;
        this.Hash_Password = Hash_Password;
        this.Salt = Salt;
    }
    
    //email validation
    public boolean isValidEmail() {
       Pattern email_pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
       Matcher match = email_pattern.matcher(getEmail());
       
       return match.find() && match.group().equals(getEmail());
    }
    
    //phone number validation
    public boolean isValidPhoneNumber() {
       Pattern phone_pattern = Pattern.compile("[0-9]+");
       Matcher match = phone_pattern.matcher(getPhone_No());
       
       return match.find() && match.group().equals(getPhone_No());
    }
    
    //date validation
    public boolean isValidDateOfBirth(TextField DOB){
        return true;
    }

    /**
     * @return the StaffID
     */
    public String getStaffID() {
        return StaffID;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @return the Phone_No
     */
    public String getPhone_No() {
        return Phone_No;
    }

    /**
     * @return the DOB
     */
    public Date getDOB() {
        return DOB;
    }

    /**
     * @return the Gender
     */
    public String getGender() {
        return Gender;
    }

    /**
     * @return the Dept
     */
    public String getDept() {
        return Dept;
    }

    /**
     * @return the Role
     */
    public String getRole() {
        return Role;
    }

    /**
     * @return the Passport
     */
    public byte[] getPassport() {
        return Passport;
    }

    /**
     * @return the Hash_Password
     */
    public String getHash_Password() {
        return Hash_Password;
    }

    /**
     * @return the Salt
     */
    public String getSalt() {
        return Salt;
    }

}
