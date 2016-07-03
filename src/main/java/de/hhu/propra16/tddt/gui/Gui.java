package de.hhu.propra16.tddt.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GuiStart.fxml"));
        Parent root = loader.load();
        window.setScene(new Scene(root, 640, 420));
        window.show();
    }
}
