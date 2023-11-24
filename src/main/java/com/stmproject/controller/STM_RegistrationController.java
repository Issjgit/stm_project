package com.stmproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stmproject.model.STM;
import com.stmproject.model.STM_Model;
import com.stmproject.repository.STM_RegistrationRepository;
import com.stmproject.service.STM_RegistrationService;

@Controller
public class STM_RegistrationController {

	@Autowired
	private STM_RegistrationService stmService;

	private String messageForRegister = "0";

	private static final String UPLOAD_DIR = "/STM_File";

	@GetMapping("/register")
	public String RegisterAction(Model model, @RequestParam("ssoid") String ssoid) {
		model.addAttribute("STM", new STM_Model());
		if (messageForRegister.equals("1"))
			model.addAttribute("message", "Registration successful!");
		else if (messageForRegister.equals("2"))
			model.addAttribute("error", "STM Number has already been issued!");
		model.addAttribute("ssoid", ssoid);
		return "RegistrationPage";
	}

	@Autowired
	STM_RegistrationRepository stm_RegistrationRepository;

	// Registration operation
	@PostMapping("/register")
	public String registerSTM(@ModelAttribute("STM") STM_Model stmModel, Model model,
			@RequestParam("ssoid") String ssoid,
			@RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
			@RequestParam(value = "wordFile", required = false) MultipartFile wordFile) {
		try {
			messageForRegister = "0";
			// Check if the STM number already exists
			if (stmService.isSTMNumberExists(stmModel.getStmNo()) != null) {
				// If the STM number already exists, return an error message
				messageForRegister = "2";
				return "redirect:/register?ssoid=" + ssoid;
			}

			// Save the uploaded files
			String pdfFileName = null;
			String wordFileName = null;
			if (pdfFile != null)
				pdfFileName = saveFile(pdfFile, "pdf");
			if (wordFile != null)
				wordFileName = saveFile(wordFile, "doc");
			// If the STM number doesn't exist, proceed with registration
			STM stm = new STM();
			stm.setNo(getNextCustomFieldValue());
			stm.setStmNo(stmModel.getStmNo());
			stm.setStmVersion("00");
			stm.setLinkDestination(stmModel.getLinkDestination());
			stm.setTextShortJP(stmModel.getTextShortJP());
			stm.setTextShortEN(stmModel.getTextShortEN());
			stm.setPdfFile(pdfFileName);
			stm.setWordFile(wordFileName);
			stm.setDraftingDate(stmModel.getDraftingDate());
			stm.setFinalDrafterName(stmModel.getFinalDrafterName());
			stm.setOldSTMNumber(stmModel.getOldSTMNumber());
			stm.setRemarks1(stmModel.getRemarks1());
			stm.setNote2(stmModel.getNote2());
			stm.setNote3(stmModel.getNote3());
			stm.setCreatorSSOID(ssoid);
			stm.setCreatedDate(new Date());
			stm.setLastUpdated(new Date());
			stm.setIsDeleted(false);

			stmService.saveSTM(stm);
			messageForRegister = "1";
			return "redirect:/register?ssoid=" + ssoid;
		} catch (Exception e) {
			e.printStackTrace();
			messageForRegister = "2";
			return "redirect:/register?ssoid=" + ssoid;
		}
	}

	private Integer getNextCustomFieldValue() {
		Integer maxCustomFieldValue = stm_RegistrationRepository.findMaxCustomFieldValue().orElse(0);
		return maxCustomFieldValue + 1;
	}

	private String saveFile(MultipartFile file, String extension) throws IOException {
		if (file != null && !file.isEmpty()) {
			String originalFileName = file.getOriginalFilename();
			Path uploadPath = Paths.get("/STM_File", UPLOAD_DIR);
			Path filePath = uploadPath.resolve(originalFileName);
			Files.createDirectories(uploadPath);
			System.out.println("filePath " + filePath);
			// Save the file to the specified directory
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			return originalFileName;
		}
		return null;
	}

	@GetMapping("/returnAdminDashboard")
	public String returnToAdminDashBoard() {
		return "AdminDashBoard";
	}

}
