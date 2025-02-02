package com.project.SudokuSolver.services.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.project.SudokuSolver.dto.SudokuDTO;
import com.project.SudokuSolver.models.Sudoku;

@Component
public class SudokuMapperImpl implements SudokuMapper {

	@Override
	public SudokuDTO toDTO(Sudoku sudoku) {

		SudokuDTO sudokuDTO = new SudokuDTO();
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				sudokuDTO.getNumbers()[i][j] = Integer.toString(sudoku.getNumbers()[i][j]);
			}
		}
		return sudokuDTO;
	}

	@Override
	public Sudoku toEntity(SudokuDTO sudokuDTO) {

		Sudoku sudoku = new Sudoku();
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				try {
					sudoku.getNumbers()[i][j] = Integer.parseInt(sudokuDTO.getNumbers()[i][j]);
//					System.out.print(sudokuDTO.getNumbers()[i][j] + " ");
				}
				catch (NumberFormatException e) {
					if(sudokuDTO.getNumbers()[i][j] == "") {
						sudoku.getNumbers()[i][j] = 0;
					}
					else {
						return null;
					}
				}
			}
		}
		
		return sudoku;
	}

	@Override
	public List<SudokuDTO> toListDTO(List<Sudoku> sudokuList) {
		
		return sudokuList.stream().map(sudoku -> toDTO(sudoku)).collect(Collectors.toList());
	}

	@Override
	public List<Sudoku> toListEntity(List<SudokuDTO> sudokuDTOList) {

		return sudokuDTOList.stream().map(sudoku -> toEntity(sudoku)).collect(Collectors.toList());
	}
}
