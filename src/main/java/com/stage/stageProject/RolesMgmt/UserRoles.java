package com.stage.stageProject.RolesMgmt;

import com.stage.stageProject.UserMgmt.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Entity @IdClass(UserRolesPrimitive.class) @Table(name = "user_roles") @NoArgsConstructor @AllArgsConstructor
public class UserRoles {
	@Setter
    @NotNull @Id @ManyToOne(optional = false) @JoinColumn(name = "user", nullable = false)		private		User		user;
	@Id	@Enumerated(EnumType.STRING) 															private 	ROLES 		role;
}

