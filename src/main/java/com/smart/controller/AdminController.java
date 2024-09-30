package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.GeofenceRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Geofence;
import com.smart.entity.User;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
	private UserRepository repository;

	 @Autowired
    private GeofenceRepository geofenceRepository;

    @ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String email = principal.getName();
		System.out.println("Email : " + email);
		User user = this.repository.getUserByUserName(email);
		System.out.println("User : " + user);
		model.addAttribute("user", user);
	}


    	@RequestMapping("/index")
	public String admindashboard(Model model, Principal principal) {
		model.addAttribute("title", "Admin Dashborad");
		return "admin/user_dashboard";
	}
	@RequestMapping("/settings")
	public String adminProfile(){
		return "admin/profile.html";
	}
	@RequestMapping("/createGeofence")
    public String showGeofenceForm(Model model) {
        model.addAttribute("geofence", new Geofence());
        return "admin/geofence_form";
    }

    @PostMapping("/saveGeofence")
    public String saveGeofence(@ModelAttribute Geofence geofence, Principal principal,
                               @RequestParam("latitude") Double latitude,
                               @RequestParam("longitude") Double longitude) {

        // Get the current admin from the principal
        String email = principal.getName();
        User admin = repository.getUserByUserName(email);
		System.out.println("adding admin");
        // Set the admin and geofence data
        geofence.setAdmin(admin);
        geofence.setLatitude(latitude);
        geofence.setLongitude(longitude);

        // Save the geofence to the database
        geofenceRepository.save(geofence);

        return "redirect:/admin/index"; // Redirect to a geofences list or dashboard
    }


}
