package com.stage.stageProject.IntersectionMgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntersectionServiceImpl implements IntersectionService {
	
	@Autowired
	private IntersectionRepo repo;

	@Override
	public Intersection saveIntersection(Intersection inter) {
		return repo.save(inter);
	}

	@Override
	public List<Intersection> fetchIntersectionList() {
		return (List<Intersection>) repo.findAll();
	}

	@Override
	public void deleteIntersectionById(String row) {
		repo.deleteById(row);
	}
	
	
}
