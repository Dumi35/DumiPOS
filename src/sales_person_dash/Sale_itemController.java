/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sales_person_dash;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model_classes.Product;

/**
 * FXML Controller class
 *
 * @author dumid
 */
public class Sale_itemController implements Initializable {

    @FXML
    private Label productName;
    @FXML
    private Label productDescription;
    @FXML
    private Label productPrice;
    @FXML
    private Rectangle productPhoto;
    
    private Product currentProduct;

    /**
     * Initializes the controller class.
     * @return 
     */
    
    public Product getCurrentProduct(){
        return this.currentProduct;
    }
    
    public void setData(Product newProduct) {
        this.currentProduct=newProduct;
        productName.setText(newProduct.getName());
        productDescription.setText(newProduct.getDescription());
        productPrice.setText(String.valueOf(newProduct.getPrice()));
        productPhoto.setFill(new ImagePattern(newProduct.getProductPhoto()));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
