package com.stage.stageProject.UserMgmt;

import java.util.HashMap;

import com.stage.stageProject.Auth.AuthData;

public class UserToken {
	public static HashMap<String, AuthData> tokenMap = new HashMap<>();
	
	public static void invalidateToken(String token) {
		tokenMap.remove(token);
	}
	public static void addToken(AuthData u, String token) {
		tokenMap.put(token, u);
	}
	public static boolean checkToken(String token) {
		return tokenMap.containsKey(token);
	}
	public static AuthData getUser(String token) {
		return tokenMap.get(token);
	}
	/**
	 * Only for debug purposes
	 */
	public static String soutMap() {
		System.out.println(tokenMap);
		return tokenMap.toString();
	}
}
