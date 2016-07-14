package de.hhu.propra16.tddt.gui;

import de.hhu.propra16.tddt.trainer.Phase;
import de.hhu.propra16.tddt.trainer.Tracker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class BarScene {

    private Tracker tracker;

    @FXML
    private Pane root;

    private final static String rotePhase = "Rote Phase";
    private final static String schwarzePhase = "Schwarze Phase";
    private final static String gruenePhase = "Grüne Phase";

    private Path chooseFile() {
        FileChooser dialog = new FileChooser();
        dialog.setTitle("Choose location");
        dialog.setInitialFileName("tracking.data");
        File file = dialog.showSaveDialog(root.getScene().getWindow());
        if (file == null) return null;
        return file.toPath();
    }

    public void saveData() {
        if (tracker == null) return;
        Path path = chooseFile();
        if (path != null) {
            try {
                tracker.saveTo(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Scene createBarScene(Tracker t) {
        Pane root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Graph.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loader.<BarScene>getController().tracker = t;

        Node bc = createGraph(t);
        root.getChildren().add(bc);
        bc.toBack();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("/style.css");
        return scene;
    }

    private Node createGraph(Tracker t) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Tracking Chart");
        xAxis.setLabel("Phase");
        yAxis.setLabel("Zeit in sek.");

        long redTime = t.getTotalTime(Phase.RED).getSeconds();
        long greenTime = t.getTotalTime(Phase.GREEN).getSeconds();
        long blackTime = t.getTotalTime(Phase.BLACK).getSeconds();

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Rote Phase");
        series1.getData().add(new XYChart.Data<>(rotePhase, redTime));


        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Grüne Phase");
        series2.getData().add(new XYChart.Data<>(gruenePhase, greenTime));


        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Schwarze Phase");
        series3.getData().add(new XYChart.Data<>(schwarzePhase, blackTime));

        bc.getData().add(series1);
        bc.getData().add(series2);
        bc.getData().add(series3);


        return bc;
    }
}