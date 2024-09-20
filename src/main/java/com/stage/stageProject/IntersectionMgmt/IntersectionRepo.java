package com.stage.stageProject.IntersectionMgmt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import com.stage.stageProject.UserMgmt.User;

@Repository
public interface IntersectionRepo extends JpaRepository<Intersection, String> {
	@Modifying
	@Transactional
	@Query("DELETE FROM Intersection i WHERE i.activity=?1")
	public void deleteByActivity(Activity activity);
	
	@Query("SELECT new com.stage.stageProject.IntersectionMgmt.Intersection(row, user, activity) FROM Intersection WHERE user=?1 AND activity=?2")
	public Intersection selectByUserAndActivity(User user, Activity activity);
	
	@Query("SELECT MAX(row) FROM Intersection")
	public int findMaxRow();
}