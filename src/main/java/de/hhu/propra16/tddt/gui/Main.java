package de.hhu.propra16.tddt.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage redStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Start.fxml"));
        Parent root = loader.load();
        StartController controller = loader.getController();
        redStage.setTitle("TDDT");
        redStage.setScene(new Scene(root, 800, 600));
        controller.loadExList();


        redStage.show();

    }

}
