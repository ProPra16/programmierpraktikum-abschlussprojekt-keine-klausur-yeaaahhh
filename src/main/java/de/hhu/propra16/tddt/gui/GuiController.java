package de.hhu.propra16.tddt.gui;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.trainer.CheckCompile;
import de.hhu.propra16.tddt.trainer.ConditionChecker;
import de.hhu.propra16.tddt.trainer.Phase;
import de.hhu.propra16.tddt.trainer.Trainer;
import de.hhu.propra16.tddt.userinterface.CodeField;
import de.hhu.propra16.tddt.userinterface.Editor;
import de.hhu.propra16.tddt.userinterface.SplitEditor;
import de.hhu.propra16.tddt.userinterface.TabCodeField;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private Parent root;

	@FXML
	private URL location;

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

    private Trainer trainer;

    public void startTrainer(Exercise exercise) {
        Editor editor = new SplitEditor(codeTabs, testTabs);
        // TODO CHANGE MESSAGEDISPLAY TO SOMETHING NOT NULL, REALLY IMPORTANT
        trainer = new Trainer(exercise, editor, null, new ConditionChecker());

        nextButton.disableProperty().bind(Bindings.not(trainer.phaseAcceptedProperty()));
        backButton.disableProperty().bind(Bindings.notEqual(Phase.GREEN, trainer.phaseProperty()));
        errorField.textProperty().bind(trainer.errorMessageProperty());

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

	public static final ObservableList data = FXCollections.observableArrayList();
	@FXML
	public void initialize(URL location, ResourceBundle resources) {


	}

	@FXML
	void addTab(Event event) {
		Tab t = (Tab) event.getSource();
		TabPane tabs = t.getTabPane();
		final Tab tab = new Tab("Tab " + (tabs.getTabs().size() + 1));
		tabs.getTabs().add(tab);
		tabs.getSelectionModel().select(tab);
		tabs.getTabs().remove(tabs.getTabs().size()-2, tabs.getTabs().size()-1);
		tabs.getTabs().add(t);
		TextArea ta = new TextArea();
		ta.setPrefSize(218, 469);
		Pane p = new Pane();
		p.setPrefSize(285,520);
		p.getChildren().add(ta);
		tab.setContent(p);
	}




	@FXML
	void nextPhase(ActionEvent event) {
        trainer.nextPhase();
	}
}


