package com.stmproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashBoardController {
	@GetMapping("/admindash")
	public String showDashboard() {
		return "AdminDashBoard";
	}

	@GetMapping("/userDash")
	public String showUserDashboard() {
		return "UserDashBoard";
	}

	@GetMapping("/return")
	public String returnToLogin() {
		return "LoginScreen";
	}

	// Redirect to SearchPage

	@GetMapping("/modify")
	public String returnToAdminDashboardModify(Model model,@RequestParam("ssoid") String ssoid) {
		model.addAttribute("ssoid", ssoid);
		return "SearchPage";
	}

	// Redirect to SearchPage
	@GetMapping("/revise")
	public String returnToAdminDashboardRevise(Model model,@RequestParam("ssoid") String ssoid) {
		model.addAttribute("ssoid", ssoid);
		return "SearchPage";
	}

	// Redirect to SearchPage
	@GetMapping("/delete")
	public String returnToAdminDashboardDelete(Model model,@RequestParam("ssoid") String ssoid) {
		model.addAttribute("ssoid", ssoid);
		return "SearchPage";
	}

	@GetMapping("/view")
	public String returnToView(Model model,@RequestParam("ssoid") String ssoid) {
		model.addAttribute("ssoid", ssoid);
		return "UserView";
	}

}
