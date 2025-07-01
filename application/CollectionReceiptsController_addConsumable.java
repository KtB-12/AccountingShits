package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CollectionReceiptsController_addConsumable {

    @FXML private TableView<?> collectiontable;
    @FXML private Text colsign;

    @FXML private ImageView minbutton;
    @FXML private ImageView maximizebutton;
    @FXML private ImageView edit;
    @FXML private ImageView delete;

    @FXML private Pane homepane;
    @FXML private Pane sidebar;

    @FXML private Button homesign, accbutton, deliversign, prosign, summarixsign, logsign, settingsb1, collectionsign;
    @FXML private ImageView homeicon, accicon, deliviericon, prodicon, summarixicon, logicon1, settingsicon, colloectionicon;

    @FXML
    private void btl(MouseEvent event) {
    	System.out.println("Back to List clicked!");
        
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
}
