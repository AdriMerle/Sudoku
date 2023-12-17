package fr.sudoku.model;

import javafx.collections.ObservableList;

import java.util.Arrays;

public enum Difficulty {
	EASY(30), MEDIUM(40), HARD(50);

	private final int nbCellsToHide;

	private Difficulty(int nbCellsToHide) {
		this.nbCellsToHide = nbCellsToHide;
	}

	public int getNbCellsToHide() {
		return nbCellsToHide;
	}

	// Return all the possible difficulties
	public static ObservableList<Difficulty> getAllDifficulties() {
		Difficulty[] values = Difficulty.values();
		ObservableList<Difficulty> difficulties = javafx.collections.FXCollections.observableArrayList();
		difficulties.addAll(Arrays.asList(values));
		return difficulties;
	}
}