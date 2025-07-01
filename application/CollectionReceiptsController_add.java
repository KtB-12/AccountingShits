package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CollectionReceiptsController_add {

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
    
    @FXML
    private void list(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FORM_Add Details_CR-machine.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

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
