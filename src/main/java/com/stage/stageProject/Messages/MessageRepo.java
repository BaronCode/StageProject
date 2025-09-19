package com.stage.stageProject.Messages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stage.stageProject.ActivitiesMgmt.Activity;

@Repository
public interface MessageRepo extends JpaRepository<Message, Integer> {

}
