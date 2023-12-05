package com.stmproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stmproject.model.M_User;
import com.stmproject.service.M_UserService;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private M_UserService userService;

	@GetMapping("/login")
	public String showLoginForm() {
		return "LoginScreen";
	}

	@PostMapping("/login")
    public String login(@RequestParam("ssoid") String ssoid, @RequestParam("password") String password, Model model) {
        M_User user = userService.findBySsoid(ssoid);
        if (user != null && password.equals(user.getPassword())) {
            if (user.getUser_Type().equals('A')) { // "A" represents admin
                logger.info("Admin login successful: {}", ssoid);
                return "redirect:/AdminDashboard?ssoid=" + ssoid;
            } else if (user.getUser_Type().equals('N')) { // "N" represents normal user
                logger.info("User login successful: {}", ssoid);
                return "redirect:/UserDashBoard?ssoid=" + ssoid;
            }
        }

        // If login fails, you can return to the login page or display an error message
        logger.warn("Login failed for ssoid: {}", ssoid);
        model.addAttribute("invalidCredentials", true);
        return "LoginScreen";
    }

	@GetMapping("/AdminDashboard")
	public String adminDashboard(@RequestParam("ssoid") String ssoid, Model model) {
		model.addAttribute("ssoid", ssoid);
		return "AdminDashboard";
	}

	@GetMapping("/UserDashBoard")
	public String userDashboard(@RequestParam("ssoid") String ssoid, Model model) {
		model.addAttribute("ssoid", ssoid);
		return "UserDashBoard";
	}

	

}
