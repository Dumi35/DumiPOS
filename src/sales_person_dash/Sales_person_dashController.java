package sales_person_dash;

import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model_classes.Product;
import model_classes.Sale;
import utility_classes.DatabaseConn;
import utility_classes.MyPrinter;

public class Sales_person_dashController implements Initializable {

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
    @FXML
    private ScrollPane Receipt;

    private MyPrinter printer = new MyPrinter();
    @FXML
    private HBox RegisterUserTab;
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

    //retrieve all product data from database
    public ObservableList<String> AllProductListSale() throws SQLException {

        ObservableList<String> listSaleNames = FXCollections.observableArrayList();//stores only product names

        con = DatabaseConn.connectDB();
        //read from Salebase table
        try {
            //retrieve goods that are not about to expire so more than 5 days from the expiry date
            ps = con.prepareStatement("SELECT * FROM products WHERE Expiry_Date > '2024-02-02' ");
            //ps = con.prepareStatement("SELECT * FROM products");
            result = ps.executeQuery();

            Product allPSale;

            while (result.next()) {
                allPSale = new Product(result.getString("Product_Code"), result.getString("Product_Name"), result.getString("Manufacturer"), result.getDate("Manu_Date"), result.getDate("Expiry_Date"), result.getInt("Quantity"), result.getInt("Price"));
                productList.add(allPSale);
                listSaleNames.add(allPSale.getName());
                if (allPSale.getStatus().equals("Low")) {
                    LowStock.add(allPSale.getName());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSaleNames;
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
        Code_col.setCellValueFactory(new PropertyValueFactory<>("Code"));
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
                        } //if not empty, check if item is already in cart/table
                        else {
                            for (int i = 0; i < NewSaleTable.getItems().size(); i++) {
                                Sale s = NewSaleTable.getItems().get(i);
                                if (SearchProdCombo.getValue().equals(s.getName())) {
                                    //System.out.println("i'm already here");
                                    s.setQuantity(s.getQuantity() + 1);
                                    s.setPrice(p.getPrice() * s.getQuantity());
                                    NewSaleTable.refresh();
                                    break;
                                }
                                if (i == NewSaleTable.getItems().size() - 1) {
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

        int totalPrice = 0;
        for (Sale s : NewSaleTable.getItems()) {
            totalPrice = s.getPrice() + totalPrice;
        }
        totalLabel.setText(String.valueOf(totalPrice));
    }

    @FXML
    public void ClearSales() {
        NewSaleTable.getItems().clear();
        ReceiptItems.getChildren().clear();
        CalculateTotal();
    }

    private void addButtonToTable() {
        TableColumn<Sale, Void> colBtn = new TableColumn("");
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
    public void createReceipt() {

        for (Sale s : NewSaleTable.getItems()) {
            Text ReceiptText = new Text(s.getName() + "\t\t\t\t\t\t\t" + s.getQuantity() + "\t\t\t\t\t\t\t" + s.getPrice());
            ReceiptItems.getChildren().add(ReceiptText);
        }
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            autoCompleteSearch();
            ShowSaleTable();
            addSaleItem();
            addButtonToTable();
            acceptPayment();
            //createReceipt();
        } catch (SQLException ex) {
            Logger.getLogger(Sales_person_dashController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
