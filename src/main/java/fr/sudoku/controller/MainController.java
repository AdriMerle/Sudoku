package fr.sudoku.controller;

import fr.sudoku.controller.command.CommandList;
import fr.sudoku.model.DataModel;
import fr.sudoku.view.PopUp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.*;
import java.util.logging.Logger;

public class MainController implements Controller {

	private static final int MESSAGE_DISPLAY_TIME = 5 * 1000;

	@FXML
	private HBox panelsContainer;

	@FXML
	private Label toolBarMessage;

	private CommandList commandList;

	private DataModel dataModel;

	/**
	 * In this implementation, the parent controller is ignored, as it is supposed
	 * to be this controller
	 */
	@Override
	public void initialize(DataModel dataModel, MainController parentController, CommandList commandList) {
		this.commandList = commandList;
		this.dataModel = dataModel;

		try {
			String[] panelPaths = { "ActionPanel.fxml", "BoardPanel.fxml" };

			for (String panelPath : panelPaths) {
				loadPanel(panelPath);
			}

		} catch (Exception e) {
			Logger logger = Logger.getLogger(getClass().getName());
			logger.severe("An error occurred while loading the view.");
			logger.severe(e.getMessage());
			panelsContainer.getChildren().clear();
			panelsContainer.getChildren().add(new Pane(new Label("An error occurred while loading the view.")));
		}
	}

	private void loadPanel(String path) throws IOException, IllegalStateException {
		FXMLLoader panelLoader = new FXMLLoader(getClass().getClassLoader().getResource(path));
		Parent panel = panelLoader.load();
		Controller controller = panelLoader.getController();
		controller.initialize(this.dataModel, this, commandList);
		panelsContainer.getChildren().add(panel);
	}

	/**
	 * Displays the given exception's message in the toolbar.<br/>
	 * The message is removed after a delay
	 * 
	 * @param e - the exception to display
	 */
	public void displayToolBarMessage(Exception e) {
		displayToolBarMessage(e.getMessage());
	}

	/**
	 * Displays the given message in the toolbar.<br/>
	 * The message is removed after a delay
	 * 
	 * @param message - the message to display
	 */
	public void displayToolBarMessage(String message) {
		toolBarMessage.setText(message);

		// Clear the message after some delay
		new Thread(() -> {
			try {
				Thread.sleep(MESSAGE_DISPLAY_TIME);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			Platform.runLater(() -> toolBarMessage.setText(""));
		}).start();
	}

	@FXML
	private void quitApplication() {
		System.exit(0);
	}

	@FXML
	private void redoCommand() {
		this.commandList.redo();
	}

	@FXML
	private void undoCommand() {
		this.commandList.undo();
	}

	@FXML
	private void showPopupVersion() {
		PopUp popUpVersion = new PopUp("About", "Version 1.0\n©Hunkanome");
		popUpVersion.show();
	}
}
