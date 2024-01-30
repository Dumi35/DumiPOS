/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package components.alerts;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;


public class AlertController implements Initializable {

    @FXML
    private DialogPane alertPane;

   public void SetContent(String alertContent){
       alertPane.setContentText(alertContent);
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //alertPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
       // System.out.println("btn types "+ alertPane.getButtonTypes());
        
    }    
    
}
