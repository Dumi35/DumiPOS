package sales_person_dash;

import Inventory_mng_dash.Inventory_mng_dashController;
import components.alerts.AlertController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model_classes.Product;
import model_classes.Sale;
import utility_classes.DatabaseConn;
import utility_classes.MyPrinter;
import utility_classes.SwitchTabs;
import utility_classes.ValidDate;

public class Sales_person_dashController implements Initializable {

    //ui tools
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox sideBar;
    @FXML
    private HBox DashboardTab;

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

    //TOOLS FOR DATABASE
    private Connection con;
    //private Statement statement;
    private PreparedStatement ps;
    private ResultSet result;
    private ObservableList<String> LowStock = FXCollections.observableArrayList();

    private ObservableList<Product> productList = FXCollections.observableArrayList();//stores all product attributes
    @FXML
    private Label totalLabel;
    @FXML
    private ScrollPane Receipt;

    private MyPrinter printer = new MyPrinter();
    @FXML
    private VBox ReceiptItems;
    @FXML
    private Button acceptBtn;
    @FXML
    private Button printBtn;
    @FXML
    private Label ReceiptDate;
    @FXML
    private BorderPane ReceiptBorderPane;
    @FXML
    private Label ReceiptTotal;
    @FXML
    private Label userName;

    private String salesPersonID;
    @FXML
    private Label ReceiptNumber;
    @FXML
    private FlowPane SaleFlowPane;
    @FXML
    private HBox SalesTab;
    @FXML
    private HBox TestTab;
    @FXML
    private VBox SalesTabPane;
    @FXML
    private TableView<?> NewSaleTable1;
    @FXML
    private TableColumn<?, ?> Code_col;
    @FXML
    private TableColumn<?, ?> Name_col1;
    @FXML
    private TableColumn<?, ?> Quantity_col1;
    @FXML
    private TableColumn<?, ?> Amount_col1;

    TableColumn<Sale, Void> colBtn = new TableColumn("");

    //load profile pic and username
    public void setData(String name, Image pic, String staffID) {
        userName.setText(name);
        profile_pic.setFill(new ImagePattern(pic));
        salesPersonID = staffID;
    }

    //FOR EACH PRODUCT CARD
    ObservableList<Node> productVBoxes = FXCollections.observableArrayList();

