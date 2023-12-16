package fr.sudoku.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DataModel {

    private final ObjectProperty<Board> board = new SimpleObjectProperty<>(null);

    public void setBoard(Board board) {
        this.board.set(board);
    }

    public ObjectProperty<Board> boardProperty() {
        return this.board;
    }

    public void setCellValue(int i, int j, int value) {
        this.board.get().setCellValue(i, j, value);
    }
}
