package com.smart.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpSession;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserRepository;
import com.smart.entity.User;
import com.smart.helper.MessageHelper;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository repository;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Signup - Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}
	

    @GetMapping("/redirect")
    public String redirectBasedOnRole(Model model, Principal principal) {
        String email = principal.getName();
        User user = repository.getUserByUserName(email);
		String a=user.getRole().toString();
		System.out.println(a);
        if (user.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/index";
        } else if (user.getRole().equals("ROLE_USER")) {
            return "redirect:/user/index";
        } else {
            return "redirect:/redirect";
        }
    }

	// Handler for Registering user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user,

			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,@RequestParam("user.photoUser") MultipartFile file,
			HttpSession session) {
		try {
			if (!agreement) {
				System.out.println("You hava not agreed the Terms and Conditions");
				throw new Exception("You hava not agreed the Terms and Conditions");
			}
			if(user.getPost().toString().equals("admin")){
				user.setRole("ROLE_ADMIN");
			}
			else{
			user.setRole("ROLE_USER");
			}
			user.setEnable(true);
			if (file.isEmpty()) {
					System.out.println("File is empty");
					user.setPhotoURL("default.png"); // Set a default image if no file is uploaded
				} else {
					// Set the file name to the linkImg property
					user.setPhotoURL(file.getOriginalFilename());
					
					// Get the path to save the file
					File saveFile = new ClassPathResource("static/uploads/userPhoto").getFile();
					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
					
					// Save the file to the specified path
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("Image is uploaded");
				}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println("Agreement : " + agreement);
			System.out.println("USER : " + user);
			User result = this.repository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new MessageHelper("Successfully Registerd !!", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new MessageHelper("Something went wrong" + e.getMessage(), "alert-danger"));
			System.out.println(user.getPost().toString());
			return "signup";
		}

	}
	// Handler for Signin User
	
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		model.addAttribute("title", "Log in - Smart Contact Manager");
		return "login";
	}
}
