package com.stage.stageProject.ActivitiesMgmt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stage.stageProject.UserMgmt.User;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Integer> {
	
	@Query("SELECT MAX(id) FROM Activity")
	public int findMaxId();
	
	@Modifying
	@Transactional
	@Query("UPDATE Activity SET name=?2, description=?3, priority=?4, status=?5 WHERE id=?1")
	public void updateActivity(int id, String name, String description, PRIORITY priority, STATUS status);
	
	@Modifying
	@Transactional
	@Query("UPDATE Activity SET delegate=?2 WHERE id=?1")
	public void updateDelegate(int id, User delegate);

	@Modifying
	@Transactional
	@Query("UPDATE Activity SET status=?2 WHERE id=?1")
	public void updateStatus(int id, STATUS status);
}
