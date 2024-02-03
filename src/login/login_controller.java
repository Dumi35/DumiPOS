package login;

import IT_admin_dash.IT_admin_dash_Controller;
import Inventory_mng_dash.Inventory_mng_dashController;
import utility_classes.DatabaseConn;
import utility_classes.Hashing;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import components.alerts.AlertController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import sales_person_dash.Sales_person_dashController;

public class login_controller implements Initializable {

    @FXML
    private TextField loginEmail;
    @FXML
    private TextField loginPassword;

    //TOOLS FOR DATABASE
    private Connection con;
    //private Statement statement;
    private PreparedStatement ps;
    private ResultSet rs;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void Login() {
        try {
            con = DatabaseConn.connectDB();
            ps = con.prepareStatement("select * from staff where Email = ?");
            ps.setString(1, loginEmail.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("Hash_Password");
                String storedSalt = rs.getString("Salt");
                String salted = Hashing.SaltPassword(loginPassword.getText(), storedSalt);
                String hashedPassword = Hashing.PepperPassword(salted);

                if (hashedPassword.equals(storedPassword)) {

                    try {
                        //load alert pane
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
                        root = loader.load();
                        stage = (Stage) loginEmail.getScene().getWindow();
                        AlertController ac = loader.getController();
                        ac.SetContent("Login successful", root, stage);

                    } catch (IOException e) {
                        System.out.println("error loading " + e);
                    }
                    
                    //load respective dashboards
                    stage = (Stage) loginEmail.getScene().getWindow();

                    //GET profile pic/passport
                    java.sql.Blob columnValue = null;
                    columnValue = rs.getBlob("Passport");
                    Image profileImage = null;

                    if (columnValue != null) {
                        byte[] imageData = columnValue.getBytes(1, (int) columnValue.length());
                        profileImage = new Image(new ByteArrayInputStream(imageData));
                    }

                    //open dashboard and load profile pic
                    switch (rs.getString("Role")) {
                        case "Sales Person" -> {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sales_person_dash/sales_person_dash.fxml"));
                            root = loader.load();
                            Sales_person_dashController saleMngController = loader.getController();
                            saleMngController.setData(rs.getString("Staff_Name"), profileImage,rs.getString("Staff_ID"));

                        }
                        case "Sales Manager" -> {
                            root = FXMLLoader.load(getClass().getResource("../sales_mng_dash/sales_mng_dash.fxml"));
                        }
                        case "Manager" -> {//for inventory manager
                            //root = FXMLLoader.load(getClass().getResource("../Inventory_mng_dash/Inventory_mng_dash.fxml"));
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Inventory_mng_dash/Inventory_mng_dash.fxml"));
                            root = loader.load();
                            Inventory_mng_dashController invMngController = loader.getController();
                            invMngController.setData(rs.getString("Staff_Name"), profileImage);
                        }
                        case "Admin" -> {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../IT_admin_dash/IT_admin_dash.fxml"));
                            root = loader.load();
                            IT_admin_dash_Controller adminController = loader.getController();
                            adminController.setData(rs.getString("Staff_Name"), profileImage);
                        }
                        default -> {

                        }

                    }

                    scene = new Scene(root);
                    stage.resizableProperty().setValue(true);
                    stage.setX(0);
                    stage.setY(0);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    //load alert pane
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
                    root = loader.load();
                    stage = (Stage) loginEmail.getScene().getWindow();
                    AlertController ac = loader.getController();
                    ac.SetContent("Invalid login details", root, stage);
                }

            } else {
                //load alert pane
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/alerts/alert.fxml"));
                root = loader.load();
                stage = (Stage) loginEmail.getScene().getWindow();
                AlertController ac = loader.getController();
                ac.SetContent("Invalid login details", root, stage);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
