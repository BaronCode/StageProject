package com.stage.stageProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class Error implements ErrorController  {
/*
    @RequestMapping("/error")
    public String handleError() {
		String content = "";
		try {
			File f = new File("src/main/resources/static/error.txt");
			System.out.println(f.getPath());
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				content = content.concat(sc.nextLine() + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	return content;
    }
    */
}
