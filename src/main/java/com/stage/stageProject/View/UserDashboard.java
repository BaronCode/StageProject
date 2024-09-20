package com.stage.stageProject.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import com.stage.stageProject.ActivitiesMgmt.ActivityRepo;
import com.stage.stageProject.ActivitiesMgmt.PRIORITY;
import com.stage.stageProject.ActivitiesMgmt.STATUS;
import com.stage.stageProject.Auth.JwtService;
import com.stage.stageProject.IntersectionMgmt.Intersection;
import com.stage.stageProject.IntersectionMgmt.IntersectionRepo;
import com.stage.stageProject.Messages.Message;
import com.stage.stageProject.Messages.MessageRepo;
import com.stage.stageProject.Messages.MessageService;
import com.stage.stageProject.Notifications.Notification;
import com.stage.stageProject.Notifications.NotificationService;
import com.stage.stageProject.RolesMgmt.ROLES;
import com.stage.stageProject.RolesMgmt.UserRolesPrimitive;
import com.stage.stageProject.RolesMgmt.UserRolesRepo;
import com.stage.stageProject.UserMgmt.User;
import com.stage.stageProject.UserMgmt.UserRepo;
import com.stage.stageProject.UserMgmt.UserService;
import com.stage.stageProject.UserMgmt.UserToken;

import jakarta.ws.rs.FormParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/data")
@Controller
public class UserDashboard {
	
	@Autowired    
	private JwtService jwtService; 
	private Logger logger = LoggerFactory.getLogger(UserDashboard.class);
	@Autowired	
	private UserController userController;
	@Autowired
	private NotificationService notiService;
	@Autowired
	private UserRepo userRepo;
	@Autowired	
	private IntersectionRepo interRepo;
	@Autowired	
	private MessageRepo msgRepo;
	@Autowired	
	private MessageService msgService;
	@Autowired	
	private UserService userService;
	@Autowired	
	private ActivityRepo aRepo;
	@Autowired
	private UserRolesRepo urRepo;
	
