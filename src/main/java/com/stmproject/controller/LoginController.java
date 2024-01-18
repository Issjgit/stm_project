package com.stmproject.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import com.stmproject.model.M_User;
import com.stmproject.service.M_UserService;
import java.net.URLEncoder;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private M_UserService userService;

	@GetMapping("/login")
	public String showLoginForm() {
		return "LoginScreen";
	}

	// ADD[S]Samata 2024/01/18
	@PostMapping("/login")
	public String login(@RequestParam("ssoid") String ssoid, @RequestParam("password") String password, Model model)
			throws IOException {
		logger.info("Received ssoid in login: {}", ssoid);

		M_User user = userService.findBySsoid(ssoid);
		if (user != null && password.equals(user.getPassword())) {
			try {
				if (user.getUser_Type().equals('A')) {
					logger.info("Admin login successful: {}", ssoid);

					String encodedSsoid = URLEncoder.encode(ssoid, StandardCharsets.UTF_8.toString());

					String redirectUri = "redirect:/AdminDashBoard?ssoid=" + encodedSsoid;
					logger.info("Redirecting to: {}", redirectUri);

					return redirectUri;
				} else if (user.getUser_Type().equals('N')) {
					logger.info("User login successful: {}", ssoid);

					String encodedSsoid = URLEncoder.encode(ssoid, StandardCharsets.UTF_8.toString());
					return "redirect:" + UriComponentsBuilder.fromPath("/UserDashBoard")
							.queryParam("ssoid", encodedSsoid).build().toUriString();
				}
			} catch (UnsupportedEncodingException e) {
				// Handle encoding exception if necessary
				logger.error("Error encoding ssoid: {}", e.getMessage());
				return "errorPage";
			}
		}

		logger.warn("Login failed for ssoid: {}", ssoid);
		model.addAttribute("invalidCredentials", true);
		return "LoginScreen";
	}
	// ADD[E]Samata 2024/01/18

	@GetMapping("/AdminDashBoard")
	public String adminDashboard(@RequestParam("ssoid") String ssoid, Model model) throws UnsupportedEncodingException {
		model.addAttribute("ssoid", URLDecoder.decode(ssoid, StandardCharsets.UTF_8.toString()));
		return "AdminDashBoard";
	}

	@GetMapping("/UserDashBoard")
	public String userDashboard(@RequestParam("ssoid") String ssoid, Model model) throws UnsupportedEncodingException {
		model.addAttribute("ssoid", URLDecoder.decode(ssoid, StandardCharsets.UTF_8.toString()));
		return "UserDashBoard";
	}

}
