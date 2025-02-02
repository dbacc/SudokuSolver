package com.project.SudokuSolver.services.utils;

import java.util.List;

import com.project.SudokuSolver.dto.SudokuDTO;
import com.project.SudokuSolver.models.Sudoku;

public interface SudokuMapper {

	SudokuDTO toDTO(Sudoku sudoku);
	
	Sudoku toEntity(SudokuDTO sudokuDTO);
	
	List<SudokuDTO> toListDTO(List<Sudoku> sudokuList);
	
	List<Sudoku> toListEntity(List<SudokuDTO> sudokuDTOList);
}
