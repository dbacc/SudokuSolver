package com.project.SudokuSolver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.SudokuSolver.dto.SudokuDTO;
import com.project.SudokuSolver.exceptions.UnsolvableSudokuException;
import com.project.SudokuSolver.models.Sudoku;
import com.project.SudokuSolver.services.SudokuService;
import com.project.SudokuSolver.services.utils.SudokuMapper;

@Controller
public class SudokuController {

	private final SudokuService sudokuService;
	private final SudokuMapper sudokuMapper;
	
	@Autowired
	public SudokuController(SudokuService sudokuService, SudokuMapper sudokuMapper) {
		
		this.sudokuService = sudokuService;
		this.sudokuMapper = sudokuMapper;
	}
	
	@GetMapping("/")
	public String sudoku(Model model) {
		
		model.addAttribute("solved", false);
		model.addAttribute("error", false);
		model.addAttribute("sudoku", new SudokuDTO());
		return "/index";
	}
	
	@PostMapping("/")
	public String task(Model model, @ModelAttribute SudokuDTO sudokuDTO) {
		
		Sudoku sudoku = sudokuMapper.toEntity(sudokuDTO);
		if(sudoku == null) {
			
			return "redirect:/";
		}
		
		SudokuDTO sudokuSolution = new SudokuDTO();
		try {
			sudokuSolution = sudokuService.solve(sudoku);
		}
		catch (UnsolvableSudokuException e) {
			
			model.addAttribute("solved", false);
			model.addAttribute("sudoku", sudokuDTO);
			model.addAttribute("error", true);
			return "/index";
		}
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				System.out.print(sudokuSolution.getNumbers()[i][j] + " ");
			}
			System.out.println();
		}
		
		model.addAttribute("solved", true);
		model.addAttribute("sudokuTask", sudokuDTO);
		model.addAttribute("sudoku", sudokuSolution);
		model.addAttribute("error", false);
		
		return "/index";
	}
}
