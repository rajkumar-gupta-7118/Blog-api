package com.rk.blog.service;

import java.util.List;

import com.rk.blog.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userdto);

	UserDto updateUser(UserDto userdto, Integer id);

	UserDto getUserById(Integer id);

	List<UserDto> getAllUser();

	void deleteUser(Integer id);

}
