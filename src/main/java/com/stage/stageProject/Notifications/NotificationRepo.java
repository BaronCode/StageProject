package com.stage.stageProject.Notifications;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
	@Query("SELECT new com.stage.stageProject.Notifications.Notification(id, timestamp, status, title, body) FROM Notification WHERE status=?1")
	public List<Notification> selectByStatus(boolean status);

	@Query("SELECT COALESCE(MAX(id), 0) FROM Notification")
	public int findMaxId();
	
	@Modifying
	@Transactional
	@Query("UPDATE Notification SET status=?2 WHERE id=?1")
	public void updateNotification(int id, boolean s);
}
