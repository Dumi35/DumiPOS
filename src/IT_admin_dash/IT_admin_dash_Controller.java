package IT_admin_dash;

//import com.sun.webkit.network.DateParser;
import components.alerts.AlertController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import utility_classes.DatabaseConn;
import utility_classes.EmailSending;
import utility_classes.Hashing;
import utility_classes.Images;
import static utility_classes.Images.photo;
import model_classes.Staff;
import utility_classes.SwitchTabs;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import utility_classes.ValidDate;
import javafx.scene.image.Image;

public class IT_admin_dash_Controller implements Initializable {

    @FXML
    private Circle profile_pic;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //TOOLS FOR DATABASE
    private Connection con;
    //private Statement statement;
    private PreparedStatement ps;
    private ResultSet result;

    //TOOLS FOR EMAIL
    private Session session = EmailSending.emailSend();

    @FXML
    private TextField staffID1;
    @FXML
    private TextField staffName1;
    @FXML
    private TextField staffEmail1;
    @FXML
    private TextField staffPhoneNo1;

    @FXML
    private DatePicker staffDOB1;
    @FXML
    private ChoiceBox<String> staffGender1;
    @FXML
    private ChoiceBox<String> staffDepartment1;
    @FXML
    private ChoiceBox<String> staffRole1;
    @FXML
    private Rectangle staffPassport1;

    private static byte[] photo = null;
    @FXML
    private VBox sideBar;
    @FXML
    private StackPane StackPane;
    @FXML
    private TableView<Staff> AllUsersTable;
    @FXML
    private TableColumn<Staff, String> ID_col;
    @FXML
    private TableColumn<Staff, String> Name_col;
    @FXML
    private TableColumn<Staff, String> Email_col;
    @FXML
    private TableColumn<Staff, String> Phone_No_col;
    @FXML
    private TableColumn<Staff, Date> DOB_col;
    @FXML
    private TableColumn<Staff, String> Gender_col;
    @FXML
    private TableColumn<Staff, String> Dept_col;
    @FXML
    private TableColumn<Staff, String> Role_col;
    @FXML
    private HBox DashboardTab;
    @FXML
    private HBox RegisterUserTab;
    @FXML
    private HBox UpdateUserTab;
    @FXML
    private HBox BackUpTab;
    @FXML
    private VBox DashboardTabPane;
    @FXML
    private VBox RegisterUserTabPane;
    @FXML
    private VBox UpdateUserTabPane;
    @FXML
    private VBox BackUpTabPane;
    @FXML
    private Label Folder_Name_Label;
    @FXML
    private ComboBox<String> hourComboBox;
    @FXML
    private ComboBox<String> minComboBox;
    @FXML
    private TextField searchUserBar;
    @FXML
    private TextField staffID2;
    @FXML
    private TextField staffName2;
    @FXML
    private TextField staffEmail2;
    @FXML
    private TextField staffPhoneNo2;
    @FXML
    private DatePicker staffDOB2;
    @FXML
    private ChoiceBox<String> staffGender2;
    @FXML
    private ChoiceBox<String> staffDepartment2;
    @FXML
    private ChoiceBox<String> staffRole2;
    @FXML
    private Rectangle staffPassport2;
    @FXML
    private Button updateStaffBtn;
    @FXML
    private Button ResetPasswordBtn;

    public void setData() {

        Image profileImage = new Image(getClass().getResource("../images/starfire.jpg").toExternalForm(), 150, 150, true, true);

        profile_pic.setFill(new ImagePattern(profileImage));

    }

    //update and register staff pane functions
    @FXML
    public void Browse() {
        Images im = new Images();
        photo = im.BrowseSystemImages(staffPassport1);
    }

