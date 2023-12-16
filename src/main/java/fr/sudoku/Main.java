package fr.sudoku;

import fr.sudoku.controller.MainController;
import fr.sudoku.controller.command.CommandList;
import fr.sudoku.model.DataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		CommandList commandList = new CommandList();
		DataModel dataModel = new DataModel();
		
		FXMLLoader rootLoader = new FXMLLoader(getClass().getClassLoader().getResource("Main.fxml"));
		Parent root = rootLoader.load();
		MainController mainController = rootLoader.getController();
		mainController.initialize(dataModel, null, commandList);

		Scene scene = new Scene(root);

		// Set the application icon
		// primaryStage.getIcons().add(new Image("file:images/logo.png"));
		primaryStage.setTitle("Sudoku");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}