package com.rk.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.blog.entities.User;
import com.rk.blog.exceptions.ResourceNotFoundException;
import com.rk.blog.payloads.UserDto;
import com.rk.blog.repositories.UserRepo;
import com.rk.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userrepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userdto) {

		User user = dtoToUser(userdto);
		User u = userrepo.save(user);

		return userToDto(u);
	}

	@Override
	public UserDto updateUser(UserDto userdto, Integer id) {

		User user = userrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());

		UserDto dto = userToDto(user);
		return dto;
	}

	@Override
	public UserDto getUserById(Integer id) {

		User user = userrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {

		List<User> list = userrepo.findAll();

		List<UserDto> dtolist = list.stream().map((user) -> userToDto(user)).collect(Collectors.toList());

		return dtolist;
	}

	@Override
	public void deleteUser(Integer id) {

		userrepo.deleteById(id);

	}

	private User dtoToUser(UserDto ud) {

		User user = modelMapper.map(ud, User.class);
//		user.setId(ud.getId());
//		user.setEmail(ud.getEmail());
//		user.setName(ud.getName());
//		user.setPassword(ud.getPassword());
//		user.setAbout(ud.getAbout());

		return user;

	}

	private UserDto userToDto(User u) {

		UserDto ud = modelMapper.map(u, UserDto.class);
//		ud.setId(u.getId());
//		ud.setName(u.getName());
//		ud.setEmail(u.getEmail());
//		ud.setPassword(u.getPassword());
//		ud.setAbout(u.getAbout());

		return ud;

	}

}
