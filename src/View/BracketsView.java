package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BracketsView {
    private final BorderPane borderPane;
    private final Button playQuarter0, playQuarter1, playQuarter2, playQuarter3;
    private final Button playSemi0, playSemi1;
    private final Button playFinals;
    private final boolean[] disableProperties;

    public BracketsView() {
        borderPane = new BorderPane();
        disableProperties = new boolean[]{false, false, false, false, true, true, true};
        playQuarter0 = new Button();
        playQuarter1 = new Button();
        playQuarter2 = new Button();
        playQuarter3 = new Button();

        playSemi0 = new Button();
        playSemi1 = new Button();

        playFinals = new Button();

        getButtons().forEach(button -> {
            button.setText("Play Game");
            View.setCursorAsSelect(button);
        });
    }

    public void updateAll(List<String> quarterFinalists, List<String> semiFinalists, List<String> finalists,
                          String champion) {

        VBox quarterButtons = new VBox(playQuarter0, playQuarter1, playQuarter2, playQuarter3);
        quarterButtons.setAlignment(Pos.CENTER);
        quarterButtons.setSpacing(75);

        VBox semiButtons = new VBox(playSemi0, playSemi1);
        semiButtons.setAlignment(Pos.CENTER);
        semiButtons.setSpacing(180);

        VBox finalButton = new VBox(playFinals);
        finalButton.setAlignment(Pos.CENTER);
        updateDisableProperties();
        HBox all = new HBox(
                View.buildPlayersVBoxFromList(quarterFinalists, 25, Color.BLUE),
                quarterButtons,
                View.buildPlayersVBoxFromList(semiFinalists, 75, Color.BLUE),
                semiButtons,
                View.buildPlayersVBoxFromList(finalists, 180, Color.BLUE),
                finalButton,
                View.buildPlayersVBoxFromList(Collections.singletonList(champion), 0, Color.RED)
        );
        all.setAlignment(Pos.CENTER);
        all.setSpacing(20);
        borderPane.setCenter(all);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void addEventHandlerToButton(int buttonIndex, EventHandler<ActionEvent> eventHandler) {
        getButtons().get(buttonIndex).setOnAction(eventHandler);
    }

    public void toggleButtonDisabled(int buttonPosition, boolean disable) {
        disableProperties[buttonPosition] = disable;
    }

    private void updateDisableProperties() {
        List<Button> buttons = getButtons();
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setDisable(disableProperties[i]);
        }
    }

    private List<Button> getButtons() {
        return Arrays.asList(
                playQuarter0, playQuarter1, playQuarter2, playQuarter3,
                playSemi0, playSemi1,
                playFinals
        );
    }
}
