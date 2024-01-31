/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package components.alerts;


import java.awt.Insets;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AlertController extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dialog Example");

        ButtonType okButtonType = new ButtonType("OK");
        ButtonType cancelButtonType = new ButtonType("CANCEL");

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Dialog Title");
        dialog.setContentText("Dialog Content");
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButtonType) {
                return "OK clicked";
            }
            return null;
        });

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();

        dialog.showAndWait().ifPresent(result -> {
            System.out.println("Result: " + result);
        });
    }

    public static void main(String args[]) {
        launch(args);
    }
}
