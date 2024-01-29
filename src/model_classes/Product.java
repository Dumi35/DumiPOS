package model_classes;

import java.util.Date;

/**
 *
 * @author dumid
 */
public class Product {

    /**
     * @return the Code
     */
    public String getCode() {
        return Code;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @return the Manufacturer
     */
    public String getManufacturer() {
        return Manufacturer;
    }

    /**
     * @return the Manu_Date
     */
    public Date getManu_Date() {
        return Manu_Date;
    }

    /**
     * @return the Expiry_Date
     */
    public Date getExpiry_Date() {
        return Expiry_Date;
    }

    /**
     * @return the Quantity
     */
    public Integer getQuantity() {
        return Quantity;
    }

    /**
     * @return the Price
     */
    public Integer getPrice() {
        return Price;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }
    
    private String Code;
    private String Name;
    private String Manufacturer;
    private Date Manu_Date;
    private Date Expiry_Date;
    private Integer Quantity;
    private Integer Price;
    private String Status;
   
    
      //create products constructor
    public Product(String Code, String Name, String Manufacturer,Date Manu_Date, Date Expiry_Date, Integer Quantity,Integer Price){
        this.Code = Code;
        this.Name = Name;
        this.Manufacturer = Manufacturer;
        this.Manu_Date = Manu_Date;
        this.Expiry_Date = Expiry_Date;
        this.Quantity = Quantity;
        this.Price = Price;
        
        if(this.Quantity<=10){
            this.Status = "Low";//if quantity < 10, status is low
        }else{
            this.Status = "High";//if quantity < 10, status is low
        } 
       
    }
    
}
