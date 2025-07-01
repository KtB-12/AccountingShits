package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dr_machine_add {

    @FXML private DatePicker date;
    @FXML private ComboBox<String> accountname;
    @FXML private TextField municipality;
    @FXML private TextField province;
    @FXML private DatePicker dateinstalled;
    @FXML private TextField drnumber;
    @FXML private TextField downpayment;
    @FXML private TextField terms;
    @FXML private TextField monthlypayment;
    @FXML private TextField firstpaymentdate;
    @FXML private TextField totalamount;
    @FXML private TextField area_manager;
    @FXML private TextField deliveredby;

    @FXML private Button atl; 
    @FXML private ImageView back_to_list;


    @FXML
    private void addprod(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popup_for_machine.fxml"));
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

    @FXML
    private void list(MouseEvent event) {
        System.out.println("List button clicked");
        // TODO: Implement logic to show product list
    }

    @FXML
    private void add_to_list(MouseEvent event) {
        System.out.println("Add to list clicked");
    }


    @FXML
    private void back_to_list(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
