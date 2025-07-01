package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class popupforaddingreceiptsController {

    @FXML private TextField amountField; 
    @FXML private TextField discountField;
    @FXML private TextField quantityField;
    @FXML private TextField unitPriceField;
    @FXML private ComboBox<String> itemDescriptionComboBox;

    @FXML
    private void exit(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
        itemDescriptionComboBox.getItems().addAll(
            "Consultation",
            "Laboratory Services",
            "Medical Equipment",
            "Other Services"
        );
    }
}
