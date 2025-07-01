package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class dr_viewedit {

    @FXML private ComboBox<String> products;
    @FXML private TextField qty;
    @FXML private TextField price;
    @FXML private TextField discount;
    @FXML private TextField amount;
    @FXML private Button atl; 
    
    @FXML private void initialize() {   
        products.getItems().addAll("Product A", "Product B", "Product C");

        products.setOnAction(event -> {
            String selected = products.getSelectionModel().getSelectedItem();
            if (selected != null) {
                qty.setText("1");
                price.setText("100.00");
                calculateAmount();
            }
        });

        discount.textProperty().addListener((obs, oldVal, newVal) -> calculateAmount());
    }
    
    @FXML
    private void exit(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void add_to_list() {
        String product = products.getValue();
        String quantity = qty.getText();
        String unitPrice = price.getText();
        String discountValue = discount.getText();
        String totalAmount = amount.getText();

        System.out.println("Saved: " + product + ", Qty: " + quantity +
                ", Price: " + unitPrice + ", Discount: " + discountValue + ", Amount: " + totalAmount);

    }

    private void calculateAmount() {
        try {
            double priceValue = Double.parseDouble(price.getText());
            double discountValue = discount.getText().isEmpty() ? 0 : Double.parseDouble(discount.getText());
            int quantityValue = Integer.parseInt(qty.getText());

            double result = (priceValue * quantityValue) - discountValue;
            amount.setText(String.format("%.2f", result));
        } catch (NumberFormatException e) {
            amount.setText("0.00");
        }
    }
}
