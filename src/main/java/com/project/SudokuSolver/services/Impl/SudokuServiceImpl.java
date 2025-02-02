package com.project.SudokuSolver.services.Impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SudokuSolver.dto.SudokuDTO;
import com.project.SudokuSolver.exceptions.UnsolvableSudokuException;
import com.project.SudokuSolver.models.Sudoku;
import com.project.SudokuSolver.services.SudokuService;
import com.project.SudokuSolver.services.utils.SudokuMapper;

@Service
public class SudokuServiceImpl implements SudokuService {

	private int[][] sudokuSolution = new int[9][9];
	private final SudokuMapper sudokuMapper;
	
	@Autowired
	public SudokuServiceImpl(SudokuMapper sudokuMapper) {
		this.sudokuMapper = sudokuMapper;
	}
	
	@Override
	public void inputSudokuTask(Sudoku sudokuTask) {
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				sudokuSolution[i][j] = sudokuTask.getNumbers()[i][j];
			}
		}
	}

	@Override
	public SudokuDTO solve(Sudoku sudokuTask) throws UnsolvableSudokuException {
		
		inputSudokuTask(sudokuTask);

		LocalDateTime startTime = LocalDateTime.now();
		boolean forwardDirection = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				LocalDateTime currentTime = LocalDateTime.now();
				Long elapsedTime = Duration.between(startTime, currentTime).toSeconds();
				if(elapsedTime >= 30) {
					System.out.println("Timed out");
					throw new UnsolvableSudokuException("Duration timed out");
				}
				
				if (forwardDirection == false && sudokuTask.getNumbers()[i][j] != 0) {

					if (j > 0) {
						j -= 2;
					} else if (i > 0) {
						i--;
						j = 7;
					} else {
						throw new UnsolvableSudokuException("Unsolvable sudoku");		
					}
				} else if (sudokuTask.getNumbers()[i][j] == 0) {

					while (sudokuSolution[i][j] <= 9) {

						sudokuSolution[i][j]++;
						if (freeValueCheck(i, j)) {

							forwardDirection = true;
							break;
						}
					}
					if (sudokuSolution[i][j] == 10) {

						forwardDirection = false;
						sudokuSolution[i][j] = 0;

						if (j > 0) {
							j -= 2;
						} else if (i > 0) {
							i--;
							j = 7;
						} else {		
							throw new UnsolvableSudokuException("Unsolvable sudoku");
						}
					}
				}
			}
		}
		Sudoku solvedSudoku = new Sudoku(sudokuSolution);
		
		return sudokuMapper.toDTO(solvedSudoku);
	}
	
	private boolean freeValueCheck(int row, int column) {
		
		for (int i = 0; i < sudokuSolution.length; i++) {
			for (int j = 0; j < sudokuSolution[i].length; j++) {

				if ((i == row || j == column) && !(i == row && j == column)) {
					if (sudokuSolution[row][column] == sudokuSolution[i][j]) {
						return false;
					}
				}
			}
		}

		int sqRow = (int) (row / 3) * 3;
		int sqColumn = (int) (column / 3) * 3;
		for (int sqi = sqRow; sqi < sqRow + 3; sqi++) {
			for (int sqj = sqColumn; sqj < sqColumn + 3; sqj++) {
				if (sudokuSolution[row][column] == sudokuSolution[sqi][sqj] && !(sqi == row && sqj == column)) {
					return false;
				}
			}
		}

		return true;
	}
}
