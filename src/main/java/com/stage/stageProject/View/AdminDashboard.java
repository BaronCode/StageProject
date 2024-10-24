package com.stage.stageProject.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import com.stage.stageProject.Notifications.NotificationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import com.stage.stageProject.ActivitiesMgmt.ActivityComparator;
import com.stage.stageProject.ActivitiesMgmt.ActivityRepo;
import com.stage.stageProject.ActivitiesMgmt.PRIORITY;
import com.stage.stageProject.ActivitiesMgmt.STATUS;
import com.stage.stageProject.Auth.JwtService;
import com.stage.stageProject.Auth.UserInfoService;
import com.stage.stageProject.IntersectionMgmt.Intersection;
import com.stage.stageProject.IntersectionMgmt.IntersectionRepo;
import com.stage.stageProject.Notifications.Notification;
import com.stage.stageProject.Notifications.NotificationRepo;
import com.stage.stageProject.Notifications.NotificationService;
import com.stage.stageProject.RolesMgmt.ROLES;
import com.stage.stageProject.RolesMgmt.UserRoles;
import com.stage.stageProject.RolesMgmt.UserRolesPrimitive;
import com.stage.stageProject.RolesMgmt.UserRolesRepo;
import com.stage.stageProject.UserMgmt.User;
import com.stage.stageProject.UserMgmt.UserRepo;
import com.stage.stageProject.UserMgmt.UserService;
import com.stage.stageProject.UserMgmt.UserToken;

