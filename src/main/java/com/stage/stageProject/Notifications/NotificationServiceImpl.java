package com.stage.stageProject.Notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepo repo;

	@Override
	public Notification saveNotification(Notification n) {
		return repo.save(n);
	}

	@Override
	public void updateNotification(int id, boolean s) {
		repo.getReferenceById(id).setStatus(s);
	}

	@Override
	public void deleteNotificationById(int id) {
		repo.deleteById(id);
	}

	@Override
	public List<Notification> filterByStatus(boolean s) {
		return repo.selectByStatus(s);
	}

	@Override
	public int findMaxId() {
		return repo.findMaxId();
	}

	
}
