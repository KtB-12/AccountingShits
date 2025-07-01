package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AreaManagerController {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ProgressIndicator collectionProgress;

    @FXML
    private TextField totalCollectionField;

    @FXML
    private TextField totalSalesField;

    @FXML
    private TextField totalMachineCollectedBlue;

    @FXML
    private TextField totalMachineCollectedRed;

    @FXML
    private TextField totalCommissionField;

    @FXML
    private ImageView exit;

    @FXML
    private void initialize() {
        // Set initial states if needed
        collectionProgress.setProgress(0.46); // or fetch dynamically
        totalCollectionField.setText("100,0000");
        totalSalesField.setText("100,000");
        totalMachineCollectedBlue.setText("100,0000");
        totalMachineCollectedRed.setText("100,000");
        totalCommissionField.setText("100,000");
    }

    @FXML
    private void exit(MouseEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
