package com.epam.controller;

import java.util.List;

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

import com.epam.dto.UserDto;
import com.epam.exception.UserNotFoundException;
import com.epam.model.User;
import com.epam.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {

		UserDto savedUser=userService.createUser(user);
		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);

	}
	
	@GetMapping("/")
	public ResponseEntity<List<User>> showAccounts()
	{
		return new ResponseEntity<>(userService.displayAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<UserDto> findUser(@PathVariable String username) throws UserNotFoundException
	{
		UserDto userDto=userService.findUser(username);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<String> DeleteUser(@PathVariable String username)
	{
		userService.deleteUser(username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	@PutMapping("/{username}")
	public ResponseEntity<String> updateUser(@PathVariable String username,@RequestBody UserDto userDto)
	{
		
		userService.updateUser(username,userDto);
		return new ResponseEntity<>("user updated",HttpStatus.OK);
	}
}
