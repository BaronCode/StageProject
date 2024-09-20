package com.stage.stageProject.Messages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stage.stageProject.ActivitiesMgmt.Activity;


@Repository
public interface MessageRepo extends JpaRepository<Message, Integer> {

	@Query("SELECT new com.stage.stageProject.Messages.Message(activity, author, body, timestamp, id) FROM Message WHERE activity=?1")
	public List<Message> findAllByActivity(Activity a);

	@Query("SELECT COALESCE(MAX(id), 0) FROM Message")
	public int findMaxId();
	
}
