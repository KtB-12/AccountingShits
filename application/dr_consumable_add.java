package application;

import java.util.ResourceBundle;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dr_consumable_add implements Initializable {

    @FXML private DatePicker date;
    @FXML private ComboBox<String> name;
    @FXML private TextField prov;
    @FXML private TextField municipality;
    @FXML private TextField drnumber;
    @FXML private TextField totalamount;
    @FXML private TextField area_manager;
    @FXML private TextField deliveredby;
    @FXML private TextField terms;

    @FXML
    private void addac(MouseEvent event) {
        System.out.println("qwerttrf");

    }

    @FXML
    private void addprod(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popup_for_consumable.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT); 

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            Node source = (Node) event.getSource();
            Bounds boundsInScreen = source.localToScreen(source.getBoundsInLocal());

            // Set stage position slightly below the button
            stage.setX(boundsInScreen.getMinX());
            stage.setY(boundsInScreen.getMaxY() + 5); // +5 pixels below

            // Dragging support
            final Delta dragDelta = new Delta();
            root.setOnMousePressed(e -> {
                dragDelta.x = e.getSceneX();
                dragDelta.y = e.getSceneY();
            });
            root.setOnMouseDragged(e -> {
                stage.setX(e.getScreenX() - dragDelta.x);
                stage.setY(e.getScreenY() - dragDelta.y);
            });

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class Delta {
        double x, y;
    }
    @FXML
    private void list(MouseEvent event) {
        System.out.println("List button clicked");
    }
    

    @FXML
    private void back_to_list(MouseEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Example: Add items to ComboBox
        name.getItems().addAll("Account A", "Account B", "Account C");

        name.setOnAction(e -> {
            String selected = name.getValue();
            // Dummy logic
            if ("Account A".equals(selected)) {
                prov.setText("Province A");
                municipality.setText("Municipality A");
                area_manager.setText("Manager A");
            } else if ("Account B".equals(selected)) {
                prov.setText("Province B");
                municipality.setText("Municipality B");
                area_manager.setText("Manager B");
            }
        });
    }
}
