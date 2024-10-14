package com.stage.stageProject.UserMgmt;

import java.util.HashMap;

import com.stage.stageProject.Auth.AuthData;

/**
 * Utility class that maps all the created tokens in the application uptime.
 */
public class UserToken {
	/**
	 * Token-User mapping of all the active sessions tokens.
	 */
	private static HashMap<String, AuthData> tokenMap = new HashMap<>();
	/**
	 * Token-User mapping of all the created tokens in the application uptime, including invalidated ones.
	 */
	private static HashMap<String, AuthData> invalidatedTokenMap = new HashMap<>();

	/**
	 * Invalidates a token and removes it from the active map.
	 * @param token the token to be invalidated.
	 */
	public static void invalidateToken(String token) {
		invalidatedTokenMap.put(token, tokenMap.get(token));
		tokenMap.remove(token);
	}

	/**
	 * Adds a User-Token entry to the map.
	 * @param u the User .
	 * @param token the generated token.
	 */
	public static void addToken(AuthData u, String token) {
		tokenMap.put(token, u);
	}

	/**
	 * Checks if a token exists in the active map.
	 * @param token the token to be checked.
	 * @return true if the active map contains the token, else false.
	 */
	public static boolean checkToken(String token) {
		return tokenMap.containsKey(token);
	}

	/**
	 * Gets an User AuthData details [username, password] by its token.
	 * @param token the token to search with.
	 * @return an AuthData object with the User's login details.
	 */
	public static AuthData getUser(String token) {
		return tokenMap.get(token);
	}
	/**
	 * Only for debug purposes.
	 */
	public static void soutMap() {
		System.out.println("Token map: " + tokenMap);
		System.out.println("Invalidated token map: " + invalidatedTokenMap);
	}
}
