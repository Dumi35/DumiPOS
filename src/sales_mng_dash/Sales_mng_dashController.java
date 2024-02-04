package sales_mng_dash;

import utility_classes.SwitchTabs;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
import javafx.scene.control.DatePicker;
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
    private TableView<ReceiptItem> DailyReportTable;
    @FXML
    private TableColumn<ReceiptItem, Integer> DailyQuantityCol;
    @FXML
    private TableColumn<ReceiptItem, String> DailyCodeCol;
    @FXML
    private TableColumn<ReceiptItem, String> DailyProdCol;
    @FXML
    private TableColumn<ReceiptItem, Integer> DailyAmountCol;
    @FXML
    private TableColumn<ReceiptItem, String> DailyStaffIDCol;
    @FXML
    private VBox WeeklyReportsTabPane;
    @FXML
    private VBox MonthlyReportsTabPane;
    @FXML
    private VBox YearlyReportsTabPane;
    @FXML
    private ComboBox<Integer> YearlySalesYear;
    @FXML
    private ComboBox<String> WeeklySalesMonth;
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
    @FXML
    private ComboBox<String> MonthlySalesMonth;
    @FXML
    private TableColumn<ReceiptItem, String> MonthlyCodeCol;
    @FXML
    private TableColumn<ReceiptItem, String> MonthlyProdCol;
    @FXML
    private TableColumn<ReceiptItem, Integer> MonthlyAmountCol;
    @FXML
    private TableColumn<ReceiptItem, String> MonthlyStaffIDCol;
    @FXML
    private TableColumn<ReceiptItem, Integer> MonthlyQuantityCol;
    @FXML
    private TableView<ReceiptItem> MonthlyReportTable;
    @FXML
    private DatePicker DailyDatePicker;
    @FXML
    private ComboBox<String> WeeklySalesWeek;
    @FXML
    private TableColumn<ReceiptItem, String> WeeklyCodeCol;
    @FXML
    private TableColumn<ReceiptItem, Integer> WeeklyQuantityCol;
    @FXML
    private TableColumn<ReceiptItem, Integer> WeeklyAmountCol;
    @FXML
    private TableColumn<ReceiptItem, String> WeeklyStaffIDCol;
    @FXML
    private TableView<ReceiptItem> WeeklyReportTable;
    @FXML
    private TableColumn<ReceiptItem, String> WeeklyProdCol;

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

    //add months to combo box in weekly, monthly, yearly reports
    public void addComboBoxMonths() {
        ObservableList<String> monthsList = FXCollections.observableArrayList();
        Month[] months = Month.values();
        for (Month month : months) {
            monthsList.add(String.valueOf(month));
        }
        WeeklySalesMonth.getItems().addAll(monthsList);
        MonthlySalesMonth.getItems().addAll(monthsList);
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

    //daily reports pane
    public void getDailySales() {
        DailyCodeCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        DailyProdCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        DailyQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        DailyAmountCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        DailyStaffIDCol.setCellValueFactory(new PropertyValueFactory<>("StaffID"));

        DailyDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            DailyReportTable.getItems().clear();
            ObservableList<ReceiptItem> riData = FXCollections.observableArrayList();
            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) // Set values for the second choice box based on the selected value of the first choice box
                {
                    try {
                        //connect to db
                        ps = con.prepareStatement("SELECT * FROM sale_receipts WHERE DATE(Date_Of_Sale) = '" + java.sql.Date.valueOf(DailyDatePicker.getValue()) + "'");//convert local date to sql date
                        rs = ps.executeQuery();

                        ReceiptItem ri;
                        while (rs.next()) {
                            ri = new ReceiptItem(rs.getString("Receipt_ID"), rs.getString("Item_Code"), rs.getString("Item_Name"), rs.getInt("Quantity"), rs.getInt("Amount"), rs.getString("Staff_ID"));
                            riData.add(ri);
                        }
                        //LOAD DATE INTO TABLE
                        DailyReportTable.getItems().addAll(riData);
                    } catch (SQLException ex) {
                        Logger.getLogger(Sales_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });

    }

    //weekly reports pane
    //load week ranges based on month and year
    public void getWeeklyRanges() {

        WeeklySalesMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            WeeklyReportTable.getItems().clear();

            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) {
                    getWeek();
                }
            });
        });

        WeeklySalesYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            WeeklyReportTable.getItems().clear();

            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) {
                    getWeek();
                }
            });
        });
    }

    public void getWeek() {
        WeeklySalesWeek.getItems().clear();
        int monthStart = 1;//first day of the month
        //int monthNo = Month.valueOf("FEBRUARY").getValue();
        int monthNo = Month.valueOf(WeeklySalesMonth.getValue()).getValue();
        //Month.
        //int yearNo = 2025;
        int yearNo = WeeklySalesYear.getValue();

        boolean isLeapYear = false;
        if (yearNo % 4 == 0) {
            isLeapYear = true;
        }
        //int monthLength = Month.valueOf(MonthlySalesMonth.getValue()).length(isLeapYear); //RETURNS NO. OF DAYS IN A MONTH true means the year is a leap year
        int monthLength = Month.of(monthNo).length(isLeapYear); //RETURNS NO. OF DAYS IN A MONTH true means the year is a leap year

        String weekRange = "";
        //ObservableList<String> weekRangesOfMonth= FXCollections.observableArrayList();
        for (int i = 1; i <= 5; i++) { //deals with number of weeks in a month
            //DATE INPUT BY USER

            LocalDate lclDate = LocalDate.of(yearNo, monthNo, monthStart);
            Locale enIsoLoc = Locale.forLanguageTag("en-u-fw-sun");
            WeekFields.of(enIsoLoc).getFirstDayOfWeek(); // returns Sunday

            //fieldISO, 1 return the date of the first day of the week which is a Sunday
            //fieldISO, 7 return the date of the last day of the week which is a Saturday
            TemporalField fieldISO = WeekFields.of(enIsoLoc).dayOfWeek();
            String weekStart = String.valueOf(lclDate.with(fieldISO, 1));
            String weekEnd = String.valueOf(lclDate.with(fieldISO, 7));

            weekRange = weekStart + " to " + weekEnd;
            WeeklySalesWeek.getItems().add(weekRange); //add values to combobox

            if (monthStart + 7 > monthLength) {
                break;
            }
            monthStart += 7;
        }

        //get week of end month use June 2024 as ref to understand since last day was 29th Jun from loop
        if (monthLength > monthStart) {
            LocalDate lclDate = LocalDate.of(yearNo, monthNo, monthLength);
            Locale enIsoLoc = Locale.forLanguageTag("en-u-fw-sun");
            WeekFields.of(enIsoLoc).getFirstDayOfWeek(); // returns Sunday

            TemporalField fieldISO = WeekFields.of(enIsoLoc).dayOfWeek();
            String weekStart = String.valueOf(lclDate.with(fieldISO, 1));
            String weekEnd = String.valueOf(lclDate.with(fieldISO, 7));

            weekRange = weekStart + " to " + weekEnd;
            WeeklySalesWeek.getItems().add(weekRange);
        }

    }

    public void loadWeeklyReportTable() {
        WeeklyCodeCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        WeeklyProdCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        WeeklyQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        WeeklyAmountCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        WeeklyStaffIDCol.setCellValueFactory(new PropertyValueFactory<>("StaffID"));

        //event listener for cmobobox
        WeeklySalesWeek.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            WeeklyReportTable.getItems().clear();
            String[] weekRange = WeeklySalesWeek.getValue().split(" to ");
            String weekStart = weekRange[0];
            String weekEnd = weekRange[1];
            System.out.println("start "+weekStart);
            System.out.println("start "+weekEnd);

            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) {
                    ObservableList<ReceiptItem> riData = FXCollections.observableArrayList();
                    try {
                        //connect to db
                        ps = con.prepareStatement("SELECT * FROM sale_receipts WHERE DATE(Date_Of_Sale) BETWEEN '" + weekStart + "' AND '" + weekEnd+"'");
                        System.out.println("SELECT * FROM sale_receipts WHERE Date_Of_Sale BETWEEN '" + weekStart + "' AND '" + weekEnd+"'");
                        rs = ps.executeQuery();
                        ReceiptItem ri;
                        while (rs.next()) {
                            ri = new ReceiptItem(rs.getString("Receipt_ID"), rs.getString("Item_Code"), rs.getString("Item_Name"), rs.getInt("Quantity"), rs.getInt("Amount"), rs.getString("Staff_ID"));
                            riData.add(ri);

                        }
                        WeeklyReportTable.getItems().addAll(riData);
                    } catch (SQLException ex) {
                        Logger.getLogger(Sales_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });
    }

    //monthly reports pane
    public void getMonthlySales() {
        MonthlyCodeCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        MonthlyProdCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        MonthlyQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        MonthlyAmountCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        MonthlyStaffIDCol.setCellValueFactory(new PropertyValueFactory<>("StaffID"));

        MonthlySalesMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            MonthlyReportTable.getItems().clear();

            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) {
                    loadMonthlyReportTable();
                }
            });
        });

        MonthlySalesYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            MonthlyReportTable.getItems().clear();

            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) {
                    loadMonthlyReportTable();
                }
            });
        });

    }

    public void loadMonthlyReportTable() {
        ObservableList<ReceiptItem> riData = FXCollections.observableArrayList();
        try {
            int monthno = Month.valueOf(MonthlySalesMonth.getValue()).getValue(); //mth no. for each mth eg 1 for jan
            //connect to db
            ps = con.prepareStatement("SELECT * FROM sale_receipts WHERE MONTH(Date_Of_Sale) = " + monthno + " AND YEAR(Date_Of_Sale) = " + String.valueOf(MonthlySalesYear.getValue()));
            rs = ps.executeQuery();
            ReceiptItem ri;
            while (rs.next()) {
                ri = new ReceiptItem(rs.getString("Receipt_ID"), rs.getString("Item_Code"), rs.getString("Item_Name"), rs.getInt("Quantity"), rs.getInt("Amount"), rs.getString("Staff_ID"));
                riData.add(ri);

            }
            MonthlyReportTable.getItems().addAll(riData);
        } catch (SQLException ex) {
            Logger.getLogger(Sales_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            addComboBoxMonths();
            getDailySales();
            getWeeklyRanges();
            getMonthlySales();
            getYearlySales();
            loadWeeklyReportTable();
        } catch (SQLException ex) {
            Logger.getLogger(Sales_mng_dashController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
