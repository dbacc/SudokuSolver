package com.project.SudokuSolver.services;

import com.project.SudokuSolver.dto.SudokuDTO;
import com.project.SudokuSolver.exceptions.UnsolvableSudokuException;
import com.project.SudokuSolver.models.Sudoku;

public interface SudokuService {

	void inputSudokuTask(Sudoku sudoku);
	SudokuDTO solve(Sudoku sudoku) throws UnsolvableSudokuException;
}
