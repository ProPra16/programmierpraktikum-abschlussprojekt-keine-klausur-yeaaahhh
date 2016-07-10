package de.hhu.propra16.tddt.gui;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.exercise.ExerciseLoader;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Created by felix on 7/10/16.
 */
public class StartController {

    @FXML
    private ListView<Exercise> excersislist;

    @FXML
    private Button selectButton;

    @FXML
    private TextArea descriptionField;

    public void loadExList() {
        ExerciseLoader loader = new ExerciseLoader(getClass().getResource("/Exercise.xml"));
        List<Exercise> exercises = null;
        try {
            exercises = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        excersislist.getItems().setAll(exercises); // getItems().setAll(...) is okay, because exercises is immutable

        // the CellFactory extracts the names from the exercises
        excersislist.setCellFactory(lv -> new ListCell<Exercise>() {
            public void updateItem(Exercise item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });

        // Bind description field to the description of selected exercise
        descriptionField.textProperty().bind(Bindings.createStringBinding(() -> {
            Exercise ex = excersislist.getSelectionModel().getSelectedItem();
            return ex == null ? "" : ex.getDescription();
        }, excersislist.getSelectionModel().selectedItemProperty()));

        // Enable the select button only, if an exercise is selected
        selectButton.disableProperty().bind(Bindings.isNull(excersislist.getSelectionModel().selectedItemProperty()));

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
}
