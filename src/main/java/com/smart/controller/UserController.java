package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.smart.dao.GeofenceService;
import com.smart.dao.UserRepository;
import com.smart.entity.Geofence;
import com.smart.entity.User;
import com.smart.helper.MessageHelper;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository repository;
	 @Autowired
    private GeofenceService geofenceService;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String email = principal.getName();
		System.out.println("Email : " + email);
		User user = this.repository.getUserByUserName(email);
		System.out.println("User + photoURL : " + user + user.getPhotoURL().toString());
		model.addAttribute("user", user);
	}

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashborad");
		// Fetch all geofences and add to the model
        List<Geofence> geofences = geofenceService.getAllGeofences();
        model.addAttribute("geofences", geofences); 
		
        model.addAttribute("geofenceCount", geofences.size()); 
		return "normal/user_dashboard";
	}

	@RequestMapping("/settings")
	public String profile(){
		return "normal/profile";
	}

	
	
}
