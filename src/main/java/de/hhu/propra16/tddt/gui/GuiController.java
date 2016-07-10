package de.hhu.propra16.tddt.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable  {
	
	
	public Stage nextStage, previousStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void setStages(Stage nextStage, Stage previousStage){
		this.nextStage = nextStage;
		this.previousStage = previousStage;
	}
	
	@FXML
	void next(ActionEvent event) {
		Button b = (Button) event.getSource();
		Stage s = (Stage) b.getScene().getWindow();
		nextStage.show();
		TextArea t =(TextArea) nextStage.getScene().lookup("#useredit");
		t.clear();
		s.hide();
	}
	
	@FXML
	void previous(ActionEvent event) {
	
		Button b = (Button) event.getSource();
		Stage s = (Stage) b.getScene().getWindow();
        previousStage.show();
        s.hide();
    }


}
