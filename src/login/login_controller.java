/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package login;

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
                    System.out.println("login successful");
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../alerts/alert.fxml"));
                        root = loader.load();
                        AlertController ac = loader.getController();
                        ac.SetContent("Login successful");
                        stage = new Stage();
                        scene = new Scene(root);
                        stage.resizableProperty().setValue(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        System.out.println("error loading " + e);
                    }
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setContentText("Invalid login details");
                alert.showAndWait();
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
