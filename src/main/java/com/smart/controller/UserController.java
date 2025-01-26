package com.smart.controller;


import java.security.Principal;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.smart.dao.GeofenceService;
import com.smart.dao.UserHistoryRepository;
import com.smart.dao.UserHistoryService;
import com.smart.dao.UserRepository;
import com.smart.entity.Geofence;
import com.smart.entity.User;
import com.smart.entity.UserHistory;

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
	public String profile() {
		return "normal/profile";
	}

	
	@Autowired
	private UserHistoryRepository userHistoryRepository;

	// @PostMapping("/saveHistory")
	// 	public @ResponseBody String saveHistory(@ModelAttribute UserHistory userHistory,
    //                       @RequestParam("userId") Integer userId,
    //                       @RequestParam("geofenceId") Integer geofenceId,
    //                       @RequestParam("userName") String userName,
    //                       @RequestParam("geofenceName") String geofenceName,
    //                       @RequestParam("present") boolean present) {

	// 				userHistory.setUserId(userId);
	// 				userHistory.setGeofenceId(geofenceId);
	// 				userHistory.setUserName(userName);
	// 				userHistory.setGeofenceName(geofenceName);

	// 				// Set the date in userHistory
	// 				Date sqlDate = new Date(System.currentTimeMillis());
	// 				userHistory.setDate(sqlDate);

	// 				// Get the current time
	// 				Calendar calendar = Calendar.getInstance();
	// 				Time currentTime = new Time(calendar.getTimeInMillis());

	// 				// Set entry or exit time based on the 'present' value
	// 				if (present) {
	// 					userHistory.setEntryTime(currentTime);
	// 				} else {
	// 					userHistory.setExitTime(currentTime);
	// 				}

	// 				// Save the user history
	// 				this.userHistoryRepository.save(userHistory);

	// 				System.out.println("userHistory: " + userHistory);

	// 				// You can return a meaningful message or object
	// 				return "success";
	// 			}
	

@PostMapping("/saveHistory")
@ResponseBody
public String saveHistory(@RequestParam("userId") Integer userId,
                          @RequestParam("geofenceId") Integer geofenceId,
                          @RequestParam("userName") String userName,
                          @RequestParam("geofenceName") String geofenceName,
                          @RequestParam("present") boolean present) {

    // Fetch existing active user history if present
    Optional<UserHistory> userHistoryOptional = userHistoryRepository.findActiveEntry(userId);

    UserHistory userHistory;
    if (userHistoryOptional.isPresent()) {
        userHistory = userHistoryOptional.get();
    } else {
        userHistory = new UserHistory();
        userHistory.setUserId(userId);
        userHistory.setGeofenceId(geofenceId);
        userHistory.setUserName(userName);
        userHistory.setGeofenceName(geofenceName);
        userHistory.setDate(new Date(System.currentTimeMillis()));
    }

    // Get the current time
    Calendar calendar = Calendar.getInstance();
    Time currentTime = new Time(calendar.getTimeInMillis());

    // Set entry or exit time based on the 'present' value
    if (present) {
        userHistory.setEntryTime(currentTime);
    } else {
        userHistory.setExitTime(currentTime);
    }

    // Save the user history (update or new entry)
    userHistoryRepository.save(userHistory);

    System.out.println("userHistory: " + userHistory);

    return "success";
}


	 @Autowired
    private UserHistoryService userHistoryService;
	

    @GetMapping("/profile/{userId}")
    public String getUserHistory(@PathVariable("userId") Integer userId, Model model) {
        List<UserHistory> userHistories = userHistoryService.getAllEntriesForUserId(userId);
        model.addAttribute("userHistories", userHistories);
        return "normal/worktrack";
    }

	
}
