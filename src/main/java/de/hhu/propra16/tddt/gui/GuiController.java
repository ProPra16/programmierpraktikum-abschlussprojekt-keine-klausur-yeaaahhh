package de.hhu.propra16.tddt.gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
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
	void GuiController() {
	}

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
	void loadExcersise(ActionEvent event) {
		/// Ladeprozedur der Uebung hier
		try {
			Button b = (Button) event.getSource();
			Stage s = (Stage) b.getScene().getWindow();
			Pane myPane = null;
			myPane = FXMLLoader.load(getClass().getResource("/Scene.fxml"));
			Scene scene = new Scene(myPane);
			s.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@FXML
	void nextPhase(ActionEvent event) {
		Button b = (Button) event.getSource();
		Stage s = (Stage) b.getScene().getWindow();
		Text text = (Text) s.getScene().lookup("#phaseid");
		TextArea left = (TextArea) s.getScene().lookup("#leftText");
		TabPane right = (TabPane) s.getScene().lookup("#codeTabs");
		if(text.getText().contains("Red Phase")){
			left.setDisable(true);
			right.setDisable(false);
			text.setText("Green Phase");
			text.setFill(Color.GREEN);
		}else if(text.getText().contains("Refactor Phase")){
			right.setDisable(true);
			text.setText("Red Phase");
			text.setFill(Color.RED);
		}else if(text.getText().contains("Green Phase")){
			left.setDisable(false);
			text.setText("Refactor Phase");
			text.setFill(Color.BLACK);
		}


	}



}




