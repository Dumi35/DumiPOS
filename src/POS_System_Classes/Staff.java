/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POS_System_Classes;

import java.io.File;
import java.util.Date;

/**
 *
 * @author dumid
 */
public class Staff {
    private String StaffID;
    private String Name;
    private String Email;
    private String Phone_No;
    private Date DOB;
    private String Gender;
    private String Dept;
    private String Role;
    private File Passport;
    private String Hash_Password;
    private String Salt;
    
    public Staff(String StaffID, String Name, String Email,String Phone_No, Date DOB, String Gender,String Dept,String Role, File Passport,String Hash_Password,String Salt){
        this.StaffID = StaffID;
        this.Name = Name;
        this.Email = Email;
        this.Phone_No = Phone_No;
        this.DOB = DOB;
        this.Gender = Gender;
        this.Dept = Dept;
        this.Passport = Passport;
        this.Hash_Password = Hash_Password;
        this.Salt = Salt;
    }
}