    @FXML
    public void OpenWebCam() throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("WebCam.fxml"));
            stage = new Stage();
            scene = new Scene(root);
            stage.resizableProperty().setValue(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("error loading " + e);
        }

    }

    public void ControlRoleChoiceBoxes() {
        staffDepartment1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) // Set values for the second choice box based on the selected value of the first choice box
                {
                    switch (newValue) {
                        case "Inventory" -> {
                            staffRole1.getItems().setAll("Manager");
                            staffRole1.setValue("Manager");
                        }
                        case "Sales" -> {
                            staffRole1.getItems().setAll("Sales Manager", "Sales Person");
                            staffRole1.setValue("Sales Manager");
                        }
                        case "IT" -> {
                            staffRole1.getItems().setAll("Admin");
                            staffRole1.setValue("Admin");
                        }
                        default -> {
                        }
                    }
                }
            });
        });

        staffDepartment2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Run the code later on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (null != newValue) // Set values for the second choice box based on the selected value of the first choice box
                {
                    switch (newValue) {
                        case "Inventory" -> {
                            staffRole2.getItems().setAll("Manager");
                            staffRole2.setValue("Manager");
                        }
                        case "Sales" -> {
                            staffRole2.getItems().setAll("Sales Manager", "Sales Person");
                            staffRole2.setValue("Sales Manager");
                        }
                        case "IT" -> {
                            staffRole2.getItems().setAll("Admin");
                            staffRole2.setValue("Admin");
                        }
                        default -> {
                        }
                    }
                }
            });
        });

    }

    //register staff pane functions
    @FXML
    public void CreateStaff() throws NoSuchAlgorithmException {

        String password = Hashing.generateRandomPassword(9);
        String salt = Hashing.generateSalt();
        String salted = Hashing.SaltPassword(password, salt);
        String hashedPassword = Hashing.PepperPassword(salted); //final hashed password s returned here

        Staff staff = new Staff(staffID1.getText(), staffName1.getText(), staffEmail1.getText(), staffPhoneNo1.getText(), Date.valueOf(staffDOB1.getValue()), staffGender1.getValue(), staffDepartment1.getValue(), staffRole1.getValue(), photo, hashedPassword, salt);

        if (staffEmail1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
        } else {
            try {
                con = DatabaseConn.connectDB();
                session = EmailSending.emailSend();
                ps = con.prepareStatement("insert into staff values(?,?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, staff.getStaffID());
                ps.setString(2, staff.getName());
                ps.setString(3, staff.getEmail());
                ps.setString(4, staff.getPhone_No());
                ps.setDate(5, (Date) staff.getDOB());
                ps.setString(6, staff.getGender());
                ps.setString(7, staff.getDept());
                ps.setString(8, staff.getRole());
                ps.setBytes(9, staff.getPassport());
                ps.setString(10, staff.getHash_Password());
                ps.setString(11, staff.getSalt());

                //send email
                try {
                    String body = "Dear " + staff.getName() + ", your registration as student for Dumi POS was successful. \nPlease find your login details below: \nEmail Address: " + staff.getEmail() + "\nPassword: " + password;

                    Message message = (Message) new MimeMessage(session);

                    message.setFrom(new InternetAddress("dumebi328@gmail.com"));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(staff.getEmail()));
                    message.setSubject("Registration for Dumi POS Successful");
                    message.setText(body);

                    Transport.send(message);

                    ps.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration");
                    alert.setContentText("User successfully created");
                    alert.showAndWait();

                } catch (MessagingException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText(e.toString());
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                //e.printStackTrace();
                System.out.println("error " + e);
            }
        }
    }

    public void setDefaultPassport() throws FileNotFoundException, IOException {
        String filePath = "C:\\Users\\dumid\\Documents\\NetBeansProjects\\Dumi_POS_System\\src\\images\\user.jpg";
        Image image = new Image("file:" + filePath, 150, 150, true, true);

        staffPassport1.setFill(new ImagePattern(image)); //set default passport image

        File imageFile = new File(filePath);
        FileInputStream fis = new FileInputStream(imageFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] Byte = new byte[1024];

        for (int i; (i = fis.read(Byte)) != -1;) {
            baos.write(Byte, 0, i);
        }
        photo = baos.toByteArray();
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
                        ShowStaffData();

                    } catch (SQLException ex) {
                        Logger.getLogger(IT_admin_dash_Controller.class.getName()).log(Level.SEVERE, null, ex);
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
                    } catch (SQLException ex) {
                        Logger.getLogger(IT_admin_dash_Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        };

        timer.schedule(task, 3000);// Schedule the task to run once after 3 seconds 

    }

    public void DisplayBirthdays() throws SQLException {
        //load birthday alert
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
        stage = (Stage) StackPane.getScene().getWindow();
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(IT_admin_dash_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        AlertController ac = loader.getController();
        if (ValidDate.isBirthday() != null) {
            try {
                ac.SetContent("Today's bday celebs: " + ValidDate.isBirthday(), root, stage);
            } catch (SQLException ex) {
                Logger.getLogger(IT_admin_dash_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //update pane functions
    java.sql.Blob columnValue = null;//to load passport image

    public void SearchStaff() {
        searchUserBar.addEventFilter(KeyEvent.KEY_PRESSED, KeyEvent -> {
            KeyCode Enter = KeyCode.ENTER;
            if ((KeyEvent.getCode()).equals(Enter)) {

                con = DatabaseConn.connectDB();
                //read from Database table
                try {
                    ps = con.prepareStatement("SELECT * FROM staff where Staff_ID =?");
                    ps.setString(1, searchUserBar.getText());
                    result = ps.executeQuery();

                    if (result.next()) {
                        updateStaffBtn.setDisable(false);
                        ResetPasswordBtn.setDisable(false);
                        staffID2.setText(result.getString("Staff_ID"));
                        staffName2.setText(result.getString("Staff_Name"));
                        staffEmail2.setText(result.getString("Email"));
                        staffPhoneNo2.setText(result.getString("Phone_no"));
                        java.sql.Date sqlDate = result.getDate("Date_Of_Birth");
                        LocalDate localDate = sqlDate.toLocalDate();
                        staffDOB2.setValue(localDate);
                        staffGender2.setValue(result.getString("Gender"));
                        staffDepartment2.setValue(result.getString("Department"));
                        staffRole2.setValue(result.getString("Role"));

                        //load passport image
                        columnValue = result.getBlob("Passport");

                        if (columnValue != null) {
                            byte[] imageData = columnValue.getBytes(1, (int) columnValue.length());
                            Image image = new Image(new ByteArrayInputStream(imageData));
                            staffPassport2.setFill(new ImagePattern(image));
                        }

                    } else {
                        updateStaffBtn.setDisable(true);
                        ResetPasswordBtn.setDisable(true);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    byte[] newPassport = null;

    @FXML
    public void changeProfile() {
        Images im = new Images();
        newPassport = im.BrowseSystemImages(staffPassport2);
    }

    @FXML
    public void UpdateStaff() throws NoSuchAlgorithmException, IOException {
        //load alert pane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
        root = loader.load();
        stage = (Stage) staffID2.getScene().getWindow();
        AlertController ac = loader.getController();
        ac.SetContent("Are you sure you want to update?", root, stage);

        if (ac.isOkClicked()) {
            try {
                con = DatabaseConn.connectDB();
                ps = con.prepareStatement("update staff set Staff_ID=?, Staff_Name=?, Email=?,Phone_no=?,Date_Of_Birth=?, Gender=?, Department=?, Role=?, Passport=? where Staff_ID = ?");
                ps.setString(1, staffID2.getText());
                ps.setString(2, staffName2.getText());
                ps.setString(3, staffEmail2.getText());
                ps.setString(4, staffPhoneNo2.getText());
                ps.setDate(5, Date.valueOf(staffDOB2.getValue()));
                ps.setString(6, staffGender2.getValue());
                ps.setString(7, staffDepartment2.getValue());
                ps.setString(8, staffRole2.getValue());

                if (newPassport != null) {
                    ps.setBytes(9, newPassport);
                } else {
                    ps.setBlob(9, columnValue);//return original value, i.e no change to passport photo
                }

                ps.setString(10, searchUserBar.getText());
                ps.executeUpdate();

            } catch (SQLException e) {
                //e.printStackTrace();
                System.out.println("error " + e);
            }
        }
    }

    public void ResetPassword() throws NoSuchAlgorithmException, SQLException, IOException {
        String password = Hashing.generateRandomPassword(9);
        String salt = Hashing.generateSalt();
        String salted = Hashing.SaltPassword(password, salt);
        String hashedPassword = Hashing.PepperPassword(salted);
        
        //send new password to database
        ps = con.prepareStatement("update staff set Hash_Password = ?, Salt=? where Staff_ID = ?");
        ps.setString(1, hashedPassword);
        ps.setString(2, salt);
        ps.setString(3, staffID2.getText());
        //send email
        try {
            String body = "Dear " + staffName2.getText() + ", your password has been reset for Dumi POS. \nPlease find your new login details below: \nEmail Address: " + staffEmail2.getText() + "\nPassword: " + password;

            Message message = (Message) new MimeMessage(session);

            message.setFrom(new InternetAddress("dumebi328@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(staffEmail2.getText()));
            message.setSubject("Password Reset for Dumi POS Successful");
            message.setText(body);

            Transport.send(message);

            ps.executeUpdate();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
            root = loader.load();
            stage = (Stage) staffID2.getScene().getWindow();
            AlertController ac = loader.getController();
            ac.SetContent("Password reset successfully", root, stage);

        } catch (SQLException | MessagingException e) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
            root = loader.load();
            stage = (Stage) staffID2.getScene().getWindow();
            AlertController ac = loader.getController();
            ac.SetContent("Error resetting password", root, stage);
            e.printStackTrace();
        }
    }

    //dashboard pane functions
    //retrieve all staff data from database
    public ObservableList<Staff> AllStaffListData() throws SQLException {
        ObservableList<Staff> listData = FXCollections.observableArrayList();

        con = DatabaseConn.connectDB();
        //read from Database table
        try {
            ps = con.prepareStatement("SELECT * FROM staff");
            result = ps.executeQuery();

            Staff allSData;

            while (result.next()) {
                allSData = new Staff(result.getString("Staff_ID"), result.getString("Staff_Name"), result.getString("Email"), result.getString("Phone_no"), result.getDate("Date_Of_Birth"), result.getString("Gender"), result.getString("Department"), result.getString("Role"), result.getBytes("Passport"), result.getString("Hash_Password"), result.getString("Salt"));
                listData.add(allSData);
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

    private ObservableList<Staff> StaffData;

    //show all staff Data in tableview
    public void ShowStaffData() throws SQLException {
        StaffData = AllStaffListData();
        ID_col.setCellValueFactory(new PropertyValueFactory<>("StaffID"));
        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Email_col.setCellValueFactory(new PropertyValueFactory<>("Email"));
        Phone_No_col.setCellValueFactory(new PropertyValueFactory<>("Phone_No"));
        DOB_col.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        Gender_col.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        Dept_col.setCellValueFactory(new PropertyValueFactory<>("Dept"));
        Role_col.setCellValueFactory(new PropertyValueFactory<>("Role"));

        AllUsersTable.setItems(StaffData);
    }

    //backup pane functions
    public void setBackUpTimes() {
        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> mins = FXCollections.observableArrayList();

        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                mins.add("0" + String.valueOf(i));
            } else {
                mins.add(String.valueOf(i));
            }
        }
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hours.add("0" + String.valueOf(i));
            } else {
                hours.add(String.valueOf(i));
            }
        }
        hourComboBox.setItems(hours);
        minComboBox.setItems(mins);
    }

    @FXML
    public void selectBackUpFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Directory");

        stage = (Stage) StackPane.getScene().getWindow();
        // Show the directory chooser dialog
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            Folder_Name_Label.setText(selectedDirectory.getAbsolutePath());
        } else {
            System.out.println("No directory selected");
        }
    }

    Timer backUpTimer = new Timer();

    @FXML
    public void Backup() throws ParseException, IOException {
        int min = Integer.parseInt(minComboBox.getValue());
        int hour = Integer.parseInt(hourComboBox.getValue());

        //load alert pane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
        root = loader.load();
        stage = (Stage) hourComboBox.getScene().getWindow();
        AlertController ac = loader.getController();
        ac.SetContent("Automatic backups set to " + hourComboBox.getValue() + ":" + minComboBox.getValue(), root, stage);

        //stop already existing timer to avoid creating 2 timer threads
        backUpTimer.cancel();
        // Schedule a new timer task with updated parameters
        backUpTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    LocalTime targetTime = LocalTime.of(hour, min); // 5:30 PM
                    LocalTime currentTime = LocalTime.of(java.time.LocalTime.now().getHour(), java.time.LocalTime.now().getMinute());

                    //check if time equals system time
                    if (currentTime.equals(targetTime)) {
                        String command = "mysqldump -u root -p#BonBon214 pos_system --result-file=" + Folder_Name_Label.getText() + "\\" + "POS-" + java.time.LocalDate.now() + ".sql"; //save backup file
                        Runtime.getRuntime().exec(command);
                        //System.out.println("we are equal "+ targetTime + currentTime);

                    } else {
                        //System.out.println("we arenot equal "+ targetTime + currentTime);
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        backUpTimer.scheduleAtFixedRate(task, 0, 3000);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setData();
        staffDepartment1.getItems().addAll("Inventory", "Sales", "IT");
        staffDepartment1.setValue("Inventory");
        staffRole1.getItems().addAll("Manager");
        staffRole1.setValue("Manager");
        staffGender1.getItems().addAll("Male", "Female");
        staffGender1.setValue("Female");

        staffDepartment2.getItems().addAll("Inventory", "Sales", "IT");
        staffDepartment2.setValue("Inventory");
        staffRole2.getItems().addAll("Manager");
        staffRole2.setValue("Manager");
        staffGender2.getItems().addAll("Male", "Female");
        staffGender2.setValue("Female");
        SearchStaff();

        ControlRoleChoiceBoxes();
        SwitchTabs.switchTabs(sideBar, StackPane);
        try {
            UpdateUI();
        } catch (IOException ex) {
            Logger.getLogger(IT_admin_dash_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            setDefaultPassport();
        } catch (IOException ex) {
            Logger.getLogger(IT_admin_dash_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackUpTimes();//displays options for hour and min comboboxes in backup
    }
}
