package com.stage.stageProject.UserMgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage.stageProject.ActivitiesMgmt.Activity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Implementation of the User Service Layer [USL].
 * @see UserService
 * @see UserRepo
 */
@Service
public class UserServiceImpl implements UserService {

    private     final       UserRepo        repo;

    public UserServiceImpl(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public User createUser(User user) {
        User u = repo.findByEmail(user.getEmail());
        if (u == null) {
            repo.save(user);
        }
        return u;
    }

    @Override
    public User getUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public String getNameByEmail(String email) {
        return repo.findByEmail(email).getName();
    }
}
