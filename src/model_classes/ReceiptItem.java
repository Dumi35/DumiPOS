package model_classes;

/**
 *
 * @author dumid
 */
public class ReceiptItem {
    private String ReceiptID;
    private String Code;
    private String Name;
    private Integer Quantity;
    private Integer Price;
    private String StaffID;

    //create products constructor
    public ReceiptItem(String ReceiptID,String Code, String Name, Integer Quantity, Integer Price, String StaffID) {
        this.ReceiptID = ReceiptID;
        this.Code = Code;
        this.Name = Name;
        this.Quantity = Quantity;
        this.Price = Price;
        this.StaffID=StaffID;
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

    /**
     * @return the StaffID
     */
    public String getStaffID() {
        return StaffID;
    }

    /**
     * @param StaffID the StaffID to set
     */
    public void setStaffID(String StaffID) {
        this.StaffID = StaffID;
    }

    /**
     * @return the ReceiptID
     */
    public String getReceiptID() {
        return ReceiptID;
    }

    /**
     * @param ReceiptID the ReceiptID to set
     */
    public void setReceiptID(String ReceiptID) {
        this.ReceiptID = ReceiptID;
    }
}
