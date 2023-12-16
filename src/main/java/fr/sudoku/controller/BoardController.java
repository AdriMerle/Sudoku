package fr.sudoku.controller;

import fr.sudoku.controller.command.CommandList;
import fr.sudoku.model.Board;
import fr.sudoku.model.DataModel;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BoardController implements Controller {

    @FXML
    private AnchorPane anchorPane;

    private MainController parentController;

    private DataModel dataModel;

    @Override
    public void initialize(DataModel dataModel, MainController parentController, CommandList commandList) {
        this.parentController = parentController;
        this.dataModel = dataModel;

        this.dataModel.boardProperty().addListener(this::onBoardUpdate);
    }

    private void onBoardUpdate(ObservableValue<? extends Board> observable, Board oldValue, Board newValue) {
        drawBoard();
        this.parentController.displayToolBarMessage("Board updated");
    }

    private void drawBoard() {
        this.anchorPane.getChildren().clear();
        Board board = this.dataModel.boardProperty().get();
        int size = 9;
        int cellSize = 50;
        int margin = 10;
        int boardSize = size * cellSize + (size + 1) * margin;
        this.anchorPane.setPrefSize(boardSize, boardSize);
        for (int i = 0; i < size; i++) {
            int x = margin + i * (cellSize + margin);
            for (int j = 0; j < size; j++) {
                int y = margin + j * (cellSize + margin);
                Label label = new Label();
                label.setPrefSize(cellSize, cellSize);
                label.setLayoutX(x);
                label.setLayoutY(y);
                label.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                label.setText(String.valueOf(board.getCellValue(i, j)));
                label.setAlignment(javafx.geometry.Pos.CENTER);
                this.anchorPane.getChildren().add(label);
            }
        }
    }
}
