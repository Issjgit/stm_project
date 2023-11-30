package com.stmproject.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.JarException;

import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.stmproject.dao.SearchPageDao;
import com.stmproject.model.STM;
import com.stmproject.repository.S_UserRepository;
import com.stmproject.service.S_UserService;
import com.stmproject.serviceImpl.S_UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@RestController
public class SearchController {

	@Autowired
	public HttpSession session;

	ModelAndView modelAndView;

	@Autowired
	private S_UserService sUserService;

	public SearchController() {
		modelAndView = new ModelAndView();
	}

	@GetMapping("/search")
	public ModelAndView searchPage(Model model) {
		model.addAttribute("STM", "1");
		modelAndView.setViewName("SearchPage");
		return modelAndView;
	}

	// after clicking Modify button from Search Page
	@PostMapping("/modifyClick")
	public ModelAndView modifyClickBtn(@RequestParam("modifyValue") String value, Model model,
			@ModelAttribute SearchPageDao sDao, HttpSession session) throws ClassNotFoundException, SQLException {

		System.out.println("Value is : " + value);
		modelAndView.setViewName("ModifySearchData");
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
	public ModelAndView searchBtnClick(Model model, @ModelAttribute SearchPageDao sDao) throws SQLException,
			ClassNotFoundException, StreamReadException, DatabindException, JsonProcessingException, IOException {

		List<SearchPageDao> searchList = sUserService.getValuesBySetValue(sDao);
		model.addAttribute("list", searchList);
		model.addAttribute("STM", "2");
		session.setAttribute("ListData", searchList);
		modelAndView.setViewName("SearchPage");

		return modelAndView;
	}

	@PostMapping("/viewClick")
	public ModelAndView viewBtnClick(Model model) throws ClassNotFoundException, SQLException {
		// fetching Data from searchUerServiceImpl class
		List<SearchPageDao> searchList = sUserService.getAllValues();
		model.addAttribute("list", searchList);
		model.addAttribute("STM", "2");
		session.setAttribute("ListData", searchList);
		modelAndView.setViewName("SearchPage");
		return modelAndView;
	}

	@PostMapping("/doubleClickTableRow")
	public ModelAndView getModifyTableRowData(@RequestBody SearchPageDao dao, Model model) {

		@SuppressWarnings("unchecked")
		List<SearchPageDao> searchList = (List<SearchPageDao>) session.getAttribute("ListData");
		for (SearchPageDao ent : searchList) {
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
	public ModelAndView ModifyPage(Model model) {
		ModelAndView model1 = modelAndView;
		SearchPageDao modifyUser = (SearchPageDao) session.getAttribute("modifyRowData");
		System.out.println("Dao is : " + modifyUser);
		model.addAttribute("stmNo", modifyUser.getsTM_No());
		model.addAttribute("stmVer", modifyUser.getStm_version());
		model.addAttribute("linkdest", modifyUser.getLink_destination());
		model.addAttribute("jpText", modifyUser.getText_short_jpn());
		model.addAttribute("enText", modifyUser.getText_short_eng());
		model.addAttribute("pdf", modifyUser.getPdf_file());
		model.addAttribute("word", modifyUser.getWord_file());
		model.addAttribute("lastName", modifyUser.getFinal_drafter_Name());
		model.addAttribute("oldSTMNo", modifyUser.getOld_stm_number());
		model.addAttribute("remarks", modifyUser.getRemarks1());
		model.addAttribute("note2", modifyUser.getNote2());
		model.addAttribute("note3", modifyUser.getNote3());

		model1.setViewName("ModifySearchData");
		return model1;
	}

	@GetMapping("/PdfFile")
	public ModelAndView PdfFileDownload(HttpSession session, ModelAndView model) throws JarException, IOException {
		return model;
	}

}
