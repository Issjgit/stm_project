package com.stmproject.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.stmproject.model.SearchResultlist;
import com.stmproject.model.STM;
import com.stmproject.repository.STM_RegistrationRepository;
import com.stmproject.service.STM_RegistrationService;
import com.stmproject.service.S_UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class SearchController {

	@Autowired
	public HttpSession session;

	ModelAndView modelAndView;

	@Autowired
	private S_UserService sUserService;

	@Autowired
	STM_RegistrationRepository stm_RegistrationRepository;

	@Autowired
	STM_RegistrationService stmService;

	public SearchController() {
		modelAndView = new ModelAndView();
	}

	@GetMapping("/search")
	public ModelAndView searchPage(Model model,@RequestParam("ssoid") String ssoid) {
		model.addAttribute("STM", "1");
		model.addAttribute("ssoid", ssoid);
		modelAndView.setViewName("SearchPage");
		return modelAndView;
	}

	// After Clicking Cancel button from Modify Page
	@SuppressWarnings("unchecked")
	@PostMapping("/goBackToSearchAfterResetClick")
	public ModelAndView resetBtnClick(Model model) {
		System.out.println("Welocme to Reset  Method");
		List<STM> searchList = new ArrayList<>();
		searchList = (List<STM>) session.getAttribute("ListData");
		model.addAttribute("list", searchList);
		modelAndView.setViewName("SearchPage");
		return modelAndView;
	}

	// After Clicking Search button from Search Page
	@PostMapping("/searchClick")
	public ModelAndView searchBtnClick(Model model, @ModelAttribute SearchResultlist sDao,@RequestParam("ssoid") String ssoid) throws SQLException,
			ClassNotFoundException, StreamReadException, DatabindException, JsonProcessingException, IOException {
		List<SearchResultlist> searchList = sUserService.getValuesBySetValue(sDao);
		model.addAttribute("list", searchList);
		model.addAttribute("STM", "2");
		model.addAttribute("ssoid", ssoid);
		session.setAttribute("ListData", searchList);
		modelAndView.setViewName("SearchPage");

		return modelAndView;
	}

	@PostMapping("/viewClick")
	public ModelAndView viewBtnClick(Model model,@RequestParam("ssoid") String ssoid) throws ClassNotFoundException, SQLException {
		// fetching Data from searchUerServiceImpl class
		List<SearchResultlist> searchList = sUserService.getAllValues();
		model.addAttribute("list", searchList);
		model.addAttribute("STM", "2");
		model.addAttribute("ssoid", ssoid);
		session.setAttribute("ListData", searchList);
		modelAndView.setViewName("SearchPage");
		return modelAndView;
	}

	@PostMapping("/doubleClickTableRow")
	public ModelAndView getModifyTableRowData(@RequestBody SearchResultlist dao, Model model) {

		@SuppressWarnings("unchecked")
		List<SearchResultlist> searchList = (List<SearchResultlist>) session.getAttribute("ListData");
		for (SearchResultlist ent : searchList) {
			if (ent.getRowIndex() == dao.getRowIndex()) {
				System.out.println(ent);
				session.setAttribute("modifyRowData", ent);
			}
		}
		model.addAttribute("STM", "2");
		modelAndView.setViewName("ModifySearchData");
		return modelAndView;
	}

	@GetMapping("/Modify")
	public ModelAndView ModifyViewPage(Model model,
			@RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
			@RequestParam(value = "wordFile", required = false) MultipartFile wordFile) {
		ModelAndView model1 = modelAndView;
		SearchResultlist modifyUser = (SearchResultlist) session.getAttribute("modifyRowData");
		System.out.println("Dao is : " + modifyUser);
		model.addAttribute("stmNo", modifyUser.getStmNo());
		model.addAttribute("stmVer", modifyUser.getStmVersion());
		model.addAttribute("linkdest", modifyUser.getLinkDestination());
		model.addAttribute("jpText", modifyUser.getTextShortJP());
		model.addAttribute("enText", modifyUser.getTextShortEN());
		model.addAttribute("pdf", modifyUser.getPdfFile());
		model.addAttribute("word", modifyUser.getWordFile());
		model.addAttribute("lastName", modifyUser.getFinalDrafterName());
		model.addAttribute("oldSTMNo", modifyUser.getOldSTMNumber());
		model.addAttribute("remarks", modifyUser.getRemarks1());
		model.addAttribute("note2", modifyUser.getNote2());
		model.addAttribute("note3", modifyUser.getNote3());

		model1.setViewName("ModifySearchData");
		return model1;
	}

	@GetMapping("/stmNo")
	public ModelAndView downloadFile(Model model) throws Exception {
		String pdfFileName = (String) session.getAttribute("pdfFileName");
		boolean isGeneratedPdfFile = sUserService.generatePDFFile(pdfFileName);
		if (isGeneratedPdfFile) {
		} else {
		}
		ModelAndView model1 = modelAndView;

		SearchResultlist modifyUser = (SearchResultlist) session.getAttribute("modifyRowData");
		System.out.println("Dao is : " + modifyUser);
		model.addAttribute("stmNo", modifyUser.getStmNo());
		model.addAttribute("stmVer", modifyUser.getStmVersion());
		model.addAttribute("linkdest", modifyUser.getLinkDestination());
		model.addAttribute("jpText", modifyUser.getTextShortJP());
		model.addAttribute("enText", modifyUser.getTextShortEN());
		model.addAttribute("pdf", modifyUser.getPdfFile());
		model.addAttribute("word", modifyUser.getWordFile());
		model.addAttribute("lastName", modifyUser.getFinalDrafterName());
		model.addAttribute("oldSTMNo", modifyUser.getOldSTMNumber());
		model.addAttribute("remarks", modifyUser.getRemarks1());
		model.addAttribute("note2", modifyUser.getNote2());
		model.addAttribute("note3", modifyUser.getNote3());

		model1.setViewName("ModifySearchData");
		return model1;
	}

	@PostMapping("/PDFBtnClick")
	public ModelAndView onClickPDFBTNClick(@RequestBody SearchResultlist dao, Model model) {

		session.setAttribute("pdfFileName", dao.getPdfFile());
		model.addAttribute("STM", "2");
		modelAndView.setViewName("ModifySearchData");
		return modelAndView;
	}

	@PostMapping("/DocBtntnClick")
	public ModelAndView onClickDocBtnClick(@RequestBody SearchResultlist dao, Model model) {
		session.setAttribute("DocFileName", dao.getWordFile());
		model.addAttribute("STM", "2");
		modelAndView.setViewName("ModifySearchData");
		return modelAndView;
	}

}
