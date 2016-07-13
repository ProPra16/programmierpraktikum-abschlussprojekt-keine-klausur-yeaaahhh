package de.hhu.propra16.tddt.gui;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.trainer.ConditionChecker;
import de.hhu.propra16.tddt.trainer.Phase;
import de.hhu.propra16.tddt.trainer.Trainer;
import de.hhu.propra16.tddt.userinterface.CodeField;
import de.hhu.propra16.tddt.userinterface.Editor;
import de.hhu.propra16.tddt.userinterface.SplitEditor;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController  {

    @FXML
    private CodeField codeTabs;

    @FXML
    private CodeField testTabs;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    @FXML
    private TextArea errorField;

    @FXML
    private Text phaseid;

    @FXML
    private Label timerLabel;

    private Trainer trainer;

    public void startTrainer(Exercise exercise) {
        Editor editor = new SplitEditor(codeTabs, testTabs);
        trainer = new Trainer(exercise,
                editor,
                (title, message) -> {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(title);
                        alert.setContentText(message);
                        alert.showAndWait();
                    });
                },
                new ConditionChecker());

        nextButton.disableProperty().bind(Bindings.not(trainer.phaseAcceptedProperty()));
        backButton.disableProperty().bind(Bindings.notEqual(Phase.GREEN, trainer.phaseProperty()));
        errorField.textProperty().bind(trainer.errorMessageProperty());

        trainer.timeProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> timerLabel.setText(newValue == null ?
                    "" : Long.toString(newValue.toMillis()/1000)));
        });

        trainer.phaseProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Phase.RED) {
                phaseid.setText("Red Phase");
                phaseid.setFill(Color.RED);
            }
            if (newValue == Phase.GREEN) {
                phaseid.setText("Green Phase");
                phaseid.setFill(Color.GREEN);
            }
            if (newValue == Phase.BLACK) {
                phaseid.setText("Refactor Phase");
                phaseid.setFill(Color.BLACK);
            }
        });
    }

    @FXML
    void checkPhase() {
        trainer.checkPhaseStatus();
    }

	@FXML
	void previousPhase(ActionEvent event) {
        trainer.previousPhase();
	}

    @FXML
    void quit() {
        Platform.exit();
    }

	@FXML
	void nextPhase(ActionEvent event) {
        trainer.nextPhase();
	}
}


