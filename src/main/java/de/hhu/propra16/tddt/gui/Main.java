package de.hhu.propra16.tddt.gui;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	Stage redstage;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage redStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/GuiRed.fxml"));     
		Parent root = (Parent)fxmlLoader.load();          
		GuiController controller = fxmlLoader.<GuiController>getController();
		redStage.setTitle("TDDT");
		redStage.setScene(new Scene(root,600, 600));
		redStage.show();
		
		StageContainer greenSC = createStage("/main/resources/GuiGreen.fxml");
		StageContainer refactorCodeSC = createStage("/main/resources/GuiRefactorCode.fxml");
		StageContainer refactorTestSC = createStage("/main/resources/GuiRefactorTest.fxml");
		
		
		greenSC.c.setStages(refactorCodeSC.s, redStage);
		refactorCodeSC.c.setStages(refactorTestSC.s, greenSC.s);
		refactorTestSC.c.setStages(redStage,refactorCodeSC.s);
		controller.setStages(greenSC.s, null);

	}
	
	public StageContainer createStage(String fxml) throws IOException{
		Stage s = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));     
		Parent root = (Parent)fxmlLoader.load();   
		GuiController controller = fxmlLoader.<GuiController>getController();
		s.setTitle("TDDT");
		s.setScene(new Scene(root,600, 600));
		StageContainer sc = new StageContainer(s,controller);
		return sc;
	}
}
