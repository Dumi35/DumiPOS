package model_classes;

import java.util.Date;

/**
 *
 * @author dumid
 */
public class Sale {

    private String Code;
    private String Name;
    private Integer Quantity;
    private Integer Price;

    //create products constructor
    public Sale(String Code, String Name, Integer Quantity, Integer Price) {
        this.Code = Code;
        this.Name = Name;
        this.Quantity = Quantity;
        this.Price = Price;

    }

    /**
     * @return the Code
     */
    public String getCode() {
        return Code;
    }

    /**
     * @param Code the Code to set
     */
    public void setCode(String Code) {
        this.Code = Code;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Quantity
     */
    public Integer getQuantity() {
        return Quantity;
    }

    /**
     * @param Quantity the Quantity to set
     */
    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * @return the Price
     */
    public Integer getPrice() {
        return Price;
    }

    /**
     * @param Price the Price to set
     */
    public void setPrice(Integer Price) {
        this.Price = Price;
    }
}
