package fr.sudoku.controller;

import fr.sudoku.controller.command.*;
import fr.sudoku.model.DataModel;

/**
 * Controller for the action panel (left side)
 * @see Controller
 */
public class ActionController implements Controller {

	private CommandList commandList;

	private MainController parentController;

	@Override
	public void initialize(DataModel dataModel, MainController parentController, CommandList commandList) {
		this.commandList = commandList;
		this.parentController = parentController;
	}
}