package Inventory_mng_dash;

import IT_admin_dash.IT_admin_dash_Controller;
import components.alerts.AlertController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import static javax.swing.text.StyleConstants.Background;
import model_classes.Product;
import model_classes.Product;
import model_classes.Sale;
import utility_classes.DatabaseConn;
import utility_classes.SwitchTabs;
import utility_classes.ValidDate;

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
    @FXML
    private Label userName;
    @FXML
    private VBox sideBar;
    @FXML
    private HBox DashboardTab;
    @FXML
    private HBox NewProductTab;
    @FXML
    private VBox DashboardTabPane;
    @FXML
    private TableView<?> AllProductsTable1;
    @FXML
    private TableColumn<?, ?> code_col1;
    @FXML
    private TableColumn<?, ?> name_col1;
    @FXML
    private TableColumn<?, ?> manufacturer_col1;
    @FXML
    private TableColumn<?, ?> mfd_date_col1;
    @FXML
    private TableColumn<?, ?> exp_date_col1;
    @FXML
    private TableColumn<?, ?> quantity_col1;
    @FXML
    private TableColumn<?, ?> price_col1;
    @FXML
    private TableColumn<?, ?> status_col1;
    @FXML
    private StackPane StackPane;
    @FXML
    private VBox NewProductTabPane;

    //TOOLS FOR DATABASE
    private Connection con;
    //private Statement statement;
    private PreparedStatement ps;
    private ResultSet result;

    //ui tools
    private Stage stage;
    private Scene scene;
    private Parent root;

    //load profile pic and username
    public void setData(String name, Image pic) {
        userName.setText(name);
        profile_pic.setFill(new ImagePattern(pic));
    }

    // Method to update the UI with new data
    public void UpdateUI() throws IOException {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {
                    
                    try {
                        ShowProductData();
                    } catch (SQLException ex) {
                        Logger.getLogger(Inventory_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

            }
        }, 0, 3000); // Update every 3 seconds

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                stage = (Stage) StackPane.getScene().getWindow();

                //listen for when close button is clicked to stop the updateUI function
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        timer.cancel();
                    }
                });
                Platform.runLater(() -> {
                    
                    try {
                        // Update JavaFX UI components here
                        DisplayBirthdays();
                        // This code will run on the JavaFX Application Thread
                    } catch (SQLException | IOException ex) {
                        Logger.getLogger(Inventory_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
            }
        };
        timer.schedule(task, 3000);// Schedule the task to run once after 3 seconds 
    }

    //dashboard pane functions
    //retrieve all staff data from database
    public ObservableList<Product> AllProductListData() throws SQLException {
        ObservableList<Product> listData = FXCollections.observableArrayList();

        con = DatabaseConn.connectDB();
        //read from Database table
        try {
            ps = con.prepareStatement("SELECT * FROM products");
            result = ps.executeQuery();

            Product allPData;

            while (result.next()) {
                allPData = new Product(result.getString("Product_Code"), result.getString("Product_Name"), result.getString("Manufacturer"), result.getDate("Manu_Date"), result.getDate("Expiry_Date"), result.getInt("Quantity"), result.getInt("Price"));
                listData.add(allPData);
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText(e.toString());
            alert.showAndWait();
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Product> ProductData;

    //show all staff Data in tableview
    public void ShowProductData() throws SQLException {
        ProductData = AllProductListData();
        colourStatusColumn();
        code_col.setCellValueFactory(new PropertyValueFactory<>("Code"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
        manufacturer_col.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
        mfd_date_col.setCellValueFactory(new PropertyValueFactory<>("Manu_Date"));
        exp_date_col.setCellValueFactory(new PropertyValueFactory<>("Expiry_Date"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("Price"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("Status"));

        AllProductsTable.setItems(ProductData);
    }

    public void colourStatusColumn() {

        status_col.setCellFactory(new Callback<TableColumn<Product, String>,
                TableCell<Product, String>>()
        {
            @Override
            public TableCell<Product, String> call(
                    TableColumn<Product, String> param) {
                return new TableCell<Product, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (!empty) {
                            int currentIndex = indexProperty()
                                    .getValue() < 0 ? 0
                                    : indexProperty().getValue();
                            String status_col = param
                                    .getTableView().getItems()
                                    .get(currentIndex).getStatus();
                            if (status_col.equals("High")) {
                                setWidth(5);
                                setTextFill(Color.WHITE);
                                setStyle("-fx-font-weight: bold");
                                setStyle("-fx-background-color: #AECFA2");
                                setText(status_col);
                            } else if (status_col.equals("Low")){
                                setWidth(5);
                                setTextFill(Color.WHITE);
                                setStyle("-fx-font-weight: bold");
                                setStyle("-fx-background-color: #E0C66B");
                                setText(status_col);
                            } else if (status_col.equals("Out of stock")){
                                setWidth(5);
                                setTextFill(Color.WHITE);
                                setStyle("-fx-font-weight: bold");
                                setStyle("-fx-background-color: #FD776F");
                                setText(status_col);
                            } 
                        }
                    }
                };
            }
        });

    }

    public void DisplayBirthdays() throws SQLException, IOException {
        //load birthday alert
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
        stage = (Stage) StackPane.getScene().getWindow();
        
        root = loader.load();

        
        AlertController ac = loader.getController();
        if (ValidDate.isBirthday() != null) {
            ac.SetContent("Today's bday celebs: " + ValidDate.isBirthday(), root, stage);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SwitchTabs.switchTabs(sideBar, StackPane);
        
        try {
            UpdateUI();

        } catch (IOException ex) {
            Logger.getLogger(IT_admin_dash_Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
