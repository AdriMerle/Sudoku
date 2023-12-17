package fr.sudoku.controller;

import fr.sudoku.controller.command.*;
import fr.sudoku.model.DataModel;
import fr.sudoku.model.Difficulty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * Controller for the action panel (left side)
 * @see Controller
 */
public class ActionController implements Controller {

	private CommandList commandList;

	private MainController parentController;

	private DataModel dataModel;

	@FXML
	private ComboBox<Difficulty> difficultyChooser;

	@Override
	public void initialize(DataModel dataModel, MainController parentController, CommandList commandList) {
		this.dataModel = dataModel;
		this.commandList = commandList;
		this.parentController = parentController;

		this.difficultyChooser.setItems(Difficulty.getAllDifficulties());
	}

	@FXML
	private void generateBoard() {
		Difficulty selectedDifficulty = this.difficultyChooser.getValue();
		if (selectedDifficulty == null) {
			this.parentController.displayToolBarMessage("No difficulty selected");
			return;
		}
		Command command = new GenerateBoardCommand(this.dataModel, selectedDifficulty);
		this.commandList.execute(command);
		this.parentController.displayToolBarMessage("Board generated");
	}
}