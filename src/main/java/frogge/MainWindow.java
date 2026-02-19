package frogge;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Frogge frogge;

    private final Image USER_IMAGE = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private final Image FROGGE_IMAGE = new Image(this.getClass().getResourceAsStream("/images/frogge.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Frogge instance */
    public void setFrogge(Frogge f) {
        frogge = f;
        dialogContainer.getChildren().add(DialogBox.getFroggeDialog(f.initGreeting(), FROGGE_IMAGE));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Frogge's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = frogge.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, USER_IMAGE),
                DialogBox.getFroggeDialog(response, FROGGE_IMAGE)
        );
        userInput.clear();
        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
