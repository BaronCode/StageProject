package com.stage.stageProject.UserMgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage.stageProject.ActivitiesMgmt.Activity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo repo;
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public User saveUser(User user) {
		return repo.save(user);
	}

	@Override
	public List<User> fetchUserList() {
		// TODO Auto-generated method stub
		return (List<User>) repo.findAll();
	}

	@Override
	public User updateUser(User user, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUserById(String name) {
		repo.deleteById(name);
	}

	@Transactional
	@Override
	public void persist(String user) {
		entityManager.persist(repo.getReferenceById(user));
	}
	
	@Transactional
	@Override
	public void mergeUserAndActivity(User user) {
		entityManager.merge(user); 
		
	}
	
	@Transactional
	@Override
	public void removeUserAndActivity(String name) {
		User u = repo.getReferenceById(name);
		entityManager.remove(entityManager.contains(u) ? u : entityManager.merge(u)); 
	}

	@Transactional
	@Override
	public void refreshUser(User user) {
		entityManager.refresh(user); 
	}

	@Transactional
	@Override
	public void detachUser(User user) {
		entityManager.detach(user); 
	}

	@Transactional
	@Override
	public void removeActivityFromUser(String name, Activity activity) {
		User user = repo.getReferenceById(name);
		user.getActivities().remove(activity);
        if (!entityManager.contains(user)) {
            entityManager.merge(user); 
        }
	}
}
