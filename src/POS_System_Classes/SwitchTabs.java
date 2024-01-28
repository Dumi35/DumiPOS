/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POS_System_Classes;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author dumid
 */
public class SwitchTabs {
    public static void switchTabs(VBox sideBar,StackPane StackPane) {
        // Get IDs of all children
        for (Node node : sideBar.getChildren()) {
            String id = node.getId();
            if (id != null && !id.isEmpty()) {
                //System.out.println("ID of child: " + id);
            }
            node.setOnMouseClicked(e -> switchPane((HBox) node, sideBar, StackPane));
        }
    }

    // Method to change background of hbox tabs in sidebar
    public static void switchPane(HBox selected, VBox sideBar, StackPane StackPane ) {
        Platform.runLater(() -> {
            // Set selected label color
            selected.setStyle("-fx-background-color:white;");

            //change label color from white to black
            for (Node LabelNode : selected.getChildren()) {
                LabelNode.setStyle("-fx-text-fill:black;");
            }
            
            // Reset color for other hbox tabs from black to white
            for (Node node : sideBar.getChildren()) {
                if (!node.equals(selected)) {
                    node.setStyle("-fx-background-color:none;-fx-text-fill:white;");
//                    HBox box = (HBox) node;
//                    for (Node LabelNode : box.getChildren()) { //convert nodes to hbox
//                        LabelNode.setStyle("-fx-text-fill:white;");
//                        System.out.println("label node"+LabelNode);
//                    }
                }
            }
            
            Node pane = getNodeById(StackPane, selected.getId() + "Pane");

            //open the correct tab pane and close others
            for (Node node : StackPane.getChildren()) {
                if (!node.equals(pane)) {
                    node.setVisible(false);
                }
            }
            pane.setVisible(true);

        });
    }

    // Method to find a node by ID recursively
    public static Node getNodeById(Node node, String id) {
        if (node.getId() != null && node.getId().equals(id)) {
            return node;
        }
        if (node instanceof javafx.scene.Parent) {
            for (Node child : ((javafx.scene.Parent) node).getChildrenUnmodifiable()) {
                Node found = getNodeById(child, id);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

}
