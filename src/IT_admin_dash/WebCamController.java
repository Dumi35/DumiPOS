/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package IT_admin_dash;

import utility_classes.Images;
import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 * FXML Controller class
 *
 * @author dumid
 */
public class WebCamController implements Initializable {

    static {
        File file = new File("C:\\Users\\dumid\\Documents\\PAU Notes\\CSC301\\JavaCVDriver\\required\\opencv_java249.dll");
        System.load(file.getAbsolutePath());
    }

    private static File getImage;
    private static final SecureRandom RAND = new SecureRandom();
    public static String filename = null;
    //private DaemonThread myThread = null;
    private VideoCapture websource = null;
    private final Mat frame = new Mat(1000, 1000, 1);
    private final MatOfByte mem = new MatOfByte();
    @FXML
    private Rectangle cameraRect;

    /**
     * Creates new form WebCamera
     */
//    public WebCamController() {
//        websource = new VideoCapture(0);
//        myThread = new DaemonThread(cameraRect);
//        Thread t = new Thread(myThread);
//        t.setDaemon(true);
//        myThread.runnable = true;
//        t.start();
//    }

//    public void OpenWebCam() {
//        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
//        confirm.setTitle("Capture Passport");
//        confirm.setContentText("Are you sure you want to capture the image? ");
//
//        ButtonType result = confirm.showAndWait().orElse(ButtonType.CANCEL);
//        if (result == ButtonType.OK) {
//            try {
//                File file = new File("Capture");
//                boolean flag = true;
//
//                if (!file.isDirectory()) {
//                    flag = file.mkdir();
//                }
//
//                if (!flag) {
//                    throw new Exception("Folder does not exist");
//                }
//
//                int imageNo = 1 + RAND.nextInt(999);
//                filename = file.getAbsolutePath() + "\\" + "Webcam" + imageNo + ".jpg";
//                Highgui.imwrite(filename, frame);
//                getImage = file;
//                CaptureImage(cameraRect);
//                //JOptionPane.showMessageDialog(rootPane, filename + " Captured");
//                //Images.jTextField5.setText(filename);
//
//                File image = new File(filename);
//                FileInputStream fis = new FileInputStream(image);
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] Byte = new byte[1024];
//
//                for (int i; (i = fis.read(Byte)) != -1;) {
//                    baos.write(Byte, 0, i);
//                }
//                Images.photo = baos.toByteArray();
//                //dispose();
//                //websource.release();
//                //close the window
//                Stage stage = (Stage) cameraRect.getScene().getWindow();
//                stage.close();
//
//            } catch (Exception e) {
//                stopCam();
//                System.out.println("Error " + e);
//            }
//        }
//    }
//
//    public class DaemonThread implements Runnable {
//
//        protected volatile boolean runnable = false;
//
//        public DaemonThread(Rectangle capture) {
//            cameraRect = capture;
//        }
//
//        @Override
//        public void run() {
//            synchronized (this) {
//                while (runnable) {
//                    if (websource.grab()) {
//                        try {
//                            websource.retrieve(frame);
//                            Highgui.imencode(".bmp", frame, mem);
//                           // Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
//                            //BufferedImage buff = (BufferedImage) im;
//                            Image im = new Image(new ByteArrayInputStream(mem.toArray()));
//
//                            // Update UI on the JavaFX Application Thread
//                            Platform.runLater(() -> {
//                                // Set the image as the fill of the rectangle
//                                cameraRect.setFill(new ImagePattern(im));
//                            });
//
//                            Thread.sleep(100); // Adjust sleep duration as needed
//
//                        } catch (Exception e) {
//                            System.out.println("Error " + e);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private void stopCam() {
//        if (myThread != null) {
//            if (myThread.runnable == true) {
//                myThread.runnable = false;
//                websource.release();
//            }
//        }
//    }
//
//    private void CaptureImage(Rectangle image) {
//        try {
//            stopCam();
//            if (getImage != null) {
////                ImageIcon imageicon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(cameraRect.getWidth(), cameraRect.getHeight(), Image.SCALE_DEFAULT));
//                //cameraRect.setIcon(imageicon);
//
//            }
//        } catch (Exception e) {
//            System.out.println("Error " + e);
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        websource = new VideoCapture(0);
//        myThread = new DaemonThread(cameraRect);
//        Thread t = new Thread(myThread);
//        t.setDaemon(true);
//        myThread.runnable = true;
//        t.start();
    }

}
