package application;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class DeliveryReceiptsController_Arch {

    @FXML private Pane sidebar;
    @FXML private Pane homepane;

    @FXML private ImageView minbutton, maximizebutton;

    @FXML private ImageView edit, delete, archive;
    @FXML private Button homesign, accbutton, prosign, collectionsign, summarixsign, logsign, settingsb1, deliversign;

    @FXML private ImageView homeicon, accicon, prodicon, colloectionicon, summarixicon, logicon1, settingsicon, deliviericon;

    @FXML private TableView<?> deltable;
    @FXML private Text delsign;

    private static final double SIDEBAR_MIN_WIDTH = 115;
    private static final double SIDEBAR_MAX_WIDTH = 260;
    private static final double HOME_PANE_MIN_WIDTH = 80;
    private static final double HOME_PANE_MAX_WIDTH = 230;
    private static final double DELTABLE_EXPAND_AMOUNT = 150;
    private static final double DELSIGN_MOVE_AMOUNT = 100;
    private static final Duration ANIMATION_DURATION = Duration.millis(300);

    private boolean isMinimized = false;
    private double originalTableX;
    private double originalTableWidth;
    private double originalDelsignX;

    private List<Double> originalColumnWidths = new ArrayList<>();

    @FXML
    private void initialize() {
        maximizebutton.setVisible(false);
        sidebar.setPrefWidth(SIDEBAR_MAX_WIDTH);
        homepane.setPrefWidth(HOME_PANE_MAX_WIDTH);

        originalTableX = deltable.getLayoutX();
        originalTableWidth = deltable.getPrefWidth();
        originalDelsignX = delsign.getLayoutX();

        for (TableColumn<?, ?> col : deltable.getColumns()) {
            originalColumnWidths.add(col.getPrefWidth());
        }
    }

    @FXML
    private void min(MouseEvent event) {
        if (isMinimized) return;

        toggleSidebarText(false);
        minbutton.setVisible(false);

        Timeline timeline = new Timeline(
            new KeyFrame(ANIMATION_DURATION,
                new KeyValue(sidebar.prefWidthProperty(), SIDEBAR_MIN_WIDTH),
                new KeyValue(homepane.prefWidthProperty(), HOME_PANE_MIN_WIDTH),
                new KeyValue(deltable.layoutXProperty(), originalTableX - DELTABLE_EXPAND_AMOUNT),
                new KeyValue(deltable.prefWidthProperty(), originalTableWidth + DELTABLE_EXPAND_AMOUNT),
                new KeyValue(delsign.layoutXProperty(), originalDelsignX - DELSIGN_MOVE_AMOUNT)
            )
        );

        for (int i = 0; i < deltable.getColumns().size(); i++) {
            TableColumn<?, ?> col = deltable.getColumns().get(i);
            double newWidth = originalColumnWidths.get(i) + (DELTABLE_EXPAND_AMOUNT / deltable.getColumns().size());
            timeline.getKeyFrames().add(new KeyFrame(ANIMATION_DURATION,
                new KeyValue(col.prefWidthProperty(), newWidth)
            ));
        }

        timeline.setOnFinished(e -> maximizebutton.setVisible(true));
        timeline.play();
        isMinimized = true;
    }

    @FXML
    private void max(MouseEvent event) {
        if (!isMinimized) return;

        Timeline timeline = new Timeline(
            new KeyFrame(ANIMATION_DURATION,
                new KeyValue(sidebar.prefWidthProperty(), SIDEBAR_MAX_WIDTH),
                new KeyValue(homepane.prefWidthProperty(), HOME_PANE_MAX_WIDTH),
                new KeyValue(deltable.layoutXProperty(), originalTableX),
                new KeyValue(deltable.prefWidthProperty(), originalTableWidth),
                new KeyValue(delsign.layoutXProperty(), originalDelsignX)
            )
        );

        for (int i = 0; i < deltable.getColumns().size(); i++) {
            TableColumn<?, ?> col = deltable.getColumns().get(i);
            timeline.getKeyFrames().add(new KeyFrame(ANIMATION_DURATION,
                new KeyValue(col.prefWidthProperty(), originalColumnWidths.get(i))
            ));
        }

        timeline.setOnFinished(e -> {
            toggleSidebarText(true);
            maximizebutton.setVisible(false);
            minbutton.setVisible(true);
        });

        timeline.play();
        isMinimized = false;
    }

    private void toggleSidebarText(boolean visible) {
        homesign.setVisible(visible);
        accbutton.setVisible(visible);
        prosign.setVisible(visible);
        deliversign.setVisible(visible);
        collectionsign.setVisible(visible);
        summarixsign.setVisible(visible);
        settingsb1.setVisible(visible);
        logsign.setVisible(visible);
    }

    @FXML private void openMyAccount(MouseEvent event) {
        loadFXML("MyACCOUNT.fxml", event);
    }

    @FXML private void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML private void exit(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML private void prod(MouseEvent event) {
        loadFXML("products_list.fxml", event);
    }

    @FXML private void list(MouseEvent event) {
        loadFXML("deliveryreceipts_list.fxml", event);
    }
    
    @FXML
    private void col(MouseEvent event) {
        loadFXML("collectionreciepts_list.fxml", event);
    }

    @FXML
    private void out(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("log_out.fxml"));
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

    public static class Delta {
        double x, y;
    }

    @FXML private void sum(MouseEvent event) {
        loadFXML("executive_summary.fxml", event);
    }

    @FXML private void account1(MouseEvent event) {
        loadFXML("List_of_accounts.fxml", event);
    }

    @FXML private void homeic(MouseEvent event) {
        loadFXML("Homepage.fxml", event);
    }

    @FXML private void homesi(MouseEvent event) {
        loadFXML("Homepage.fxml", event);
    }

    private void loadFXML(String fxmlFile, MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading " + fxmlFile);
        }
    }
}
