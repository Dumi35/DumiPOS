package sales_person_dash;

import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model_classes.Product;
import model_classes.Sale;
import utility_classes.DatabaseConn;

public class Sales_person_dashController implements Initializable {

    @FXML
    private VBox sideBar;
    @FXML
    private HBox DashboardTab;
    @FXML
    private HBox RegisterUserTab;
    @FXML
    private HBox UpdateUserTab;
    @FXML
    private HBox BackUpTab;
    @FXML
    private ComboBox<String> SearchProdCombo;
    @FXML
    private Circle profile_pic;
    @FXML
    private StackPane StackPane;
    @FXML
    private VBox DashboardTabPane;

    @FXML
    private TableColumn<Sale, String> Name_col;
    @FXML
    private TableColumn<Sale, Integer> Quantity_col;//number customer is paying for
    @FXML
    private TableColumn<Sale, Integer> Amount_col;
    @FXML
    private TableView<Sale> NewSaleTable;

    @FXML
    private TableColumn<Sale, String> Code_col;

    //TOOLS FOR DATABASE
    private Connection con;
    //private Statement statement;
    private PreparedStatement ps;
    private ResultSet result;
    private ObservableList<String> LowStock = FXCollections.observableArrayList();

    private ObservableList<Product> productList = FXCollections.observableArrayList();//stores all product attributes
    @FXML
    private Label totalLabel;

    //retrieve all product data from database
    public ObservableList<String> AllProductListData() throws SQLException {

        ObservableList<String> listDataNames = FXCollections.observableArrayList();//stores only product names

        con = DatabaseConn.connectDB();
        //read from Database table
        try {
            ps = con.prepareStatement("SELECT * FROM products");
            result = ps.executeQuery();

            Product allPData;

            while (result.next()) {
                allPData = new Product(result.getString("Product_Code"), result.getString("Product_Name"), result.getString("Manufacturer"), result.getDate("Manu_Date"), result.getDate("Expiry_Date"), result.getInt("Quantity"), result.getInt("Price"));
                productList.add(allPData);
                listDataNames.add(allPData.getName());
                if (allPData.getStatus().equals("Low")) {
                    LowStock.add(allPData.getName());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listDataNames;
    }

    public void autoCompleteSearch() throws SQLException {

        // Create a list with some dummy values.
        ObservableList<String> items = FXCollections.observableArrayList(AllProductListData());

        // Set a custom cell factory for the ComboBox
        SearchProdCombo.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);

                    // Check if the item is the "Special Item"
                    for (String i : LowStock) {
                        if (item.equals(i)) {
                            // Set the background color to red for the "Special Item"
                            setBackground();
                        }
                    }

                }
            }

            private void setBackground() {
                setStyle("-fx-background-color: #e5eaee; -fx-text-fill: black;");
            }
        });

        // Create a FilteredList wrapping the ObservableList.
        FilteredList<String> filteredItems = new FilteredList<>(items, p -> true);

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        SearchProdCombo.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = SearchProdCombo.getEditor();//get editor returns the textfield of the combbobox
            final String selected = SearchProdCombo.getSelectionModel().getSelectedItem();

            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        return item.toUpperCase().startsWith(newValue.toUpperCase());
                    });
                    SearchProdCombo.show();
                }
            });
        });

        SearchProdCombo.setItems(filteredItems);

    }

    //load product into table
    public void addProductToSale() {
        SearchProdCombo.getValue();
        //NewSaleTable.getItems().add();
    }

    //show all sale Data in tableview
    public void ShowSaleData() throws SQLException {
        //StaffData = AllStaffListData();
        Code_col.setCellValueFactory(new PropertyValueFactory<>("Code"));
        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Quantity_col.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Amount_col.setCellValueFactory(new PropertyValueFactory<>("Price"));

        //NewSaleTable.setItems(StaffData);
        //NewSaleTable.getC/;
    }

    public void addSaleItem() {
        SearchProdCombo.addEventFilter(KeyEvent.KEY_PRESSED, KeyEvent -> {
            KeyCode Enter = KeyCode.ENTER;
            if ((KeyEvent.getCode()).equals(Enter)) {

                for (Product p : productList) {
                    //add the new item
                    if (SearchProdCombo.getValue().equals(p.getName())) {
                        
                        //fill table if empty
                        if (NewSaleTable.getItems().isEmpty()) {
                            Sale item = new Sale(p.getCode(), SearchProdCombo.getValue(), 1, p.getPrice()); //create sale item with code, name, qty, and price
                            NewSaleTable.getItems().add(item);
                        } 
                        
                        //if not empty, check if item is already in cart/table
                        else {
                            for(int i=0; i<NewSaleTable.getItems().size();i++){
                                Sale s =NewSaleTable.getItems().get(i);
                                if (SearchProdCombo.getValue().equals(s.getName())) {
                                    //System.out.println("i'm already here");
                                    s.setQuantity(s.getQuantity()+1);  
                                    s.setPrice(p.getPrice()*s.getQuantity());
                                    NewSaleTable.refresh();
                                    break;
                                }
                                if(i==NewSaleTable.getItems().size()-1){
                                    Sale item = new Sale(p.getCode(), SearchProdCombo.getValue(), 0, p.getPrice()); //is adding an extra one for some reason but i can't be asked
                                    NewSaleTable.getItems().add(item);
                                }
                            }
                        }
                        

                    }
                }
                CalculateTotal();
            }
            
        });
    }

    public void CalculateTotal() {
        
        int totalPrice=0;
        for(Sale s: NewSaleTable.getItems()){
            totalPrice = s.getPrice()+ totalPrice;
        }
        totalLabel.setText(String.valueOf(totalPrice));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            autoCompleteSearch();
            ShowSaleData();
            addSaleItem();
        } catch (SQLException ex) {
            Logger.getLogger(Sales_person_dashController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
