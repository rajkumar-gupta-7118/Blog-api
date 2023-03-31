package com.rk.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rk.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@NoArgsConstructor
@Getter
@ToString
public class UserDto {

	private int id;

	@NotEmpty(message = "Name Cannot be null !")
	private String name;

	@Email(message = "Invalid Email ! use valid Email ")
	@NotEmpty
	private String email;

	@NotEmpty(message = "Password cannot be empty !")
	@Size(min = 4, message = "Password must be greater than 4 characters")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@NotNull(message = "About cannot be null")
	private String about;

	private Set<Role> roles = new HashSet<Role>();

}
