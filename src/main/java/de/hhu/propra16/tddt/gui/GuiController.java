package de.hhu.propra16.tddt.gui;

import de.hhu.propra16.tddt.exercise.ExerciseLoader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.xml.sax.InputSource;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

	private ExerciseLoader e;
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
	private TextArea descriptionField;

	@FXML
	void backbutton(ActionEvent event) {
		Button b = (Button) event.getSource();
		Stage s = (Stage) b.getScene().getWindow();
		Text text = (Text) s.getScene().lookup("#phaseid");
		TextArea left = (TextArea) s.getScene().lookup("#leftText");
		TabPane right = (TabPane) s.getScene().lookup("#codeTabs");
		if(text.getText().contains("Green Phase")){
			left.setDisable(false);
			right.setDisable(true);
			text.setText("Red Phase");
			text.setFill(Color.RED);
		}
	}


	@FXML
	void loadDescription(MouseEvent event) {
		if (excersislist.getSelectionModel().getSelectedItem()!=null)
			descriptionField.setText(e.ExDescriptions.get(excersislist.getSelectionModel().getSelectedIndex()).toString());

	}
	@FXML
	private ListView<?> excersislist;

	public static final ObservableList data = FXCollections.observableArrayList();
	@FXML
	public void initialize(URL location, ResourceBundle resources) {



	}

	public void loadExList(){
		Path p = Paths.get("katalog/Exercise.xml");
		InputSource in = new InputSource(p.toString());
		e = new ExerciseLoader(in);
		excersislist.setItems(e.ExTitles);
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


