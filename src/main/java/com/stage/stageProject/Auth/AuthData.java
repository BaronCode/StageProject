package com.stage.stageProject.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthData {
	private String name;
	private String psw;
	public AuthData(String name, String psw) {
		this.name = name;
		this.psw = psw;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return psw;
	}
}
