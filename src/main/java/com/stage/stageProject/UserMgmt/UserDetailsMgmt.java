package com.stage.stageProject.UserMgmt;

import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Synthesis class for a User, used by SpringBoot to manage User entities.
 * <br>Getter methods are generated via Lombok - call them using get + capitalized variable nome + ().
 * @see User
 */
@Getter
@Setter
public class UserDetailsMgmt implements UserDetails {
    /**
     * Username.
     */
    private String username;
    /**
     * Password.
     */
    private String password;

    /**
     * Constructor
     * @param user the User whose fields are being synthesized.
     */
    public UserDetailsMgmt(User user) { 
        username = user.getName();
        password = user.getPsw();
    }

    /**
     * A string representation of this UserDetails.
     * @return a String representation of this UserDetails.
     */
    @Override
    public String toString() {
    	return "\n" + username +
    	"\n" + password;
    }

    /**
     * Returns a list of the User's authorities. Method not implemented because Spring Boot's authorities are not used.
     * @deprecated
     * @return a List with zero elements.
     */
    @Deprecated
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
