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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GuiRed.fxml"));
        Parent root = loader.load();
        GuiController controller = loader.getController();
        redStage.setTitle("TDDT");
        redStage.setScene(new Scene(root, 600, 600));
        redStage.show();

        StageContainer greenSC = createStage("/GuiGreen.fxml");
        StageContainer refactorCodeSC = createStage("/GuiRefactorCode.fxml");
        StageContainer refactorTestSC = createStage("/GuiRefactorTest.fxml");


        greenSC.c.setStages(refactorCodeSC.s, redStage);
        refactorCodeSC.c.setStages(refactorTestSC.s, greenSC.s);
        refactorTestSC.c.setStages(redStage,refactorCodeSC.s);
		controller.setStages(greenSC.s, null);

	}
	
	public StageContainer createStage(String fxml) throws IOException{
		Stage s = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = fxmlLoader.load();
        GuiController controller = fxmlLoader.getController();
        s.setTitle("TDDT");
        s.setScene(new Scene(root, 600, 600));
        return new StageContainer(s, controller);
    }
}
