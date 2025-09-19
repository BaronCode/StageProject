package com.stage.stageProject.View;

import com.stage.stageProject.Notifications.NotificationServiceImpl;
import com.stage.stageProject.RolesMgmt.UserRolesServiceImpl;
import com.stage.stageProject.UserMgmt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stage.stageProject.Auth.JwtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/data")
@Controller
public class UserDashboard {

    private		final 		JwtService 					jwtService;
    private		final       UserServiceImpl             userService;
    private		final		NotificationServiceImpl 	notificationService;
    private		final       UserRolesServiceImpl        userRolesService;
    private		final		Logger						logger;

    public UserDashboard(JwtService js, UserServiceImpl usi, NotificationServiceImpl nsi, UserRolesServiceImpl ursi) {
        this.jwtService = js;
        this.userService = usi;
        this.notificationService = nsi;
        this.userRolesService = ursi;
        this.logger = LoggerFactory.getLogger(AuthenticationController.class);
    }

	@RequestMapping("/")
	public String parseRole(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication: " + authentication.getPrincipal().toString());
        return "redirect:/data/dashboard/";
	}

    @RequestMapping("/dashboard")
    public String dashboard(Authentication a, Model model) {
    	String email = a.getName();
        logger.info("Displaying dashboard of {}", userService.getNameByEmail(email));
/*
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
                    .getCreator()
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

        ArrayList<Activity> created = new ArrayList<>(aRepo.findAll().stream().filter(o->o.getCreator().getName().equals(name)).toList());

        model.addAttribute("admin", urRepo.existsById(new UserRolesPrimitive(name, ROLES.ADMIN)));
        model.addAttribute("name", name);
        model.addAttribute("role", ROLES.USER);
        model.addAttribute("activities", activities);
        model.addAttribute("delegate", delegate);
        model.addAttribute("created", created);
        model.addAttribute("priorities", Arrays.asList(PRIORITY.values()));
        model.addAttribute("mappedActivities", aMap);
        model.addAttribute("createdActivities", createdMap);
*/
        return "/data/dashboard";
    	
    }
	/*
    @PostMapping("/dashboard")
	public String editDashboard(
			@RequestParam(value = "edit", defaultValue="add") String action,
			@FormParam("name") Optional<String> name,
			@FormParam("id") Optional<Integer> id,
			@FormParam("priority") Optional<PRIORITY> priority,
			RedirectAttributes model
			) {
    	String uname = jwtService.extractUserMail(userController.token);
		User current = userRepo.getReferenceById(uname);
		Pair<String, Boolean> pair;
		
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
			notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity creation", a.notificationToString(), LocalDateTime.now(), false));
            logger.info("Activity {} created!", a);
			message = "created a new activity!\nDetails: " + a;
			success = true;
		} else logger.error("Activity with id {} already exists!", id);
		
		return Pair.createPair(message, success);
    }

	private Pair<String, Boolean> remove_activity(User current, String uname, int id) {
		boolean success = false;
    	String message = "Unable to delete activity!\nDetails: trying to delete activity not created by " + uname;
		Activity a = aRepo.getReferenceById(id);
		if (current.getActivities().contains(a)) {
			userService.removeActivityFromUser(uname, a);
			userService.refreshUser(current);
			notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity deletion", a.notificationToString(), LocalDateTime.now(), false));
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
			notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity assign", "Assigned " + aRepo.getReferenceById(id).notificationToString() + " to " + current.getName(), LocalDateTime.now(), false));
			logger.info("Assigned activity " + aRepo.getReferenceById(id));
			message = "assigned to yourself a new activity!\nDetails: " + aRepo.getReferenceById(id);
			success = true;
		} else logger.error(message);
		
		return Pair.createPair(message, success);
	}
	private Pair<String, Boolean> done_activity(User current, int id) {
		Activity a = aRepo.getReferenceById(id);
		aRepo.updateActivity(a.getId(), a.getName(), a.getDescription(), a.getPriority(), STATUS.COMPLETED);
		
		notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity done", "User " + current.getName() + " completed " + aRepo.getReferenceById(id).notificationToString(), LocalDateTime.now(), false));
		String message = "completed an activity!\nDetails: " + a;
		logger.info("Completed " + a);
		return Pair.createPair(message, true);
	}
	private Pair<String, Boolean> start_activity(User current, int id) {
		Activity a = aRepo.getReferenceById(id);
		String message = "started an activity!\nDetails: " + a;
		
		aRepo.updateStatus(id, STATUS.IN_PROGRESS);
		notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity started", "User " + current.getName() + " started " + aRepo.getReferenceById(id).notificationToString(), LocalDateTime.now(), false));
		logger.info("Started activity " + a);
		return Pair.createPair(message, true);
	}
	private Pair<String, Boolean> accept_activity(User current, int id) {
		Activity a = aRepo.getReferenceById(id);
		String message = "accepted the activity " + a;
		
		aRepo.updateStatus(id, STATUS.ACCEPTED);
		notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity accepted", "User " + current.getName() + " accepted " + aRepo.getReferenceById(id).notificationToString(), LocalDateTime.now(), false));
		logger.info("Accepted activity " + a);
		
		return Pair.createPair(message, true);
	}
	private Pair<String, Boolean> decline_activity(User current, int id) {
		Activity a = aRepo.getReferenceById(id);
		String message = "declined the activity " + a;
		
		aRepo.updateStatus(id, STATUS.REFUSED);
		notiService.saveNotification(new Notification(notiService.findMaxId()+1, "Activity declined", "User " + current.getName() + " declined " + aRepo.getReferenceById(id).notificationToString(), LocalDateTime.now(), false));
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
			msgService.saveMessage(new Message(msgRepo.findMaxId()+1, userRepo.getReferenceById(author.get()), body.get(), LocalDateTime.now(), aRepo.getReferenceById(id)));
		}
		String token = userController.token;
		if (UserToken.checkToken(token)) {
			String name = jwtService.extractUserMail(token);
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