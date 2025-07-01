package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class dr_generated {

    @FXML private TableView<?> tableView;
    @FXML private TableColumn<?, ?> drnumber;
    @FXML private TableColumn<?, ?> date;
    @FXML private TableColumn<?, ?> qty;
    @FXML private TableColumn<?, ?> product;
    @FXML private TableColumn<?, ?> deliveredby;
    @FXML private TableColumn<?, ?> amount;
    @FXML private TextField totalall;
    @FXML private Button atl; // Print Document
    @FXML private Button atl1; // Back to table

    @FXML
    private void print(MouseEvent event) {
        System.out.println("Print button clicked");
        // TODO: Implement your print logic here
    }

    @FXML
    private void exit(MouseEvent event) {
        // Close the current window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimize(MouseEvent event) {
        // Minimize the window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }
}
