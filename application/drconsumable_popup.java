package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class drconsumable_popup {

    @FXML private TextField quantity;
    @FXML private ComboBox<String> product;
    @FXML private TextField unitPrice;
    @FXML private TextField discount;
    @FXML private TextField amount;
    @FXML private Button atl;

    @FXML
    private void initialize() {
        // You can populate the ComboBox here
        product.getItems().addAll("Item 1", "Item 2", "Item 3");
    }

    @FXML
    private void add_to_list(MouseEvent event) {
        System.out.print("esrdtfyguhji");
    }
    
    @FXML
    private void exit(MouseEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
