/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package components.alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AlertController implements Initializable {

    @FXML
    private DialogPane alertPane;

    private Stage stage;
    private Scene scene;
    private Stage primaryStage;
    //private Parent root;

    boolean okClicked = true;//when ok button is clicked
    @FXML
    private Label alertContentLabel;

    public void SetContent(String alertContent, Parent root, Stage callerStage) { //parameters are message, the caller root and the caller stage
        
        //alertPane.setContentText(alertContent);
        alertContentLabel.setText(alertContent);
        //disable caller fxml
        callerStage.getScene().getRoot().setDisable(true);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(callerStage);
        stage = callerStage;
        primaryStage = new Stage();
        scene = new Scene(root);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        //close pane
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                    System.out.println("Close button clicked!");
                    stage.getScene().getRoot().setDisable(false);                   
                
            }
        });
    }

    
    public boolean isOkClicked() {
        return okClicked;
    }

    public void loadActions() {
        // TODO
        //System.out.println("btn types " + alertPane.getButtonTypes());

        //look for ok buttons in the dialog pane
        alertPane.getButtonTypes().stream()
                .filter(buttonType -> buttonType.getButtonData() == ButtonType.OK.getButtonData())
                .findFirst()
                .ifPresent(buttonType -> alertPane.lookupButton(buttonType).addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            handleOKButtonAction();
            event.consume(); // Consume the event to prevent the default action
        }));

        //look for cancel buttons in the dialog pane
        alertPane.getButtonTypes().stream()
                .filter(buttonType -> buttonType.getButtonData() == ButtonType.CANCEL.getButtonData())
                .findFirst()
                .ifPresent(buttonType -> alertPane.lookupButton(buttonType).addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            handleCancelButtonAction();
            event.consume(); // Consume the event to prevent the default action
        }));
        

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadActions();
    }

    private void handleOKButtonAction() {
        //System.out.println("OK button clicked");
        okClicked= true;
        closeDialog();
        stage.getScene().getRoot().setDisable(false);
    }

    private void handleCancelButtonAction() {
        //System.out.println("Cancel button clicked");
        okClicked = false;
        closeDialog();
        stage.getScene().getRoot().setDisable(false);
    }

    private void closeDialog() {
        // Close the dialog
        Stage stage = (Stage) alertPane.getScene().getWindow();
        stage.close();
    }

}
