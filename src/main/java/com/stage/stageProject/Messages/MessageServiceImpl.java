package com.stage.stageProject.Messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepo repo;

	@Override
	public Message saveMessage(Message n) {
		return repo.save(n);
	}

	@Override
	public void deleteMessageById(int id) {
		repo.deleteById(id);
	}
}
