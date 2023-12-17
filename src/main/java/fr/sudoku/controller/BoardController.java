package fr.sudoku.controller;

import fr.sudoku.controller.command.CommandList;
import fr.sudoku.model.Board;
import fr.sudoku.model.DataModel;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class BoardController implements Controller {

    @FXML
    private AnchorPane anchorPane;

    private MainController parentController;

    private DataModel dataModel;

    private static final int CELL_SIZE = 50;
    private static final int MARGIN = 5;

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
        int size = 9;
        int boardSize = size * CELL_SIZE + (size + 1) * MARGIN;
        this.anchorPane.setPrefSize(boardSize, boardSize);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TextField textField = new TextField();
                formatTextField(textField, i, j);
                this.anchorPane.getChildren().add(textField);
            }
        }
    }

    public void formatTextField(TextField textField, int i, int j) {
        int y = MARGIN + i * (CELL_SIZE + MARGIN) + i/3 * MARGIN * 2;
        int x = MARGIN + j * (CELL_SIZE + MARGIN) + j/3 * MARGIN * 2;
        Board board = this.dataModel.boardProperty().get();
        textField.setPrefSize(CELL_SIZE, CELL_SIZE);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        if(board.getCellValue(i, j) != 0) {
            textField.setText(String.valueOf(board.getCellValue(i, j)));
            if (board.isFixed(i, j)) {
                textField.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: #d3d3d3");
                textField.setEditable(false);
            }
        } else {
            textField.setText("");
        }
        textField.setAlignment(javafx.geometry.Pos.CENTER);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                textField.setText(oldValue);
            } else {
                try {
                    int value = Integer.parseInt(newValue);
                    this.dataModel.setCellValue(i, j, value);
                } catch (NumberFormatException | IllegalStateException e) {
                    drawBoard();
                    this.parentController.displayToolBarMessage(e);
                }
            }
        });
    }
}
