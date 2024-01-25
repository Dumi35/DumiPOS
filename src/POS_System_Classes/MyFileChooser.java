
package POS_System_Classes;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author dumid
 */
public class MyFileChooser {
    //declare column names
    //List<String[]>
    
    //TOOLS FOR DATABASE
    private static Connection con;
    //private Statement statement;
    private static PreparedStatement ps;
    //private Result result;
    
    public static List<String[]> OpenFile(TableView newRecords, Label DisplayFilename, ObservableList dataList) throws FileNotFoundException, IOException{
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new ExtensionFilter("CSV Files","*.csv"));
        File selectedFile = fc.showOpenDialog(null);
        
        List<String[]> data = new ArrayList<>();
        //declare column names
        TableColumn columnF1 = new TableColumn("EmailAddress");
        columnF1.setCellValueFactory(
                new PropertyValueFactory<>("f1"));

        TableColumn columnF2 = new TableColumn("Username");
        columnF2.setCellValueFactory(
                new PropertyValueFactory<>("f2"));

        TableColumn columnF3 = new TableColumn("Password");
        columnF3.setCellValueFactory(
                new PropertyValueFactory<>("f3"));
        
        if (selectedFile != null){
           DisplayFilename.setText(selectedFile.getAbsolutePath());
           
           try{
               //clear the table
               //dataList.clear();
               newRecords.getItems().clear();
               newRecords.getColumns().clear();
               //add to the table
               String line;
                BufferedReader br=new BufferedReader(new FileReader(selectedFile));
                while((line=br.readLine())!=null){
                    String[] fields = line.split(",");
                    Record record = new Record(fields[0], fields[1], fields[2]);  
                    dataList.add(record);
                    String[] items = {record.getF1(),record.getF2(),record.getF3()};
                    data.add(items);
                }
                
                newRecords.setItems(dataList);
                newRecords.getColumns().addAll(columnF1, columnF2, columnF3);
                
           }catch(Exception e){System.out.println(e);}
        }else{System.out.println("File ain't valid");}
        return data;
    }
    
    //upload a CSV file to database
    public static void UploadFile(List<String[]> data, PreparedStatement ps) throws FileNotFoundException, IOException, SQLException{
        
        
        for (int i=0; i< data.size();i++){
            //System.out.println(Arrays.toString(data.get(i))); //Arrays.toString makes it print out the actual content
            try{
                for (int j=0; j<data.get(i).length;j++) {
                String get = data.get(i)[j];
                
                System.out.println(get);
                
                ps.setString(j+1, get);       
            }
            ps.executeUpdate();
            }catch(Exception e){System.out.println(e);}
            
        }
    }
    
    public static class Record {
        //Each record has 3 fields

        private SimpleStringProperty f1, f2, f3;

        public String getF1() {
            return f1.get();
        }

        public String getF2() {
            return f2.get();
        }

        public String getF3() {
            return f3.get();
        }

        Record(String f1, String f2, String f3) {
            this.f1 = new SimpleStringProperty(f1);
            this.f2 = new SimpleStringProperty(f2);
            this.f3 = new SimpleStringProperty(f3);
        }

    }

}

