/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sales_mng_dash;

import utility_classes.SwitchTabs;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model_classes.Sale;
import model_classes.ReceiptItem;
import utility_classes.DatabaseConn;

/**
 * FXML Controller class
 *
 * @author dumid
 */
public class Sales_mng_dashController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //TOOLS FOR DATABASE
    private Connection con = DatabaseConn.connectDB();
    //private Statement statement;
    private PreparedStatement ps;
    private ResultSet rs;

    @FXML
    private Circle profile_pic;
    @FXML
    private AreaChart<?, ?> areaChart1;
    @FXML
    private AreaChart<?, ?> areaChart2;
    @FXML
    private AreaChart<?, ?> areaChart3;
    @FXML
    private VBox sideBar;
    @FXML
    private StackPane StackPane;
    @FXML
    private TableView<Sale> TopProductsTable;
    @FXML
    private TableColumn<Sale, String> TopProductsCode;
    @FXML
    private TableColumn<Sale, String> TopProductsName;
    @FXML
    private TableColumn<Sale, Integer> TopProductsQuantity;
    @FXML
    private TableColumn<Sale, Integer> TopProductsAmount;
    @FXML
    private Label TotalSalesAmount;
    @FXML
    private Label TotalProductsNumber;
    @FXML
    private HBox DashboardTab;
    @FXML
    private HBox DailyReportsTab;
    @FXML
    private HBox WeeklyReportsTab;
    @FXML
    private HBox MonthlyReportsTab;
    @FXML
    private HBox YearlyReportsTab;
    @FXML
    private FlowPane DashboardTabPane;
    @FXML
    private VBox DailyReportsTabPane;
    @FXML
    private TableView<?> AllUsersTable;
    @FXML
    private TableColumn<?, ?> DailyCodeCol;
    @FXML
    private TableColumn<?, ?> DailyProdCol;
    @FXML
    private TableColumn<?, ?> DailyQtyCol;
    @FXML
    private TableColumn<?, ?> DailyAmountCol;
    @FXML
    private TableColumn<?, ?> DailyStaffIDCol;
    @FXML
    private VBox WeeklyReportsTabPane;
    @FXML
    private TableView<?> AllUsersTable1;
    @FXML
    private TableColumn<?, ?> DailyCodeCol1;
    @FXML
    private TableColumn<?, ?> DailyProdCol1;
    @FXML
    private TableColumn<?, ?> DailyQtyCol1;
    @FXML
    private TableColumn<?, ?> DailyAmountCol1;
    @FXML
    private TableColumn<?, ?> DailyStaffIDCol1;
    @FXML
    private VBox MonthlyReportsTabPane;
    @FXML
    private TableView<?> AllUsersTable11;
    @FXML
    private TableColumn<?, ?> DailyCodeCol11;
    @FXML
    private TableColumn<?, ?> DailyProdCol11;
    @FXML
    private TableColumn<?, ?> DailyQtyCol11;
    @FXML
    private TableColumn<?, ?> DailyAmountCol11;
    @FXML
    private TableColumn<?, ?> DailyStaffIDCol11;
    @FXML
    private VBox YearlyReportsTabPane;
    @FXML
    private ComboBox<Integer> YearlySalesYear;
    @FXML
    private ComboBox<?> WeeklySalesMonth;
    @FXML
    private ComboBox<Integer> WeeklySalesYear;
    @FXML
    private ComboBox<Integer> MonthlySalesYear;
    @FXML
    private TableView<ReceiptItem> YearlyReportTable;
    @FXML
    private TableColumn<ReceiptItem, String> YearlyCodeCol;
    @FXML
    private TableColumn<ReceiptItem, String> YearlyProdCol;
    @FXML
    private TableColumn<ReceiptItem, Integer> YearlyQuantityCol;
    @FXML
    private TableColumn<ReceiptItem, Integer> YearlyAmountCol;
    @FXML
    private TableColumn<ReceiptItem, String> YearlyStaffIDCol;

    public void setData() {

        Image profileImage = new Image(getClass().getResource("../images/starfire.jpg").toExternalForm(), 150, 150, true, true);

        profile_pic.setFill(new ImagePattern(profileImage));
    }

    private void setAreaChart1() {
        XYChart.Series series = new XYChart.Series();
        // Generate data points for the sine wave
        for (int angle = 0; angle <= 360; angle += 20) {
            double radians = Math.toRadians(angle);
            double sineValue = Math.sin(radians);
            series.getData().add(new XYChart.Data<>(String.valueOf(angle), sineValue));
        }

        areaChart1.getYAxis().setTickLabelsVisible(false); //HIDE NUMBERS ON AXIS
        areaChart1.getYAxis().setOpacity(0);
        areaChart1.getXAxis().setTickLabelsVisible(false);
        areaChart1.getXAxis().setOpacity(0);

        areaChart1.setCreateSymbols(false);

        areaChart1.getData().addAll(series);

    }

    private void setAreaChart2() {
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data<>("0", 0));
        series.getData().add(new XYChart.Data<>("3", 5));
        series.getData().add(new XYChart.Data<>("5", 2));
        series.getData().add(new XYChart.Data<>("8", 7));
        series.getData().add(new XYChart.Data<>("10", 5));
        series.getData().add(new XYChart.Data<>("13", 8));
        series.getData().add(new XYChart.Data<>("14", 8));
        series.getData().add(new XYChart.Data<>("15", 8));

        areaChart2.getYAxis().setTickLabelsVisible(false); //HIDE NUMBERS ON AXIS
        areaChart2.getYAxis().setOpacity(0);
        areaChart2.getXAxis().setTickLabelsVisible(false);
        areaChart2.getXAxis().setOpacity(0);

        areaChart2.setCreateSymbols(false);
        areaChart2.getData().addAll(series);
    }

    private void setAreaChart3() {
        XYChart.Series series = new XYChart.Series();

        for (double x = -3; x <= 7; x += 1) {
            double y = (2.0 * (x * x)) - (5.0 * x) + 2.0;
            series.getData().add(new XYChart.Data<>(String.valueOf(x), y));
        }

        areaChart3.getYAxis().setTickLabelsVisible(false); //HIDE NUMBERS ON AXIS
        areaChart3.getYAxis().setOpacity(0);
        areaChart3.getXAxis().setTickLabelsVisible(false);
        areaChart3.getXAxis().setOpacity(0);

        areaChart3.setCreateSymbols(false);
        areaChart3.getData().addAll(series);
    }

    public void getTotalStats() throws SQLException {
        ps = con.prepareStatement("select SUM(Amount) AS TotalSalesAmount FROM sale_receipts");
        rs = ps.executeQuery();
        while (rs.next()) {
            TotalSalesAmount.setText(rs.getString("TotalSalesAmount"));
        }
        ps = con.prepareStatement("SELECT COUNT(*) AS row_count FROM products");
        rs = ps.executeQuery();
        while (rs.next()) {
            TotalProductsNumber.setText(rs.getString("row_count"));
        }
    }

    public void getTopSellingProducts() throws SQLException {
        TopProductsCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        TopProductsName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TopProductsQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        TopProductsAmount.setCellValueFactory(new PropertyValueFactory<>("Price"));

        //ObservableList<String> TopProductsList = FXCollections.observableArrayList();
        Sale allPSale;

        ps = con.prepareStatement("SELECT Item_Code, Item_Name, SUM(Quantity) AS total_quantity, SUM(Amount) AS total_amount FROM sale_receipts GROUP BY Item_Code, Item_Name ORDER BY total_quantity DESC LIMIT 5;");
        rs = ps.executeQuery();
        while (rs.next()) {
            allPSale = new Sale(rs.getString("Item_Code"), rs.getString("Item_Name"), rs.getInt("total_quantity"), rs.getInt("total_amount"));
            TopProductsTable.getItems().add(allPSale);
        }
    }

    public void ShowSaleTable() throws SQLException {
        TopProductsCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        TopProductsName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TopProductsQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        TopProductsAmount.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }

    //add years to combo box in weekly, monthly, yearly reports
    public void addComboBoxYears() {
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 1993; i <= java.time.LocalDate.now().getYear(); i++) {
            years.add(i);
        }
        WeeklySalesYear.getItems().addAll(years);
        MonthlySalesYear.getItems().addAll(years);
        YearlySalesYear.getItems().addAll(years);
    }

    //yearly reports pane
    public void getYearlySales() {
        YearlyCodeCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        YearlyProdCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        YearlyQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        YearlyAmountCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        YearlyStaffIDCol.setCellValueFactory(new PropertyValueFactory<>("StaffID"));

        YearlySalesYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            YearlyReportTable.getItems().clear();
            ObservableList<ReceiptItem> riData = FXCollections.observableArrayList();
            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) // Set values for the second choice box based on the selected value of the first choice box
                {
                    try {
                        //connect to db
                        ps = con.prepareStatement("SELECT * FROM sale_receipts WHERE YEAR(Date_Of_Sale) = " + String.valueOf(YearlySalesYear.getValue()));
                        rs = ps.executeQuery();
                        ReceiptItem ri;
                        while (rs.next()) {
                            ri = new ReceiptItem(rs.getString("Receipt_ID"), rs.getString("Item_Code"), rs.getString("Item_Name"), rs.getInt("Quantity"), rs.getInt("Amount"), rs.getString("Staff_ID"));
                            riData.add(ri);

                        }
                        YearlyReportTable.getItems().addAll(riData);
                    } catch (SQLException ex) {
                        Logger.getLogger(Sales_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            setData();
            setAreaChart1();
            setAreaChart2();
            setAreaChart3();
            SwitchTabs.switchTabs(sideBar, StackPane);
            getTotalStats();
            getTopSellingProducts();
            addComboBoxYears();
            getYearlySales();
        } catch (SQLException ex) {
            Logger.getLogger(Sales_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
