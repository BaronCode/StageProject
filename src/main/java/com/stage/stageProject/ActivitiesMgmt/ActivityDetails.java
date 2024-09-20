package com.stage.stageProject.ActivitiesMgmt;


public class ActivityDetails {
	    private String name; 
	    private int id;
	    private PRIORITY priority;
	    
	    public ActivityDetails(Activity activity) { 
	        name = activity.getName(); 
	        id = activity.getId();
	        priority = activity.getPriority();
	    } 

	    public String getName() { 
	        return name; 
	    } 

	    public int getId() { 
	        return id; 
	    } 

	    public PRIORITY getPriority() { 
	        return priority; 
	    } 

	    public String toString() {
	    	return "\n" + name +
	    	"\n" + id +
	    	"\n" + priority;
	    }
	

}
