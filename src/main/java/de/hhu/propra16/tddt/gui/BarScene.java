package de.hhu.propra16.tddt.gui;

import de.hhu.propra16.tddt.trainer.Phase;
import de.hhu.propra16.tddt.trainer.Tracker;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BarScene {
    final static String rotePhase = "Rote Phase";
    final static String schwarzePhase = "Schwarze Phase";
    final static String gruenePhase = "Grüne Phase";


    public BarScene(){

    }
    public Scene createBarScene(Tracker t){
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

        Scene scene = new Scene(bc, 800, 600);
        scene.getStylesheets().add("./style.css");
        bc.getData().add(series1);
        bc.getData().add(series2);
        bc.getData().add(series3);


        return scene;
    }
}