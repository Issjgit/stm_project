package com.stmproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {
	@GetMapping("/")
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
	public String returnToAdminDashboardModify() {
		return "SearchPage";
	}

	// Redirect to SearchPage
	@GetMapping("/revise")
	public String returnToAdminDashboardRevise() {
		return "SearchPage";
	}

	// Redirect to SearchPage
	@GetMapping("/delete")
	public String returnToAdminDashboardDelete() {
		return "SearchPage";
	}

	@GetMapping("/view")
	public String returnToView() {
		return "UserView";
	}

}