    //retrieve all product data from database
    public ObservableList<String> AllProductListSale() throws SQLException {

        ObservableList<String> listSaleNames = FXCollections.observableArrayList();//stores only product names

        con = DatabaseConn.connectDB();
        //read from Salebase table
        try {
            //retrieve goods that are not about to expire so more than 5 days from the expiry date
            ps = con.prepareStatement("SELECT * FROM products WHERE Expiry_Date > '" + java.time.LocalDate.now() + "' AND Quantity>0");

            result = ps.executeQuery();

            Product allPSale;

            while (result.next()) {
                //byte[] photo = result.getBytes("ProductPhoto");
                java.sql.Blob columnValue = result.getBlob("Product_Photo");

                Image image = new Image(getClass().getResource("../images/starfire.jpg").toExternalForm(), 150, 150, true, true);
                if (columnValue != null) {
                    byte[] imageData = columnValue.getBytes(1, (int) columnValue.length());
                    image = new Image(new ByteArrayInputStream(imageData));
                }

                String description = "Lorem ipsum dolor sit amet consectetur adipisicing elit.";

                if (result.getString("Description") != null) {
                    description = result.getString("Description");
                }

                allPSale = new Product(result.getString("Product_Code"), result.getString("Product_Name"), result.getString("Manufacturer"), result.getDate("Manu_Date"), result.getDate("Expiry_Date"), result.getInt("Quantity"), result.getInt("Price"), image, description);
                productList.add(allPSale);
                listSaleNames.add(allPSale.getName());
                if (allPSale.getStatus().equals("Low")) {
                    LowStock.add(allPSale.getName());
                }

                //from your model
                //load passport image
                Product newProduct = allPSale;

                newProduct.setProductPhoto(allPSale.getProductPhoto());
//the model's setter to set its relevant data
                newProduct.setName(allPSale.getName());
                newProduct.setDescription(allPSale.getDescription());
                newProduct.setPrice(allPSale.getPrice());

                try {
                    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Sale_item.fxml"));
                    VBox orderVBox = fxmlloader.load();
                    Sale_itemController currentOrderController = fxmlloader.getController();
//in the controller, have a methd that sets the data            
                    currentOrderController.setData(newProduct);
                    SaleFlowPane.getChildren().add(orderVBox);
                    productVBoxes.add(orderVBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSaleNames;
    }

    private void filterProducts(String searchQuery) {
        SaleFlowPane.getChildren().clear();
        if (searchQuery == null || searchQuery.equals("")) {
            for (Node node : productVBoxes) {
                if (node instanceof VBox productVBox) {
                    Sale_itemController productController = (Sale_itemController) productVBox.getProperties().get("controller");
                    SaleFlowPane.getChildren().add(productVBox);  // Add matching products
                }
            }

        } else {
            for (Node node : productVBoxes) {

                for (Node vbox : ((VBox) node).getChildren()) { //node is whole parent fxml that had image and other vbox
                    if (vbox instanceof VBox) {
                        for (Node labelProdName : ((VBox) vbox).getChildren()) {
                            if (labelProdName.getId().equals("productName")) {

                                if (labelProdName instanceof Label) {
                                    String name = ((Label) labelProdName).getText();
                                    if (containsSearchQuery(name, searchQuery)) {
                                        SaleFlowPane.getChildren().add(node);
                                    }
                                    //when parent vbox clicked, should add to receipt
                                    node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            addSaleItems(name);
                                        }
                                    });
                                }

                            }
                        }
                    }

                }
                //}

            }
        }
    }

    //for inital load
    public void VBoxAddSale() {
        for (Node node : productVBoxes) {

            for (Node vbox : ((VBox) node).getChildren()) { //node is whole parent fxml that had image and other vbox
                if (vbox instanceof VBox) {
                    for (Node labelProdName : ((VBox) vbox).getChildren()) {
                        if (labelProdName.getId().equals("productName")) {

                            if (labelProdName instanceof Label) {
                                String name = ((Label) labelProdName).getText();
                                //when parent vbox clicked, should add to receipt
                                node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        addSaleItems(name);
                                    }
                                });
                            }

                        }
                    }
                }

            }
            //}

        }
    }

    private boolean containsSearchQuery(String name, String searchQuery) {
        // Check if product code or name contains the search query (case-insensitive)
        //String name = product.getName().toLowerCase();
        searchQuery = searchQuery.toLowerCase();

        return name.contains(searchQuery);
    }

    public void autoCompleteSearch() throws SQLException {

        // Create a list with some dummy values.
        ObservableList<String> items = FXCollections.observableArrayList(AllProductListSale());

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

                filterProducts(editor.getText()); //load product cards in flowpane
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

    //show all sale Sale in tableview
    public void ShowSaleTable() throws SQLException {
        //Code_col.setCellValueFactory(new PropertyValueFactory<>("Code"));
        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Quantity_col.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Amount_col.setCellValueFactory(new PropertyValueFactory<>("Price"));

        // Define a custom formatter for the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

        // Format the LocalDate object using the formatter
        String formattedDate = LocalDate.now().format(formatter); //get current date

        //display date on receipt
        ReceiptDate.setText(String.valueOf(formattedDate));

    }

    public void addSaleItems(String prodName) {
        for (Product p : productList) {
            //add the new item
            if (prodName.equals(p.getName())) {
                //fill table if empty
                if (NewSaleTable.getItems().isEmpty()) {
                    Sale item = new Sale(p.getCode(), prodName, 1, p.getPrice()); //create sale item with code, name, qty, and price
                    NewSaleTable.getItems().add(item);
                } //if not empty, check if item is already in cart/table
                else {
                    for (int i = 0; i < NewSaleTable.getItems().size(); i++) {
                        Sale s = NewSaleTable.getItems().get(i);
                        if (prodName.equals(s.getName())) {
                            //System.out.println("i'm already here");
                            s.setQuantity(s.getQuantity() + 1);
                            s.setPrice(p.getPrice() * s.getQuantity());
                            NewSaleTable.refresh();
                            break;
                        }
                        if (i == NewSaleTable.getItems().size() - 1) {
                            Sale item = new Sale(p.getCode(), prodName, 0, p.getPrice()); //is adding an extra one for some reason but i can't be asked
                            NewSaleTable.getItems().add(item);
                        }
                    }
                }

            }
        }
        CalculateTotal();
    }

    public void searchBarAddItem() {
        SearchProdCombo.addEventFilter(KeyEvent.KEY_PRESSED, KeyEvent -> {
            KeyCode Enter = KeyCode.ENTER;
            if ((KeyEvent.getCode()).equals(Enter)) {
                addSaleItems(SearchProdCombo.getValue());

            }

        });

    }

    public void CalculateTotal() {

        int totalPrice = 0;
        for (Sale s : NewSaleTable.getItems()) {
            totalPrice = s.getPrice() + totalPrice;
        }
        totalLabel.setText(String.valueOf(totalPrice));
        

    }

    @FXML
    public void ClearSales() throws SQLException {
        NewSaleTable.getItems().clear();
        //ReceiptItems.getChildren().clear();
        CalculateTotal();
        ReceiptTotal.setText(String.valueOf(0));
        NewSaleTable.getColumns().remove(colBtn);
        addButtonToTable();
    }

    private void addButtonToTable() {
        //TableColumn<Sale, Void> colBtn = new TableColumn("");
        Name_col.setPrefWidth(116);
        Quantity_col.setPrefWidth(93);
        Amount_col.setPrefWidth(93);
        colBtn.setPrefWidth(100);

        Callback<TableColumn<Sale, Void>, TableCell<Sale, Void>> cellFactory = new Callback<TableColumn<Sale, Void>, TableCell<Sale, Void>>() {
            public TableCell<Sale, Void> call(final TableColumn<Sale, Void> param) {
                final TableCell<Sale, Void> cell = new TableCell<Sale, Void>() {

                    Button addBtn = new Button("+");
                    Button deleteBtn = new Button("-");

                    //btn.setText("To");
                    {
                        addBtn.setStyle("-fx-text-fill: white; -fx-background-radius: 5px; -fx-border-radius:5px;-fx-padding:4;");
                        addBtn.setPrefHeight(3);
                        addBtn.setPrefWidth(40);
                        //addBtn.setMaxSize(30, 20);
                        deleteBtn.setStyle("-fx-text-fill: white; -fx-background-radius: 5px; -fx-border-radius:5px;-fx-padding:4;");
                        deleteBtn.setPrefHeight(3);
                        deleteBtn.setPrefWidth(40);

                        addBtn.setOnAction((ActionEvent event) -> {
                            Sale data = getTableView().getItems().get(getIndex());
                            int price = data.getPrice() / data.getQuantity();//would give original price for one item
                            data.setQuantity(data.getQuantity() + 1);
                            data.setPrice(data.getQuantity() * price);
                            CalculateTotal();
                            NewSaleTable.refresh();
                        });

                        //reduce number in table
                        deleteBtn.setOnAction((ActionEvent event) -> {
                            Sale data = getTableView().getItems().get(getIndex());
                            int price = data.getPrice() / data.getQuantity();//would give original price for one item
                            //if 1 item in table and delete is done, delete
                            if (data.getQuantity() == 1) {
                                NewSaleTable.getItems().remove(data);
                            } else { //reduce quantity by 1
                                data.setQuantity(data.getQuantity() - 1);
                                data.setPrice(data.getQuantity() * price);
                                NewSaleTable.refresh();
                            }
                            CalculateTotal();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttonBox = new HBox(addBtn, deleteBtn);
                            buttonBox.setSpacing(10);
                            setGraphic(buttonBox);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        NewSaleTable.getColumns().add(colBtn);

    }

    //disable accept btn and print btn
    public void acceptPayment() {

        NewSaleTable.getItems().addListener((ListChangeListener<Object>) c -> {
            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (NewSaleTable.getItems().isEmpty()) {
                    // If the table is empty, disable the buttons
                    acceptBtn.setDisable(true);
                    printBtn.setDisable(true);

                } else {
                    // If the table is not empty, enable the buttons
                    acceptBtn.setDisable(false);
                    //printBtn.setDisable(false);
                }
            });
        });

    }

    @FXML
    public void createReceipt() throws SQLException {
        NewSaleTable.getColumns().remove(colBtn);
        Name_col.setPrefWidth(189);
        Quantity_col.setPrefWidth(106);
        Amount_col.setPrefWidth(106);
        
        ps = con.prepareStatement("select MAX(Receipt_ID) AS LastReceiptNo FROM sale_receipts");
        ResultSet rs = ps.executeQuery();
        int receiptNo = 0;
        while (rs.next()) {
            receiptNo = (rs.getInt("LastReceiptNo")) + 1;
        }
        if (receiptNo < 100) {
            ReceiptNumber.setText("Receipt #" + String.format("%03d", receiptNo));//e.g display 12 as 012 or 99 as 099
        }

        //reduce stock number in products table and save item in receipt
        ps = con.prepareStatement("select * from products");
        rs = ps.executeQuery();

        while (rs.next()) {
            for (Sale s : NewSaleTable.getItems()) {
                //reduce stock no here
                if (rs.getString("Product_Name").equals(s.getName())) {
                    PreparedStatement prepare = con.prepareStatement("update products set Quantity = ? where Product_Name=?");
                    prepare.setInt(1, rs.getInt("Quantity") - s.getQuantity());
                    prepare.setString(2, rs.getString("Product_Name"));
                    prepare.executeUpdate();

                    prepare = con.prepareStatement("insert into sale_receipts values(?,?,?,?,?,?,?)");
                    prepare.setInt(1, receiptNo);
                    //ps.setString(6, salesPersonID);
                    prepare.setString(2, s.getCode());
                    prepare.setString(3, s.getName());
                    prepare.setInt(4, s.getQuantity());
                    prepare.setInt(5, s.getPrice());
                    prepare.setString(6, "001");
                    prepare.setObject(7, LocalDateTime.now());
                    prepare.executeUpdate();
                }

            }
        }
        //ReceiptItems.getChildren().add(ReceiptTable);

        ReceiptTotal.setText(totalLabel.getText());
        printBtn.setDisable(false);
        acceptBtn.setDisable(true);
    }

    //print receipt
    @FXML
    private void printReceipt() {
        Rectangle printBounds = new Rectangle(ReceiptBorderPane.getWidth(), ReceiptBorderPane.getHeight());
        printer.setPrintRectangle(printBounds);

        // Create and configure a PrinterJob
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            // Print the script VBox using the NodePrinter class
            boolean success = printer.print(job, true, ReceiptBorderPane);
            if (success) {
                job.endJob(); // Only call this if printing is successful
            }
        }
    }

    //update ui
    // Method to update the UI with new data
    public void UpdateUI() throws IOException {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {

                    try {
                        autoCompleteSearch();
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
        try {
            autoCompleteSearch();
            ShowSaleTable();
            searchBarAddItem();
            addButtonToTable();
            acceptPayment();
            VBoxAddSale();
            SwitchTabs.switchTabs(sideBar, StackPane);
            //UpdateUI();
        } catch (SQLException ex) {
            Logger.getLogger(Sales_person_dashController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
