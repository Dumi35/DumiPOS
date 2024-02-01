package utility_classes;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Images {

    public String filename = null;
    public static byte photo[] = null;

    public static void main(String[] args) {

    }

    public byte[] BrowseSystemImages(Rectangle imgView) {
        try {
            FileChooser fc = new FileChooser();

            //fc.showOpenDialog(null);
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png", "*.jpeg"));

            File f = fc.showOpenDialog(null);
            String filePath = f.getAbsolutePath();

            if (f != null) {
                Image image = new Image("file:" + filePath, 150, 150, true, true);

                imgView.setFill(new ImagePattern(image));

                File imageFile = new File(filePath);
                FileInputStream fis = new FileInputStream(imageFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] Byte = new byte[1024];

                for (int i; (i = fis.read(Byte)) != -1;) {
                    baos.write(Byte, 0, i);
                }
                photo = baos.toByteArray();

            }

        } catch (Exception e) {

        }
        return photo;

    }
    
    public byte[] changeProfilePic(Rectangle imgView) {
        try {
            FileChooser fc = new FileChooser();

            //fc.showOpenDialog(null);
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png", "*.jpeg"));

            File f = fc.showOpenDialog(null);
            String filePath = f.getAbsolutePath();

            if (f != null) {
                Image image = new Image("file:" + filePath, 150, 150, true, true);

                imgView.setFill(new ImagePattern(image));

                File imageFile = new File(filePath);
                FileInputStream fis = new FileInputStream(imageFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] Byte = new byte[1024];

                for (int i; (i = fis.read(Byte)) != -1;) {
                    baos.write(Byte, 0, i);
                }
                photo = baos.toByteArray();
                return photo;
            }

        } catch (Exception e) {

        }
        return null;

    }


//    //web camera btn
//    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
//        WebCamera newCapture = new WebCamera();
//        newCapture.show();
//        newCapture.setVisible(true);
//    }
}
