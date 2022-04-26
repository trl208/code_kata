package com.epam.client;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.dto.UserDto;
import com.epam.exception.UserNotFoundException;

@FeignClient(name="user-service")
@LoadBalancerClient(name="user-service")
public interface UserClient {

	@PostMapping("/users/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto user);
	
	@GetMapping("//users/")
	public ResponseEntity<List<UserDto>> showAccounts();
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserDto> findUser(@PathVariable String username) throws UserNotFoundException;
	
	@DeleteMapping("/users/{username}")
	public ResponseEntity<String> DeleteUser(@PathVariable String username);
	
	@PutMapping("/users/{username}")
	public ResponseEntity<String> updateUser(@PathVariable String username,@RequestBody UserDto userDto);
}
