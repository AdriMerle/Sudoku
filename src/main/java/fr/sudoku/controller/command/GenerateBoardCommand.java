package fr.sudoku.controller.command;

import fr.sudoku.model.Board;
import fr.sudoku.model.DataModel;
import fr.sudoku.model.Difficulty;

public class GenerateBoardCommand implements Command {
    private final DataModel dataModel;

    private final Difficulty difficulty;

    /**
     * Used when initializing the command
     * @param dataModel the dataModel where the information is stored
     * @param difficulty the difficulty of the board to generate
     */
    public GenerateBoardCommand(DataModel dataModel, Difficulty difficulty) {
        this.dataModel = dataModel;
        this.difficulty = difficulty;
    }

    /**
     * Add the delivery to the tour and recalculate the graph
     */
    @Override
    public void doCommand() {
        this.dataModel.generateBoard(this.difficulty);
    }

    /**
     * Undoes the addition of the delivery and recalculate the graph
     */
    @Override
    public void undoCommand() {
        this.dataModel.setBoard(new Board());
    }
}
