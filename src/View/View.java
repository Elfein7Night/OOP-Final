package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class View {
    private BorderPane borderPane;
    private Alert alert;
    private Scene scene;
    private String title;
    private Stage stage;

    public View(Stage _stage, String _title, int width, int height, boolean show) {
        stage = _stage;
        alert = new Alert(Alert.AlertType.ERROR);
        borderPane = new BorderPane();
        title = _title;
        scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        if (show) stage.show();
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public void updateBorderPane(BorderPane _borderPane, String sport) {
        borderPane = _borderPane;
        String txt = title;
        txt += sport == null ? "" : " (" + sport + ")";
        HBox title = new HBox(new Text(txt));
        title.setAlignment(Pos.CENTER);
        borderPane.setTop(title);
        scene.setRoot(borderPane);
    }

    public static TextField buildTextFieldFromName(String name) {
        TextField textField = new TextField(name);
        textField.editableProperty().set(false);
        return textField;
    }

    public static VBox showPlayersFromList(List<String> players, int spacing) {
        VBox vBox = new VBox();
        players.forEach(p -> vBox.getChildren().add(buildTextFieldFromName(p)));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(spacing);
        vBox.setPadding(new Insets(0, 0, 0, 15));
        return vBox;
    }

    public void showAlert(Alert.AlertType type, String message) {
        alert.setAlertType(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }


}
