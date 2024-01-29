package utility_classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model_classes.Product;

public class comboBoxingg extends Application {

    //TOOLS FOR DATABASE
    private Connection con;
    //private Statement statement;
    private PreparedStatement ps;
    private ResultSet result;
    private ObservableList<String> LowStock = FXCollections.observableArrayList();

    //retrieve all product data from database
    public ObservableList<String> AllProductListData() throws SQLException {
        ObservableList<Product> listData = FXCollections.observableArrayList();
        ObservableList<String> listDataNames = FXCollections.observableArrayList();

        con = DatabaseConn.connectDB();
        //read from Database table
        try {
            ps = con.prepareStatement("SELECT * FROM products");// + QuizTableName.getText());
            result = ps.executeQuery();

            Product allPData;

            while (result.next()) {
                allPData = new Product(result.getString("Product_Code"), result.getString("Product_Name"), result.getString("Manufacturer"), result.getDate("Manu_Date"), result.getDate("Expiry_Date"), result.getInt("Quantity"), result.getInt("Price"));
                listDataNames.add(allPData.getName());
                if (allPData.getStatus().equals("Low")) {
                    LowStock.add(allPData.getName());
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return listDataNames;
    }

    public void start(Stage stage) throws SQLException {
        HBox root = new HBox();

        ComboBox<String> cb = new ComboBox<String>();
        cb.setEditable(true);

        // Create a list with some dummy values.
        ObservableList<String> items = FXCollections.observableArrayList(AllProductListData());

        // Set a custom cell factory for the ComboBox
        cb.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);

                    // Check if the item is the "Special Item"
                    for (String i : LowStock) {
                        if (item.equals(i)) {
                            // Set the background color to red for the "Special Item"
                            setBackground();
                        }
                    }

                }
            }

            private void setBackground() {
                setStyle("-fx-background-color: #e5eaee; -fx-text-fill: black;");
            }
        });

        // Create a FilteredList wrapping the ObservableList.
        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        cb.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cb.getEditor();//get editor returns the textfield of the combbobox
            final String selected = cb.getSelectionModel().getSelectedItem();

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                    cb.show();
                }
            });
        });

        cb.setItems(filteredItems);

        root.getChildren().add(cb);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
