package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.smart.dao.GeofenceRepository;
import com.smart.dao.GeofenceService;
import com.smart.dao.UserRepository;
import com.smart.dao.UserService;
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
       @Autowired
    private GeofenceService geofenceService;
    @Autowired
    private UserService userService;

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
    @RequestMapping("/geolist")
	public String geolist(){
		return "admin/geofence_list";
	}


	@RequestMapping("/createGeofence")
    public String showGeofenceForm(Model model) {
        model.addAttribute("geofence", new Geofence());
        return "admin/geofence_form";
    }

    @RequestMapping("/geofenceView/{id}")
     public String viewGeofence(@PathVariable("id") Long id, Model model) { 
        Geofence geofence = geofenceService.getGeofenceById(id);
        model.addAttribute("geofence", geofence); 
        List<User> users=userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/geofenceView"; 
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

    @GetMapping("/show_geofence/{page}")
    public String getAllGeofence(@PathVariable("page") Integer page, Model model, Principal principal) {
        model.addAttribute("title", "Show All Geofences");
    
        
        // Create a pageable object for pagination, with 6 items per page
        Pageable pageable = PageRequest.of(page, 6);
    
        // Get the paginated list of all geofences (without filtering by admin)
        Page<Geofence> list = geofenceRepository.findAll(pageable);
    
        // Add attributes to the model to be displayed in the Thymeleaf template
        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", list.getTotalPages());
    
        return "admin/geofence_list";
    }
    
    // Delete Geofence
@RequestMapping(value = "/delete-geofence/{id}", method = RequestMethod.GET)
    public String deleteGeofence(@PathVariable("id") Long geofenceId, RedirectAttributes redirectAttributes) {
        Optional<Geofence> geofence = geofenceRepository.findById(geofenceId);
        if (geofence.isPresent()) {
            geofenceRepository.delete(geofence.get());
            redirectAttributes.addFlashAttribute("message", "Geofence deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Geofence not found.");
        }
        return "redirect:/admin/show_geofence/0";
    }
    @GetMapping("/update-geofence/{id}")
    public String showUpdateGeofenceForm(@PathVariable("id") Long id, Model model) {
        Geofence geofence = geofenceRepository.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("Invalid geofence Id:" + id));
        model.addAttribute("geofence", geofence);
        return "admin/update-geofence";  // This refers to the Thymeleaf template for the form
    }

    // Handle form submission to update the geofence
    @PostMapping("/update-geofence/{id}")
    public String updateGeofence(@PathVariable("id") Long id, @ModelAttribute Geofence geofence, Model model) {
        geofenceService.updateGeofence(id, geofence);  // Service method to update the geofence
        return "redirect:/admin/show_geofence/0";  // Redirect to the list of geofences after updating
    }
    

}
