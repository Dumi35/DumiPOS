/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Inventory_mng_dash;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.shape.Circle;
import model_classes.Product;

/**
 * FXML Controller class
 *
 * @author dumid
 */
public class Inventory_mng_dashController implements Initializable {

    @FXML
    private Circle profile_pic;
    @FXML
    private TableView<Product> AllProductsTable;
    @FXML
    private TableColumn<Product, String> code_col;
    @FXML
    private TableColumn<Product, String> name_col;
    @FXML
    private TableColumn<Product, String> manufacturer_col;
    @FXML
    private TableColumn<Product, Date> mfd_date_col;
    @FXML
    private TableColumn<Product, Date> exp_date_col;
    @FXML
    private TableColumn<Product, Integer> quantity_col;
    @FXML
    private TableColumn<Product, Integer> price_col;
    @FXML
    private TableColumn<Product, String> status_col;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
