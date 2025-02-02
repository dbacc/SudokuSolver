package com.project.SudokuSolver.exceptions;

public class UnsolvableSudokuException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnsolvableSudokuException(String message) {
		super(message);
	}
}
