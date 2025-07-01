package application;

import java.util.ResourceBundle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class collection_addmachine implements Initializable {

    @FXML private TextField mcr;
    @FXML private TextField drnumber;
    @FXML private TextField name;
    @FXML private TextField address;
    @FXML private DatePicker date;
    @FXML private TextField terms;
    @FXML private TextField machine;
    @FXML private TextField price;
    @FXML private TextField total;
    @FXML private TextField recievedby;

    @FXML private Button atl; // + Add to List
    @FXML private Button list; // LIST button
    @FXML private Button add; // + button near "Add Details"

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Optional: set default date to today
        date.setValue(LocalDate.now());
    }

    @FXML
    private void add_to_list() {
        // Placeholder for "+ Add to List" logic
        String mcrNo = mcr.getText();
        String drNo = drnumber.getText();
        String accountName = name.getText();
        LocalDate deliveryDate = date.getValue();
        String addr = address.getText();
        String term = terms.getText();
        String machineName = machine.getText();
        String contractPrice = price.getText();
        String totalAmount = total.getText();
        String receivedBy = recievedby.getText();

        System.out.println("Added to List: " + mcrNo + ", " + drNo + ", " + accountName);
    }

    @FXML
    private void list() {
        System.out.println("Add Details button clicked.");
    }
    @FXML
    private void back_to_list(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    //collectionFORM machine_ADD PAYMENT DETAILS.fxml
    @FXML
    private void add(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("collectionFORM machine_ADD PAYMENT DETAILS.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT); 

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Get screen coordinates of the button
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
}
