package dumi_pos_system;

import components.alerts.AlertController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility_classes.Hashing;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.scene.image.Image;
import utility_classes.ValidDate;

/**
 *
 * @author dumid
 */
public class Dumi_POS_System extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("../sales_mng_dash/sales_mng_dash.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../sales_person_dash/sales_person_dash.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("../IT_admin_dash/IT_admin_dash.fxml"));
        
        
        //Parent root = FXMLLoader.load(getClass().getResource("../components/alerts/alert.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Dumebi POS");
        stage.resizableProperty().setValue(false);
        stage.centerOnScreen();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/PAULogo.png")));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
        launch(args);
       // ValidDate.isBirthday();
//        String password = Hashing.generateRandomPassword(9);
//        String salt = Hashing.generateSalt();
//        String salted = Hashing.SaltPassword(password, salt);
//        Hashing.PepperPassword(salted); //final hashed password s returned here
    }

}
