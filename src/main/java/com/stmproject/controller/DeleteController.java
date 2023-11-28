package com.stmproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.stmproject.model.STM;
import com.stmproject.service.DeleteService;

@Controller
public class DeleteController {

	@Autowired
	private DeleteService deleteService;

	@PostMapping("/delete")
	public String deleteStm(@ModelAttribute("stm") STM stm, BindingResult result, Model model) {
		boolean isDeleted = deleteService.isDeleteStm(stm.getStmNo());
		if (isDeleted) {
			model.addAttribute("message", "STM deleted successfully!");
		} else {
			model.addAttribute("error", "STM not found. Deletion failed.");
		}
		return "ModifyScreen";
	}
}

