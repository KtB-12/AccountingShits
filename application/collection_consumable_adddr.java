package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class collection_consumable_adddr {

    @FXML
    private TextField drNumberField;

    @FXML
    private TextField amountField;

    @FXML
    private Button atl; // Confirm button

    @FXML
    private ImageView exit; // Exit (cross) icon

    @FXML
    private void add_to_list(MouseEvent event) {
        String drNumber = drNumberField.getText();
        String amount = amountField.getText();

        // Validation
        if (drNumber == null || drNumber.isEmpty()) {
            System.out.println("DR Number is required.");
            return;
        }
        if (amount == null || amount.isEmpty()) {
            System.out.println("Amount is required.");
            return;
        }

        // Add your insertion logic here:
        System.out.println("Added DR: " + drNumber + " with Amount: " + amount);

        // Optionally clear fields after adding
        drNumberField.clear();
        amountField.clear();
    }

    /**
     * Called when the cross icon is clicked.
     * Closes the current window.
     */
    @FXML
    private void exit(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
