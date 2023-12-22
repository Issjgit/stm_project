package com.stmproject.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.stmproject.model.STM;
import com.stmproject.model.SearchResultlist;
import com.stmproject.service.S_UserService;

@Controller
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	public HttpSession session;

	@Autowired
	private S_UserService sUserService;

	@Value("${Empty_Record_Message}")
	private String failuereMsg;

	@Value("${Record_Message}")
	private String successMsg;

	public SearchController() {
	}

	@GetMapping("/search")
	public String searchPage(Model model, @RequestParam("ssoid") String ssoid) {
		model.addAttribute("STM", "1");
		model.addAttribute("ssoid", ssoid);
		session.setAttribute("username", ssoid);
		model.addAttribute("ssoid", session.getAttribute("username"));

		return "SearchPage";
	}

	// After Clicking Cancel button from Modify Page
	@SuppressWarnings("unchecked")
	@PostMapping("/goBackToSearchAfterResetClick")
	public String resetBtnClick(Model model) {
		System.out.println("Welocme to Reset  Method");
		List<STM> searchList = new ArrayList<>();
		searchList = (List<STM>) session.getAttribute("ListData");
		model.addAttribute("list", searchList);
		return "SearchPage";
	}

	// After Clicking Search button from Search Page
	@PostMapping("/searchClick")
	public String searchBtnClick(Model model, @ModelAttribute SearchResultlist sDao,
			@RequestParam("ssoid") String ssoid) throws Exception {

		List<SearchResultlist> searchList = sUserService.getValuesBySetValue(sDao);
		model.addAttribute("list", searchList);
		if (!searchList.isEmpty()) {
			model.addAttribute("STM", "2");
			model.addAttribute("records", searchList.size() + " " + successMsg);
			logger.debug(searchList.size() + " " + successMsg);
		} else {
			model.addAttribute("records", failuereMsg);
			logger.debug(failuereMsg);
		}
		model.addAttribute("ssoid", ssoid);
		session.setAttribute("ListData", searchList);
		return "SearchPage";
	}

	@PostMapping("/viewClick")
	public String viewBtnClick(Model model, @RequestParam("ssoid") String ssoid)
			throws ClassNotFoundException, SQLException {
		// fetching Data from searchUerServiceImpl class
		List<SearchResultlist> searchList = sUserService.getAllValues();
		model.addAttribute("list", searchList);
		if (!searchList.isEmpty()) {
			model.addAttribute("STM", "2");
			model.addAttribute("records", searchList.size() + " " + successMsg);
			logger.debug(searchList.size() + " " + successMsg);
		} else {
			model.addAttribute("records", failuereMsg);
			logger.debug(failuereMsg);
		}
		model.addAttribute("ssoid", ssoid);
		session.setAttribute("ListData", searchList);
		model.addAttribute("ssoid", session.getAttribute("username"));
		return "SearchPage";
	}

	@PostMapping("/doubleClickTableRow")
	public String getModifyTableRowData(@RequestBody SearchResultlist dao, Model model) {

		@SuppressWarnings("unchecked")
		List<SearchResultlist> searchList = (List<SearchResultlist>) session.getAttribute("ListData");
		for (SearchResultlist ent : searchList) {
			if (ent.getRowIndex() == dao.getRowIndex()) {
				System.out.println(ent);
				session.setAttribute("modifyRowData", ent);
				break;
			}
		}
		model.addAttribute("ssoid", session.getAttribute("username"));
		model.addAttribute("STM", "2");
		return "ModifySearchData";
	}

	@GetMapping("/Modify")
	public String ModifyViewPage(HttpSession session, Model model) {
		SearchResultlist modifyUser = (SearchResultlist) session.getAttribute("modifyRowData");
		System.out.println("Dao is : " + modifyUser);
		model.addAttribute("stmNo", modifyUser.getStmNo());
		model.addAttribute("stmVer", modifyUser.getStmVersion());
		model.addAttribute("linkdest", modifyUser.getLinkDestination());
		model.addAttribute("jpText", modifyUser.getTextShortJP());
		model.addAttribute("enText", modifyUser.getTextShortEN());
		model.addAttribute("pdf", modifyUser.getPdfFile());
		model.addAttribute("word", modifyUser.getWordFile());
		model.addAttribute("draftingdate", modifyUser.getDraftingDate());
		model.addAttribute("lastName", modifyUser.getFinalDrafterName());
		model.addAttribute("oldSTMNo", modifyUser.getOldSTMNumber());
		model.addAttribute("remarks", modifyUser.getRemarks1());
		model.addAttribute("note2", modifyUser.getNote2());
		model.addAttribute("note3", modifyUser.getNote3());

		model.addAttribute("ssoid", session.getAttribute("username"));
		return "ModifySearchData";
	}

	@CrossOrigin
	@GetMapping("/downloadattachmentpdf")
	public ResponseEntity<byte[]> downloadFile(@RequestParam("file") String fileName, Model model) throws IOException {

		String filePath = "C:/STM_File/STM_File/" + fileName;
		Path path = Paths.get(filePath);
		byte[] fileContent = Files.readAllBytes(path);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<>(fileContent, headers, org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/generateWord")
	public ResponseEntity<byte[]> downloadWordFile(@RequestParam("file") String fileName,
			@RequestParam("defaultvalue") String word, Model model) throws IOException {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				XWPFDocument document = new XWPFDocument()) {
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setText("Hello, this is a generated Word document!");

			document.write(outputStream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			String basePath = "C:/STM_File/STM_File/";
			String filePath = basePath + fileName;

			String message = "Document Downloaded successfully!";
			headers.setContentDispositionFormData("attachment", filePath);

			model.addAttribute("message", message);
			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		} catch (IOException e) {
			model.addAttribute("error", "Please try again.");

			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
