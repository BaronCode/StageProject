/*
 * Server = localhost
 * Database = postgres
 * Port = 5432
 * Username = postgres
 * Password = angelone
 */


package com.stage.stageProject;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StageProjectApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	SpringApplication app = new SpringApplication(StageProjectApplication.class);
    	app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }
}