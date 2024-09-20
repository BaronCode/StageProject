package com.stage.stageProject.Messages;


import org.springframework.stereotype.Service;

/**
 * Service layer is where all the business logic lies
 */
@Service
public interface MessageService {	
	public Message saveMessage(Message inter);
	public void deleteMessageById(int id);
}


