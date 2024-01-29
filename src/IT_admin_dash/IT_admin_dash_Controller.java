package IT_admin_dash;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    private Session session;

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
//    @FXML
//    private ImageView staffPassport1;

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
    private TableColumn<Staff, Date> DOB_col;
    private TableColumn<Staff, String> Gender_col;
    private TableColumn<Staff, String> Dept_col;
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
                            staffRole1.getItems().setAll("Manager", "Sales Person");
                            staffRole1.setValue("Manager");
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
    public void UpdateUI() {
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
    }   
    
    
    public void stopTimer(){
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try{
                    System.out.println("Close button clicked!");
                    root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
                    stage = new Stage();//(Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.resizableProperty().setValue(false);
                    stage.setScene(scene);
                    stage.show();
                    
                }catch (IOException ex) {
                }
                
            }
        });
    
    }

    //dashboard pane functions
    //retrieve all staff data from database
    public ObservableList<Staff> AllStaffListData() throws SQLException {
        ObservableList<Staff> listData = FXCollections.observableArrayList();

        con = DatabaseConn.connectDB();
        //read from Database table
        try {
            ps = con.prepareStatement("SELECT * FROM staff");// + QuizTableName.getText());
            result = ps.executeQuery();

            Staff allSData;
            //java.sql.Date sqlDate = result.getDate("Date_Of_Birth");
            // LocalDate localDate = sqlDate.toLocalDate();

            while (result.next()) {
                allSData = new Staff(result.getString("Staff_ID"), result.getString("Staff_Name"), result.getString("Email"), result.getString("Phone_no"), result.getDate("Date_Of_Birth"), result.getString("Gender"), result.getString("Department"), result.getString("Role"), result.getBytes("Passport"), result.getString("Hash_Password"), result.getString("Salt"));
                listData.add(allSData);
            }

        } catch (Exception e) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setData();
        staffDepartment1.getItems().addAll("Inventory", "Sales", "IT");
        staffDepartment1.setValue("Inventory");
        staffRole1.getItems().addAll("Manager", "SalesPerson");
        staffRole1.setValue("Manager");
        staffGender1.getItems().addAll("Male", "Female");
        staffGender1.setValue("Female");

        ControlRoleChoiceBoxes();
        SwitchTabs.switchTabs(sideBar, StackPane);
        UpdateUI();

        try {
            setDefaultPassport();
        } catch (IOException ex) {
            Logger.getLogger(IT_admin_dash_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
