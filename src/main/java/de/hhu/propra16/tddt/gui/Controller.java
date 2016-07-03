package de.hhu.propra16.tddt.gui;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.exercise.ExerciseLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Controller {
    private final static String[] COLOR = {"RED", "GREEN", "BLACK"};
    private final static String[] PHASE = {"Test Phase", "Code Phase", "Refactor Phase"};
    private int index = 0;
    private String tmpItem;

    @FXML
    BorderPane tddt, tddtstart;
    @FXML
    Label phase;
    @FXML
    TextArea errors, code;
    @FXML
    TextField description;
    @FXML
    ListView view;
    @FXML
    Button back, next, check, toTddt, exercises;

    public void checkCode() {
        boolean condition1 = true;
        if (condition1) next.setDisable(false);
        if (condition1 && index == 1) back.setVisible(true);
    }

    public void goBack() {
        changePhase("-");
        code.setText(String.valueOf(index));
        System.out.println(code.getText());
        phase.setText(PHASE[index]);
        phase.setTextFill(Paint.valueOf(COLOR[index]));

        back.setVisible(false);
        next.setDisable(true);
    }

    public void goNext() {
        changePhase("+");
        code.setText(String.valueOf(index));
        phase.setText(PHASE[index]);
        phase.setTextFill(Paint.valueOf(COLOR[index]));

        back.setVisible(false);
        next.setDisable(true);
    }

    private void changePhase(String sign) {
        if (sign.equals("+")) {
            index++;
            if (index > 2) index = 0;
        } else {
            index--;
            if (index < 0) index = 2;
        }
    }

    public void startTddt() throws IOException {
        Stage window = (Stage) tddtstart.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Gui.fxml"));
        window.setScene(new Scene(root, 640, 420));
    }

    public void loadFile() {
        ObservableList<String> items = FXCollections.observableArrayList();
        Path p = Paths.get("katalog/Exercise.xml");
        List<Exercise> list = ExerciseLoader.loadFile(new InputSource(p.toString()));
        list.forEach((exercise -> items.add(exercise.getName())));
        view.setItems(items);
    }

    public void hasSelected() {
        for (int i = 0; i < view.getItems().size(); i++) {
            if (view.getSelectionModel().isSelected(i)) toTddt.setDisable(false);
        }
    }
}
