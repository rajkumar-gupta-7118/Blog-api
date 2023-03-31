package com.rk.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.blog.payloads.ResponseApi;
import com.rk.blog.payloads.UserDto;
import com.rk.blog.service.UserService;

@RestController
@RequestMapping("/apis/users")
public class UserController {
	@Autowired
	private UserService userservice;

	/**@apiNote This API is used to Create User
	 * @author Rajkumar
	 * @since 1.0
	 * @param userdto
	 * @return
	 */
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto) {

		UserDto dto = userservice.createUser(userdto);

		return new ResponseEntity<UserDto>(dto, HttpStatus.CREATED);

	}

	/**@apiNote This API is used to Update User
	 * @author Rajkumar
	 * @since 1.0
	 * @param userdto
	 * @param id
	 * @return
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userdto, @PathVariable Integer id) {

		UserDto updateUser = userservice.updateUser(userdto, id);

		return ResponseEntity.ok(updateUser);

	}

	/**@apiNote This API is used to get User
	 * @author Rajkumar
	 * @since 1.0
	 * @param id
	 * @return
	 */
	@GetMapping("/getUser/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {

		UserDto userById = userservice.getUserById(id);

		return ResponseEntity.ok(userById);

	}

	/**@apiNote This API is used to get all User
	 * @author Rajkumar
	 * @since 1.0
	 * @return
	 */
	@GetMapping("/allUsers")
	public ResponseEntity<List<UserDto>> getAllUser() {
		return new ResponseEntity<List<UserDto>>(userservice.getAllUser(), HttpStatus.OK);

	}

	/**@apiNote This API is used to Delete User
	 * @author Rajkumar
	 * @since 1.0
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<ResponseApi> deleteUser(@PathVariable Integer id) {

		userservice.deleteUser(id);

		return new ResponseEntity<ResponseApi>(new ResponseApi("SuccessFully Deleted"), HttpStatus.OK);

	}

}