	@RequestMapping("/")
	public String parseRole(Model model) {
		String name = jwtService.extractUsername(userController.token);
		if (urRepo.existsById(new UserRolesPrimitive(name, ROLES.ADMIN))) {
			return "redirect:/data/admin/dashboard";
		} else return "redirect:/data/dashboard";
	}
	
    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
    	String token = userController.token;
    	if (UserToken.checkToken(token)) {
    		String name = jwtService.extractUsername(token);
    		logger.info("Displaying dashboard of " + name);

    		LinkedHashMap<Integer, String> aMap = new LinkedHashMap<>();
    		aRepo.findAll()
    			.stream()
    			.sorted(Comparator.comparing(Activity::getId)
    					.reversed())
    			.forEach(o->aMap.put(o.getId(), o.getName()));
    		
    		LinkedHashMap<Integer, String> createdMap = new LinkedHashMap<>();
    		aRepo.findAll()
    			.stream()
    			.sorted(Comparator.comparing(Activity::getId)
    					.reversed())
    			.filter(o->o
    					.getUser()
    					.getName()
    					.equals(name))
    			.forEach(o->createdMap.put(o.getId(), o.getName()));
    		
    		List<Intersection> ilist = interRepo.findAll();
    		
    		ArrayList<Activity> delegate = new ArrayList<>();
    		ArrayList<Activity> activities = new ArrayList<>();
    		for (Intersection i : ilist) {
    			if (i.getUser().getName().equals(name)) {
    				if (i.getActivity().getDelegate().getName().equals(name)) {
    					delegate.add(i.getActivity());
    				} else activities.add(i.getActivity());
    			}
    		}
    		delegate.sort(Comparator.comparing(Activity::getPriority).thenComparing(Activity::getStatus));
    		activities.sort(Comparator.comparing(Activity::getPriority).thenComparing(Activity::getStatus));
    		
    		ArrayList<Activity> created = new ArrayList<>(aRepo.findAll().stream().filter(o->o.getUser().getName().equals(name)).toList());

    		model.addAttribute("admin", urRepo.existsById(new UserRolesPrimitive(name, ROLES.ADMIN)));
    		model.addAttribute("name", name);
    		model.addAttribute("role", ROLES.USER);
    		model.addAttribute("activities", activities);
    		model.addAttribute("delegate", delegate);
    		model.addAttribute("created", created);
    		model.addAttribute("priorities", Arrays.asList(PRIORITY.values()));
    		model.addAttribute("mappedActivities", aMap);
    		model.addAttribute("createdActivities", createdMap);

    		return "/data/dashboard";
    	} else return "redirect:/auth/fail?reason=credentials_expired";
    	
    }
	
    @PostMapping("/dashboard")
	public String editDashboard(
			@RequestParam(value = "edit", defaultValue="add") String action,
			@FormParam("name") Optional<String> name,
			@FormParam("id") Optional<Integer> id,
			@FormParam("priority") Optional<PRIORITY> priority,
			RedirectAttributes model
			) {
    	String uname = jwtService.extractUsername(userController.token);
		User current = userRepo.getReferenceById(uname);
		Pair<String, Boolean> pair = Pair.createPair(null, null);
		
		switch (action) {
			case "add" -> pair = add_activity(uname, name.get(), aRepo.findMaxId()+1, priority.get());
			case "remove" -> pair = remove_activity(current, uname, id.get());
			case "assign" -> pair = assign_activity(current, uname, id.get());
			case "done" -> pair = done_activity(current, id.get());
			case "start" -> pair = start_activity(current, id.get());
			case "accept" -> pair = accept_activity(current, id.get());
			case "decline" -> pair = decline_activity(current, id.get());
			default -> pair = Pair.createPair("Invalid action", false);
		}
		
		model.addFlashAttribute("response", pair.first);
		model.addFlashAttribute("success", pair.second);
		return "redirect:/data/dashboard";
	}
	private Pair<String, Boolean> add_activity(String uname, String name, int id, PRIORITY priority) {
    	boolean success = false;
		String message = "Unable to create activity!\nDetails: activity with ID " + id + " already exists";
		
		if (!aRepo.existsById(id)) {
			Activity a = new Activity(aRepo.findMaxId()+1, name, priority, userRepo.getReferenceById(uname));
			aRepo.save(a);
			notiService.saveNotification(new Notification(notiService.findMaxId()+1, LocalDateTime.now(), false, "Activity creation", a.notificationToString()));
			logger.info("Activity " + a + " created!");
			message = "created a new activity!\nDetails: " + a;
			success = true;
		} else logger.error("Activity with id " + id + " already exists!");
		
		return Pair.createPair(message, success);
    }
	private Pair<String, Boolean> remove_activity(User current, String uname, int id) {
		boolean success = false;
    	String message = "Unable to delete activity!\nDetails: trying to delete activity not created by " + uname;
		Activity a = aRepo.getReferenceById(id);
		if (current.getActivities().contains(a)) {
			userService.removeActivityFromUser(uname, a);
			userService.refreshUser(current);
			notiService.saveNotification(new Notification(notiService.findMaxId()+1, LocalDateTime.now(), false, "Activity deletion", a.notificationToString()));
			logger.info("Deleted activity " + id);
			message = "deleted the activity with ID " + id + "\nDetails: " + a;
			success = true;
		} else if (!aRepo.existsById(id)) {
			logger.error("No activity with ID=" + id + "!");
			message = "Unable to delete activity!\nDetails: no activity with ID" + id;
		} else logger.error("Trying to delete activity not created by " + uname + "!");
		
		return Pair.createPair(message, success);
	}
	private Pair<String, Boolean> assign_activity(User current, String uname, int id) {
		boolean success = false;
		String message = "Unable to assign activity!\nDetails: no activity with ID " + id;
		
		if (aRepo.existsById(id)) {
			if (aRepo.getReferenceById(id).getDelegate()==null) {
				aRepo.updateDelegate(id, current);
				aRepo.updateStatus(id, STATUS.ACCEPTED);
			}
			
			userService.refreshUser(current);
			interRepo.save(new Intersection(interRepo.findMaxRow()+1, current, aRepo.getReferenceById(id)));
			notiService.saveNotification(new Notification(notiService.findMaxId()+1, LocalDateTime.now(), false, "Activity assign", "Assigned " + aRepo.getReferenceById(id).notificationToString() + " to " + current.getName()));
			logger.info("Assigned activity " + aRepo.getReferenceById(id));
			message = "assigned to yourself a new activity!\nDetails: " + aRepo.getReferenceById(id);
			success = true;
		} else logger.error(message);
		
		return Pair.createPair(message, success);
	}
	private Pair<String, Boolean> done_activity(User current, int id) {
		Activity a = aRepo.getReferenceById(id);
		interRepo.deleteByActivity(a);
		aRepo.deleteById(id);
		
		notiService.saveNotification(new Notification(notiService.findMaxId()+1, LocalDateTime.now(), false, "Activity done", "User " + current.getName() + " completed " + aRepo.getReferenceById(id).notificationToString()));
		String message = "completed an activity!\nDetails: " + a;
		logger.info("Completed " + a);
		return Pair.createPair(message, true);
	}
	private Pair<String, Boolean> start_activity(User current, int id) {
		Activity a = aRepo.getReferenceById(id);
		String message = "started an activity!\nDetails: " + a;
		
		aRepo.updateStatus(id, STATUS.IN_PROGRESS);
		notiService.saveNotification(new Notification(notiService.findMaxId()+1, LocalDateTime.now(), false, "Activity started", "User " + current.getName() + " started " + aRepo.getReferenceById(id).notificationToString()));
		logger.info("Started activity " + a);
		return Pair.createPair(message, true);
	}
	private Pair<String, Boolean> accept_activity(User current, int id) {
		Activity a = aRepo.getReferenceById(id);
		String message = "accepted the activity " + a;
		
		aRepo.updateStatus(id, STATUS.ACCEPTED);
		notiService.saveNotification(new Notification(notiService.findMaxId()+1, LocalDateTime.now(), false, "Activity accepted", "User " + current.getName() + " accepted " + aRepo.getReferenceById(id).notificationToString()));
		logger.info("Accepted activity " + a);
		
		return Pair.createPair(message, true);
	}
	private Pair<String, Boolean> decline_activity(User current, int id) {
		Activity a = aRepo.getReferenceById(id);
		String message = "declined the activity " + a;
		
		aRepo.updateStatus(id, STATUS.REFUSED);
		notiService.saveNotification(new Notification(notiService.findMaxId()+1, LocalDateTime.now(), false, "Activity declined", "User " + current.getName() + " declined " + aRepo.getReferenceById(id).notificationToString()));
		logger.info("Declined activity " + a);
		
		return new Pair<>(message, true);
	}

	@RequestMapping(method = RequestMethod.POST, value="/editActivity", params={"id", "role"})
	public String editActivity(
			Model model, 
			@FormParam("id") int id,
			@FormParam("role") Optional<ROLES> role,
			@FormParam("author") Optional<String> author,
			@FormParam("body") Optional<String> body,
			@FormParam("msgrt") Optional<Boolean> msgrt
		) {
		if (author.isPresent()) {
			msgService.saveMessage(new Message(aRepo.getReferenceById(id), userRepo.getReferenceById(author.get()), body.get(), LocalDateTime.now(), msgRepo.findMaxId()+1));
		}
		String token = userController.token;
		if (UserToken.checkToken(token)) {
			String name = jwtService.extractUsername(token);
			Activity act = aRepo.findById(id).get();
			model.addAttribute("delegate", act.getDelegate().getName().equals(name));
			model.addAttribute("name", name);
			model.addAttribute("act", act);
			model.addAttribute("role", role.get());
    		model.addAttribute("priorities", Arrays.asList(PRIORITY.values()));
    		model.addAttribute("status", Arrays.asList(STATUS.values()));
    		String format = "d/MM, HH:mm";
    		model.addAttribute("formatter", DateTimeFormatter.ofPattern(format));
    		List<Message> msgs = loadMessages(id).stream().sorted(Comparator.comparing(Message::getTimestamp).reversed()).toList();
    		if (msgs==null || msgs.size()==0) msgs = new ArrayList<>();
    		model.addAttribute("messages", msgs);
			return "/data/editActivity";
		} else return "redirect:/auth/fail?reason=credentials_expired";
	}
	
	private List<Message> loadMessages(int id) {
		return msgRepo.findAllByActivity(aRepo.getReferenceById(id));
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/editActivity", params={"id", "title", "editordata", "priority"})
	public String editActivityDashboard(
			
			@FormParam("id") int id,
			@FormParam("title") String title,
			@FormParam("editordata") String editordata,
			@FormParam("priority") PRIORITY priority,
			@FormParam("status") STATUS status,
			RedirectAttributes model
			) {
		aRepo.updateActivity(id, title, editordata, priority, status);
		model.addAttribute("response", "Edited!");
		model.addAttribute("success", true);
		return "redirect:/data/dashboard";
	}
/*
	@RequestMapping(method = RequestMethod.POST, value="/editActivity", params={"id", "author", "body", "role"})
	public String sendMessage(
			
			@FormParam("id") int id,
			@FormParam("author") String author,
			@FormParam("body") String body,
			@FormParam("role") String role,
			RedirectAttributes model
			) {
		model.addAttribute("id", id);
		model.addAttribute("role", role);
		return "redirect:/data/editActiviy";
	}
*/
}
