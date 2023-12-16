package fr.sudoku.controller;

import fr.sudoku.controller.command.CommandList;
import fr.sudoku.model.Board;
import fr.sudoku.model.DataModel;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        int margin = 5;
        int boardSize = size * cellSize + (size + 1) * margin;
        this.anchorPane.setPrefSize(boardSize, boardSize);
        for (int i = 0; i < size; i++) {
            int x = margin + i * (cellSize + margin) + i/3 * margin;
            for (int j = 0; j < size; j++) {
                int y = margin + j * (cellSize + margin) + j/3 * margin;

                TextField textField = new TextField();
                textField.setPrefSize(cellSize, cellSize);
                textField.setLayoutX(x);
                textField.setLayoutY(y);
                textField.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                if(board.getCellValue(i, j) != 0) {
                    textField.setText(String.valueOf(board.getCellValue(i, j)));
                } else {
                    textField.setText("");
                }
                textField.setAlignment(javafx.geometry.Pos.CENTER);
                int finalI = i;
                int finalJ = j;
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.length() > 1) {
                        textField.setText(oldValue);
                    } else {
                        try {
                            int value = Integer.parseInt(newValue);
                            this.dataModel.setCellValue(finalI, finalJ, value);
                        } catch (NumberFormatException | IllegalStateException e) {
                            textField.setText(oldValue);
                            this.parentController.displayToolBarMessage(e.getMessage());
                        }
                    }
                });
                this.anchorPane.getChildren().add(textField);
            }
        }
    }
}
