package application;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

public class DeliveryReceiptsController implements SidebarStateAware {

    @FXML private Pane sidebar, homepane;
    @FXML private ImageView minbutton, maximizebutton;
    @FXML private ImageView edit, delete, archive;
    @FXML private Button homesign, accbutton, prosign, collectionsign, summarixsign, logsign, settingsb1, deliversign;
    @FXML private ImageView homeicon, accicon, prodicon, colloectionicon, summarixicon, logicon1, settingsicon, deliviericon;

    @FXML private TableView<DeliveryReceipt> deltable;
    @FXML private Text delsign;

    @FXML private ImageView view;
    @FXML private ImageView view1;
    @FXML private ChoiceBox<?> choiceBox;
    @FXML private Button generate;
    @FXML private ImageView exit;
    @FXML private ImageView minimize;

    @FXML private TableColumn<DeliveryReceipt, String> dr_number;
    @FXML private TableColumn<DeliveryReceipt, LocalDate> date_delivered;
    @FXML private TableColumn<DeliveryReceipt, String> account_name;
    @FXML private TableColumn<DeliveryReceipt, String> address;
    @FXML private TableColumn<DeliveryReceipt, String> delivered_by;
    @FXML private TableColumn<DeliveryReceipt, Double> total_amount;
    @FXML private TableColumn<DeliveryReceipt, String> status;

    private static final double SIDEBAR_MIN_WIDTH = 115;
    private static final double SIDEBAR_MAX_WIDTH = 260;
    private static final double HOME_PANE_MIN_WIDTH = 80;
    private static final double HOME_PANE_MAX_WIDTH = 230;
    private static final double DELTABLE_EXPAND_AMOUNT = 150;
    private static final double DELSIGN_MOVE_AMOUNT = 100;
    private static final Duration ANIMATION_DURATION = Duration.millis(300);

    private double originalTableX;
    private double originalTableWidth;
    private double originalDelsignX;

    private final List<Double> originalColumnWidths = new ArrayList<>();
    private final List<TableColumn<?, ?>> columns = new ArrayList<>();

    @FXML
    private void initialize() {
        maximizebutton.setVisible(false);
        sidebar.setPrefWidth(SIDEBAR_MAX_WIDTH);
        homepane.setPrefWidth(HOME_PANE_MAX_WIDTH);

        originalTableX = deltable.getLayoutX();
        originalTableWidth = deltable.getPrefWidth();
        originalDelsignX = delsign.getLayoutX();

        for (Object obj : deltable.getColumns()) {
            TableColumn<?, ?> col = (TableColumn<?, ?>) obj;
            columns.add(col);
            originalColumnWidths.add(col.getPrefWidth());
        }

        applySidebarState(SidebarState.isMinimized());

        setupTableColumns();
        loadDeliveryReceipts();
    }

    @Override
    public void applySidebarState(boolean minimized) {
        if (minimized) {
            sidebar.setPrefWidth(SIDEBAR_MIN_WIDTH);
            homepane.setPrefWidth(HOME_PANE_MIN_WIDTH);
            toggleSidebarText(false);
            minbutton.setVisible(false);
            maximizebutton.setVisible(true);
            deltable.setLayoutX(originalTableX - DELTABLE_EXPAND_AMOUNT);
            deltable.setPrefWidth(originalTableWidth + DELTABLE_EXPAND_AMOUNT);
            delsign.setLayoutX(originalDelsignX - DELSIGN_MOVE_AMOUNT);

            for (int i = 0; i < columns.size(); i++) {
                double originalWidth = originalColumnWidths.get(i);
                double ratio = originalWidth / originalTableWidth;
                double expandedWidth = originalWidth + (DELTABLE_EXPAND_AMOUNT * ratio);
                columns.get(i).setPrefWidth(expandedWidth);
            }
        } else {
            sidebar.setPrefWidth(SIDEBAR_MAX_WIDTH);
            homepane.setPrefWidth(HOME_PANE_MAX_WIDTH);
            toggleSidebarText(true);
            minbutton.setVisible(true);
            maximizebutton.setVisible(false);
            deltable.setLayoutX(originalTableX);
            deltable.setPrefWidth(originalTableWidth);
            delsign.setLayoutX(originalDelsignX);

            for (int i = 0; i < columns.size(); i++) {
                columns.get(i).setPrefWidth(originalColumnWidths.get(i));
            }
        }
    }

