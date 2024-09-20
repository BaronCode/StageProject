package com.stage.stageProject.Notifications;


import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service layer is where all the business logic lies
 */
@Service
public interface NotificationService {
	
	public Notification saveNotification(Notification inter);
	public void updateNotification(int id, boolean s);
	public void deleteNotificationById(int id);
	public List<Notification> filterByStatus(boolean s);
	public int findMaxId();
}


