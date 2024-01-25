package dumi_pos_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author dumid
 */
public class Dumi_POS_System extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Dumebi POS");
        stage.resizableProperty().setValue(false);
        stage.centerOnScreen();
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
