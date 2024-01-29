/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sales_mng_dash;

import utility_classes.SwitchTabs;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author dumid
 */
public class Sales_mng_dashController implements Initializable {

    @FXML
    private Circle profile_pic;
    @FXML
    private AreaChart<?, ?> areaChart1;
    @FXML
    private AreaChart<?, ?> areaChart2;
    @FXML
    private AreaChart<?, ?> areaChart3;
    @FXML
    private VBox sideBar;
    @FXML
    private StackPane StackPane;
    

    public void setData() {
//        fullName.setText(data.get("firstName") + " " + data.get("lastName"));
//        emailAddress.setText(data.get("emailAddress"));
//        uniqueStaffID = data.get("staffID");
//        currentConnection.getAllScoresFromDatabase(uniqueStaffID, testScoreTable, filterMode, downlooadTableButton);
//        
//        currentCourseFunction.initializeCourseList(uniqueStaffID, noContentVBox, courseListScrollPane, courseContainerVBox, this);
        
//            Image profileImage = currentConnection.imageDownload("lecturer_details", data.get("emailAddress"));
//            if(profileImage == null){ 
              Image profileImage = new Image(getClass().getResource("../images/starfire.jpg").toExternalForm(), 150, 150, true, true);
            //}
            profile_pic.setFill(new ImagePattern(profileImage));
    }  
    
    private void setAreaChart1(){
        XYChart.Series series = new XYChart.Series();
        // Generate data points for the sine wave
        for (int angle = 0; angle <= 360; angle += 20) {
            double radians = Math.toRadians(angle);
            double sineValue = Math.sin(radians);
            series.getData().add(new XYChart.Data<>(String.valueOf(angle), sineValue));
        }
                
        areaChart1.getYAxis().setTickLabelsVisible(false); //HIDE NUMBERS ON AXIS
        areaChart1.getYAxis().setOpacity(0);
        areaChart1.getXAxis().setTickLabelsVisible(false);
        areaChart1.getXAxis().setOpacity(0);
        
        areaChart1.setCreateSymbols(false);
        
        areaChart1.getData().addAll(series);
        
    }
    
    private void setAreaChart2(){
        XYChart.Series series = new XYChart.Series();
        
        series.getData().add(new XYChart.Data<>("0", 0));
        series.getData().add(new XYChart.Data<>("3", 5));
        series.getData().add(new XYChart.Data<>("5", 2));
        series.getData().add(new XYChart.Data<>("8", 7));
        series.getData().add(new XYChart.Data<>("10", 5));
        series.getData().add(new XYChart.Data<>("13", 8));
        series.getData().add(new XYChart.Data<>("14", 8));
        series.getData().add(new XYChart.Data<>("15", 8));
        
        areaChart2.getYAxis().setTickLabelsVisible(false); //HIDE NUMBERS ON AXIS
        areaChart2.getYAxis().setOpacity(0);
        areaChart2.getXAxis().setTickLabelsVisible(false);
        areaChart2.getXAxis().setOpacity(0);
        
        areaChart2.setCreateSymbols(false);
        areaChart2.getData().addAll(series);
    }
    
    private void setAreaChart3(){
        XYChart.Series series = new XYChart.Series();
        
       for (double x = -3; x <= 7; x+= 1) {
            double y = (2.0*(x*x)) -(5.0*x) +2.0;
            series.getData().add(new XYChart.Data<>(String.valueOf(x), y));
        }
        
        areaChart3.getYAxis().setTickLabelsVisible(false); //HIDE NUMBERS ON AXIS
        areaChart3.getYAxis().setOpacity(0);
        areaChart3.getXAxis().setTickLabelsVisible(false);
        areaChart3.getXAxis().setOpacity(0);
        
        areaChart3.setCreateSymbols(false);
        areaChart3.getData().addAll(series);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setData();
        setAreaChart1();
        setAreaChart2();
        setAreaChart3();
        SwitchTabs.switchTabs(sideBar,StackPane);
    }    
    
}
