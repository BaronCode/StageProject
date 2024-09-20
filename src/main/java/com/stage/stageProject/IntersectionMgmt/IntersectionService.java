package com.stage.stageProject.IntersectionMgmt;


import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service layer is where all the business logic lies
 */
@Service
public interface IntersectionService {
	
	public Intersection saveIntersection(Intersection inter);
	public List<Intersection> fetchIntersectionList();
	public void deleteIntersectionById(String row);
}