    @FXML
    private void min(MouseEvent event) {
        if (SidebarState.isMinimized()) return;

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

        for (int i = 0; i < columns.size(); i++) {
            TableColumn<?, ?> col = columns.get(i);
            double originalWidth = originalColumnWidths.get(i);
            double ratio = originalWidth / originalTableWidth;
            double expandedWidth = originalWidth + (DELTABLE_EXPAND_AMOUNT * ratio);
            timeline.getKeyFrames().add(
                new KeyFrame(ANIMATION_DURATION, new KeyValue(col.prefWidthProperty(), expandedWidth))
            );
        }

        timeline.setOnFinished(e -> {
            maximizebutton.setVisible(true);
            SidebarState.setMinimized(true);
        });

        timeline.play();
    }

    @FXML
    private void max(MouseEvent event) {
        if (!SidebarState.isMinimized()) return;

        Timeline timeline = new Timeline(
            new KeyFrame(ANIMATION_DURATION,
                new KeyValue(sidebar.prefWidthProperty(), SIDEBAR_MAX_WIDTH),
                new KeyValue(homepane.prefWidthProperty(), HOME_PANE_MAX_WIDTH),
                new KeyValue(deltable.layoutXProperty(), originalTableX),
                new KeyValue(deltable.prefWidthProperty(), originalTableWidth),
                new KeyValue(delsign.layoutXProperty(), originalDelsignX)
            )
        );

        for (int i = 0; i < columns.size(); i++) {
            TableColumn<?, ?> col = columns.get(i);
            double originalWidth = originalColumnWidths.get(i);
            timeline.getKeyFrames().add(
                new KeyFrame(ANIMATION_DURATION, new KeyValue(col.prefWidthProperty(), originalWidth))
            );
        }

        timeline.setOnFinished(e -> {
            toggleSidebarText(true);
            maximizebutton.setVisible(false);
            minbutton.setVisible(true);
            SidebarState.setMinimized(false);
        });

        timeline.play();
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

    private void setupTableColumns() {
        dr_number.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDrNumber()));
        date_delivered.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDateDelivered()));
        account_name.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAccountName()));
        address.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));
        delivered_by.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDeliveredBy()));
        total_amount.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getTotalAmount()));
        status.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
    }

    private void loadDeliveryReceipts() {
        ObservableList<DeliveryReceipt> receipts = FXCollections.observableArrayList();

        String query = """
            SELECT dr.dr_number, dr.date_delivered, dr.account_name, dr.delivered_by,
                   COALESCE(a.municipality, '') || ', ' || COALESCE(a.prov, '') AS address,
                   dr.total_amount, dr.status
            FROM delivery_receipts dr
            LEFT JOIN accounts a ON dr.account_name = a.account_name
            ORDER BY dr.date_delivered DESC
        """;

        try (Connection conn = PostgresConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                receipts.add(new DeliveryReceipt(
                    rs.getString("dr_number"),
                    rs.getDate("date_delivered").toLocalDate(),
                    rs.getString("account_name"),
                    rs.getString("delivered_by"),
                    rs.getString("address"),
                    rs.getDouble("total_amount"),
                    rs.getString("status")
                ));
            }

            deltable.setItems(receipts);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Navigation and modals
    @FXML private void openMyAccount(MouseEvent event) { loadFXML("MyACCOUNT.fxml", event); }
    @FXML private void minimize(MouseEvent event) { ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true); }
    @FXML private void exit(MouseEvent event) { ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); }
    @FXML private void prod(MouseEvent event) { loadFXML("products_list.fxml", event); }
    @FXML private void sum(MouseEvent event) { loadFXML("executive_summary.fxml", event); }
    @FXML private void account1(MouseEvent event) { loadFXML("List_of_accounts.fxml", event); }
    @FXML private void homeic(MouseEvent event) { loadFXML("Homepage.fxml", event); }
    @FXML private void homesi(MouseEvent event) { loadFXML("Homepage.fxml", event); }
    @FXML private void view(MouseEvent event) { loadFXML("deliveryreciepts_view.fxml", event); }
    @FXML private void arch(MouseEvent event) { loadFXML("LIST-CancelledDeliveryReceipts.fxml", event); }
    @FXML private void col(MouseEvent event) { loadFXML("collectionreciepts_list.fxml", event); }

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

    @FXML
    private void add(MouseEvent event) {
        openModalAtButton("drchoice.fxml", event);
    }

    @FXML
    private void generate(MouseEvent event) {
        openModalAtButton("generate(Date).fxml", event);
    }

    private void openModalAtButton(String fxml, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            Node source = (Node) event.getSource();
            Bounds boundsInScreen = source.localToScreen(source.getBoundsInLocal());
            stage.setX(boundsInScreen.getMinX());
            stage.setY(boundsInScreen.getMaxY() + 5);

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

    private void loadFXML(String fxmlFile, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            if (loader.getController() instanceof SidebarStateAware) {
                ((SidebarStateAware) loader.getController()).applySidebarState(SidebarState.isMinimized());
            }

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading " + fxmlFile);
        }
    }

    public static class Delta {
        double x, y;
    }
}
