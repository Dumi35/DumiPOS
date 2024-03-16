package Inventory_mng_dash;

import IT_admin_dash.IT_admin_dash_Controller;
import components.alerts.AlertController;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import static javax.swing.text.StyleConstants.Background;
import model_classes.Product;
import model_classes.Product;
import model_classes.Sale;
import utility_classes.DatabaseConn;
import utility_classes.Images;
import utility_classes.SwitchTabs;
import utility_classes.NotificationClass;

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
    private StackPane StackPane;

    private static byte[] photo = null;
    //TOOLS FOR DATABASE
    private Connection con = DatabaseConn.connectDB();
    //private Statement statement;
    private PreparedStatement ps;
    private ResultSet result;

    //ui tools
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField ProdCode;
    @FXML
    private TextField ProdName;
    @FXML
    private TextField ProdManufacturer;
    @FXML
    private DatePicker ProdMFD;
    @FXML
    private DatePicker ProdEXP;
    @FXML
    private TextField ProdQuantity;
    @FXML
    private TextField ProdCost;
    @FXML
    private TextField ProdPrice;
    @FXML
    private TextArea ProdDescr;
    @FXML
    private Rectangle ProdPhoto;

    private String myEmail;
    @FXML
    private Label restockProdName;
    @FXML
    private TextField restockProdQty;
    @FXML
    private VBox NewProductTabPane;

    //load profile pic and username
    public void setData(String name, Image pic, String email) {
        userName.setText(name);
        profile_pic.setFill(new ImagePattern(pic));
        myEmail = email;
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
                        NotifyLowStock();
                    } catch (SQLException | IOException ex) {
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
                        NotifyExpiryDate();
                        
                        // This code will run on the JavaFX Application Thread
                    } catch (SQLException | IOException ex) {
                        Logger.getLogger(Inventory_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            }
        };
        timer.schedule(task, 3000);// Schedule the task to run once after 3 seconds 
    }

    public void NotifyLowStock() throws SQLException, IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
        stage = (Stage) StackPane.getScene().getWindow();

        root = loader.load();

        AlertController ac = loader.getController();
        String value = NotificationClass.LowStockLevels(myEmail);
        if (value != null) {
            ac.SetContent("Low stock levels: " + value, root, stage);
        }
    }

    public void NotifyExpiryDate() throws SQLException, IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
        stage = (Stage) StackPane.getScene().getWindow();

        root = loader.load();

        AlertController ac = loader.getController();
        String value = NotificationClass.isExpiryDate(myEmail);
        if (value != null) {
            ac.SetContent("These items are about to expire: " + value, root, stage);
        }

    }

    @FXML
    public void ProductSelectData() throws SQLException {
        Product qData = AllProductsTable.getSelectionModel().getSelectedItem();
        int num = AllProductsTable.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }
        restockProdName.setText(String.valueOf(qData.getName()));
    }

    @FXML
    public void RestockProduct() throws SQLException {
        ps = con.prepareStatement("SELECT * FROM products where Product_Name =?");
        ps.setString(1, restockProdName.getText());
        result = ps.executeQuery();
        if (result.next()) {
            ps = con.prepareStatement("UPDATE products set Quantity=? where Product_Name =?");
            ps.setInt(1, Integer.parseInt(restockProdQty.getText())+result.getInt("Quantity"));
            ps.setString(2, restockProdName.getText());
            ps.executeUpdate();
        }
    }

    //dashboard pane functions
    //retrieve all product data from database
    public ObservableList<Product> AllProductListData() throws SQLException {
        ObservableList<Product> listData = FXCollections.observableArrayList();

        //con = DatabaseConn.connectDB();
        //read from Database table
        try {
            ps = con.prepareStatement("SELECT * FROM products");
            result = ps.executeQuery();

            Product allPData;
            Image image = null;

            while (result.next()) {
                allPData = new Product(result.getString("Product_Code"), result.getString("Product_Name"), result.getString("Manufacturer"), result.getDate("Manu_Date"), result.getDate("Expiry_Date"), result.getInt("Quantity"), result.getInt("Price"), image, result.getString("Description"));
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

    //show all product Data in tableview
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

        status_col.setCellFactory(new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
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
                            } else if (status_col.equals("Low")) {
                                setWidth(5);
                                setTextFill(Color.WHITE);
                                setStyle("-fx-font-weight: bold");
                                setStyle("-fx-background-color: #E0C66B");
                                setText(status_col);
                            } else if (status_col.equals("Out of stock")) {
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

    //register product pane functions
    @FXML
    public void Browse() {
        Images im = new Images();
        photo = im.BrowseSystemImages(ProdPhoto);
    }

    public void setDefaultProductPhoto() throws FileNotFoundException, IOException {
        String filePath = "C:\\Users\\dumid\\Documents\\NetBeansProjects\\Dumi_POS_System\\src\\images\\default_product.png";
        Image image = new Image("file:" + filePath, 150, 150, true, true);

        ProdPhoto.setFill(new ImagePattern(image)); //set default passport image

        File imageFile = new File(filePath);
        FileInputStream fis = new FileInputStream(imageFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] Byte = new byte[1024];

        for (int i; (i = fis.read(Byte)) != -1;) {
            baos.write(Byte, 0, i);
        }
        photo = baos.toByteArray();
    }

    @FXML
    public void CreateProduct() {
        // Image image = null;
//        Product prod = new Product(ProdCode.getText(), ProdName.getText(), ProdManufacturer.getText(), java.sql.Date.valueOf(ProdMFD.getValue()), java.sql.Date.valueOf(ProdEXP.getValue()), Integer.valueOf(ProdQuantity.getText()), Integer.valueOf(ProdPrice.getText()),image,ProdDescr.getText());

        try {
            //con = DatabaseConn.connectDB();
            ps = con.prepareStatement("insert into products values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, ProdCode.getText());
            ps.setString(2, ProdName.getText());
            ps.setString(3, ProdManufacturer.getText());
            ps.setDate(4, java.sql.Date.valueOf(ProdMFD.getValue()));
            ps.setDate(5, java.sql.Date.valueOf(ProdEXP.getValue()));
            ps.setInt(6, Integer.parseInt(ProdQuantity.getText()));
            ps.setInt(7, Integer.parseInt(ProdCost.getText()));
            ps.setInt(8, Integer.parseInt(ProdPrice.getText()));//selling price
            ps.setObject(9, LocalDateTime.now());//date time
            ps.setBytes(10, photo);//date time
            ps.setString(11, ProdDescr.getText());//date time

            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setContentText("Product successfully created");
            alert.showAndWait();

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("error " + e);
        }

    }

    public void DisplayBirthdays() throws SQLException, IOException {
        //load birthday alert
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
        stage = (Stage) StackPane.getScene().getWindow();

        root = loader.load();

        AlertController ac = loader.getController();
        if (NotificationClass.isBirthday(myEmail) != null) {
            ac.SetContent("Today's bday celebs: " + NotificationClass.isBirthday(myEmail), root, stage);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            NotificationClass.InitialiseCounters();
            SwitchTabs.switchTabs(sideBar, StackPane);
            setDefaultProductPhoto();
            UpdateUI();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Inventory_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
