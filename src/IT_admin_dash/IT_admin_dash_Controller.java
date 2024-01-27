/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IT_admin_dash;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;


public class IT_admin_dash_Controller implements Initializable {
    
    @FXML
    private Circle profile_pic;
    
    public void setData() {

            Image profileImage = new Image(getClass().getResource("../images/starfire.jpg").toExternalForm(), 150, 150, true, true);
            
            profile_pic.setFill(new ImagePattern(profileImage));
    }  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setData();
    }    
}
