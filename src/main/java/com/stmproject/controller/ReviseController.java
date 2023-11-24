package com.stmproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.stmproject.model.STM;
import com.stmproject.service.ReviseService;
import com.stmproject.service.STM_HistoryService;

@Controller
public class ReviseController {
	@Autowired
	private STM_HistoryService stmHistoryService;
	
	@Autowired
	private ReviseService reviseService;

	@GetMapping("/revise/{id}")
	public String reviewSTM(@PathVariable String id, Model model) {
	    STM stm = reviseService.getSTMById(id);
	    model.addAttribute("stm", stm);
	    return "ReviseScreen";
	}
	
	@PostMapping("/revise")
	public String updateSTM(@ModelAttribute STM updatedSTM,Model model) {	    
	    System.out.println(updatedSTM);
	    // Update the STM with the provided STM Number
	    reviseService.updateSTM(updatedSTM);
	    model.addAttribute("stm", updatedSTM);
	    return "ReviseScreen";
	}

}