import jakarta.ws.rs.FormParam;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/data/admin")
@Controller
@Slf4j
public class AdminDashboard {
	/*
	@Autowired
	private UserController userController;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private NotificationRepo notiRepo;
	@Autowired
	private NotificationServiceImpl notiService;
	@Autowired
	private UserRepo uRepo;
	@Autowired
	private ActivityRepo aRepo;
	@Autowired
	private IntersectionRepo repo;
	@Autowired
	private UserRolesRepo urRepo;
	
	private final Logger logger = LoggerFactory.getLogger(AdminDashboard.class);
	private boolean isOwner;
	
	
	@RequestMapping("/dashboard")
	public String adminDashboard(Model model) {
		String token = userController.token;
		String name = jwtService.extractUsername(token);
		isOwner = urRepo.existsById(new UserRolesPrimitive(name, ROLES.OWNER));
		if (UserToken.checkToken(token) && urRepo.existsById(new UserRolesPrimitive(name, ROLES.ADMIN))) {
			if (isOwner) {
				logger.info("Displaying dashboard of owner " + name);
			} else logger.info("Displaying dashboard of admin " + name);
			
			List<User> userList = uRepo.findAll().isEmpty() ? new ArrayList<>() : uRepo.findAll();
			List<Intersection> intersectionList = repo.findAll().isEmpty() ? new ArrayList<>() : repo.findAll();
			List<Activity> activityList = aRepo.findAll().isEmpty() ? new ArrayList<>() : aRepo.findAll();
			List<Notification> notiList = notiRepo.findAll().isEmpty() ? new ArrayList<>() : notiRepo.findAll();
			List<Notification> read = notiRepo.selectByStatus(true).isEmpty() ? new ArrayList<>() : notiRepo.selectByStatus(true);
			List<Notification> unread = notiRepo.selectByStatus(false).isEmpty() ? new ArrayList<>() : notiRepo.selectByStatus(false);

			activityList.sort(Comparator.comparing(Activity::getPriority).thenComparing(Comparator.nullsLast(new ActivityComparator())));
			notiList.sort(Comparator.comparing(Notification::getTimestamp).reversed());
			read.sort(Comparator.comparing(Notification::getTimestamp).reversed());
			unread.sort(Comparator.comparing(Notification::getTimestamp).reversed());
			
			intersectionList.sort(Comparator.comparing(Intersection::getActivityId));
    		LinkedHashMap<Integer, String> aMap = new LinkedHashMap<>();
    		aRepo.findAll()
    			.stream()
    			.sorted(Comparator.comparing(Activity::getId)
    					.reversed())
    			.forEach(o->aMap.put(o.getId(), o.getName()));
   
			model.addAttribute("name", name);
			model.addAttribute("role", isOwner ? ROLES.OWNER : ROLES.ADMIN);
			model.addAttribute("adminrole", ROLES.ADMIN);
			model.addAttribute("ulist", userList);
    		model.addAttribute("priorities", Arrays.asList(PRIORITY.values()));
			model.addAttribute("ilist", intersectionList);
			model.addAttribute("alist", activityList);
			model.addAttribute("amap", aMap);
			model.addAttribute("notifications", notiList);
			model.addAttribute("read", read);
			model.addAttribute("unread", unread);
			
			return "/data/admin/dashboard";
		} else if (!UserToken.checkToken(token)) {
			return "redirect:/auth/fail?reason=credentials_expired";
		}
		else return "redirect:/auth/fail?reason=user_not_admin";
	}
	
	@PostMapping("/dashboard")
	public String edit(
			@RequestParam(value = "edit") String action,
			@FormParam("mail") Optional<String> mail,
			@FormParam("username") Optional<String> username,
			@FormParam("psw") Optional<String> psw,
			@FormParam("activityname") Optional<String> activityname,
			@FormParam("activityid") Optional<Integer> activityid,
			@FormParam("priority") Optional<PRIORITY> priority,
			@FormParam("notificationid") Optional<Integer> notificationid,
			RedirectAttributes model
			) {
    	boolean success = false;
		String uname = jwtService.extractUsername(userController.token);
		User current = uRepo.getReferenceById(uname);
		switch (action) {	
			case "delete_user" -> {
				String message = "Unable to delete user!\nDetails: user " + username + " is ADMIN";
				String nameget = username.get();
				
				if (!urRepo.existsById(new UserRolesPrimitive(nameget, ROLES.ADMIN)) || isOwner) {
					if (!nameget.equals(uname)) {
						User u = uRepo.getReferenceById(nameget);
						message = " successfully deleted a user!\nDetails: " + uRepo.getReferenceById(nameget).consoleToString();
						notiService.saveNotification(new Notification(notiService.findMaxId()+1, "User deleted", u.notificationToString(), LocalDateTime.now(), false));
						userService.removeUserAndActivity(nameget);
						success = true;
					} else {
						message = "Unable to delete user!\nDetails: " + nameget + " is current user";
						logger.error("Tried to delete user " + uname + " [current user]!");
					}
				} else logger.error("Trying to delete user " + nameget + " which is ADMIN"); 

				model.addFlashAttribute("location", "user");
				model.addFlashAttribute("response", message);
			}
			case "create_user" -> {
				String nameget = username.get();
				String mailget = mail.get();
				String pswget = psw.get();
				String message = "Unable to create user!\nDetails: user " + nameget + " already exists"; 
				
				if (!uRepo.existsById(nameget)) {
					User u = new User(nameget, mailget, pswget);
					userInfoService.addUser(u);
			    	urRepo.save(new UserRoles(nameget, ROLES.USER));
			    	notiService.saveNotification(new Notification(notiService.findMaxId()+1, "User created", u.notificationToString(), LocalDateTime.now(), false));
					message = "created a new user!\nDetails: " + u.consoleToString();
					success = true;
					logger.info(u.consoleToString() + " created!");
				}
				model.addFlashAttribute("location", "user");
				model.addFlashAttribute("response", message);
			}
			case "create_activity" -> {
				String nameget = activityname.get();
				int id = aRepo.findMaxId()+1;
				PRIORITY priorityget = priority.get();
				String message = "Unable to create activity!\nDetails: activity with ID " + id + " already exists";

				if (!aRepo.existsById(id)) {
					Activity a = new Activity(id, nameget, priorityget, current);
					current.addActivity(a);
					userService.persist(current.getName());
					notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity created", a.notificationToString(), LocalDateTime.now(), false));
					logger.info("Activity " + a + " created!");
					message = "created a new activity!\nDetails: " + a;
					success = true;
				} else logger.error("Activity with id " + id + " already exists!");

				model.addFlashAttribute("location", "activity");
				model.addFlashAttribute("response", message);
			}
			case "remove_activity" -> {
				String message = "";
				int idget = activityid.get();
				if (aRepo.existsById(idget)) {
					Activity a = aRepo.getReferenceById(idget);
					userService.removeActivityFromUser(a.getUser().getName(), a);
					logger.info("Deleted activity " + idget);
					notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity deleted", a.notificationToString(), LocalDateTime.now(), false));
					message = "deleted the activity with ID " + idget;
					success = true;
				} else if (!aRepo.existsById(idget));
				success = true;
				model.addFlashAttribute("location", "activity");
				model.addFlashAttribute("response", message);
				logger.info(message);
			}
			case "assign_activity" -> {
				int idget = activityid.get();
				String userget = username.get();
				String message = "Unable to find ";
				if (aRepo.existsById(idget) && uRepo.existsById(userget)) {
					User u = uRepo.getReferenceById(userget);
					Activity a = aRepo.getReferenceById(idget);
					repo.save(new Intersection(repo.findMaxRow()+1, u, a));
					message = "assigned activity to user\nDetails:\n\t" + u + "\n\t" + a;
					notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity assigned", a.notificationToString() + "\n" + u.notificationToString(), LocalDateTime.now(), false));
					logger.info("Assigned " + a.consoleToString() + " to " + u.consoleToString());
					success = true;
				} else {
					message = message.concat(aRepo.existsById(idget) ? " user" : " activity");
					logger.error(message);
				}
				model.addFlashAttribute("location", "activity");
				model.addFlashAttribute("response", message);
			}
			case "read" -> {
				int idget = notificationid.get();
				logger.info("Marked as read");
				notiRepo.updateNotification(idget, true);
			}
		}
		model.addFlashAttribute("success", success);
		return "redirect:/data/admin/dashboard";
	}
	*/

}