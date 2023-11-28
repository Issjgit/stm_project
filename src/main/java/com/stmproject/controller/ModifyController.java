package com.stmproject.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stmproject.model.STM;

import com.stmproject.service.ModifyService;

@Controller
public class ModifyController {

	@Autowired
	private ModifyService modifyService;

	@GetMapping("modify/{stmNo}")
	public String ModifyPage(@PathVariable String stmNo, Model model) {
		STM stm = modifyService.getSTMByStmNoQuery(stmNo);
		model.addAttribute("stm", stm);
		return "ModifyScreen";
	}

	
	
	
	@PostMapping("/modify")
	public String modifySTM(@ModelAttribute ("stm")STM modifySTM,BindingResult result,Model model,
	    @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
	    @RequestParam(value = "wordFile", required = false) MultipartFile wordFile
	) throws IOException {
	    System.out.println(modifySTM);
	    System.out.println(modifySTM.getStmNo());

	    try {
	        // Modify the STM with the provided STM Number
	    	modifyService.modifySTM(modifySTM, pdfFile, wordFile);

	        // Retrieve the updated STM
	        STM stm = modifyService.getSTMByStmNoQuery(modifySTM.getStmNo());

	        // Add success message to the model
	        model.addAttribute("message", "STM Modify successfully!");
	        model.addAttribute("stm", stm);
	    } catch (Exception e) {
	        // Add error message to the model
	        model.addAttribute("error", "Failed to Modify STM. Please try again.");
	        e.printStackTrace(); 
	    }

	    return "ModifyScreen";
	}
	
	
	@GetMapping("/returnSearchPage")
	public String returnToSearchPage() {
		return "SearchPage";
	}
	
	
}
