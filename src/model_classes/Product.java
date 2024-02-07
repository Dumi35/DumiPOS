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
        
        if(this.Quantity<=10 && this.Quantity>0){
            this.Status = "Low";//if quantity < 10, status is low
        }else if (this.Quantity==0){
            this.Status = "Out of stock";
        } else{
            this.Status = "High";//if quantity < 10, status is low
        }
       
    }

    /**
     * @param Code the Code to set
     */
    public void setCode(String Code) {
        this.Code = Code;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @param Manufacturer the Manufacturer to set
     */
    public void setManufacturer(String Manufacturer) {
        this.Manufacturer = Manufacturer;
    }

    /**
     * @param Manu_Date the Manu_Date to set
     */
    public void setManu_Date(Date Manu_Date) {
        this.Manu_Date = Manu_Date;
    }

    /**
     * @param Expiry_Date the Expiry_Date to set
     */
    public void setExpiry_Date(Date Expiry_Date) {
        this.Expiry_Date = Expiry_Date;
    }

    /**
     * @param Quantity the Quantity to set
     */
    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * @param Price the Price to set
     */
    public void setPrice(Integer Price) {
        this.Price = Price;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }
    
}
