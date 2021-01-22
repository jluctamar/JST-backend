package com.jst.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jst.pojos.Users;
import com.jst.repositories.UsersRepo;
import com.jst.services.UserService;


@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UsersController {
	private static final Logger logger = LogManager.getLogger(UsersController.class);
	
	private UserService userService;
	
	@Autowired
	public UsersController( UserService userService ) {
		this.userService = userService;
	}
	
	// Create user 
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> register(@RequestBody String createUserJson)
			throws JsonParseException, JsonMappingException, IOException {
		logger.info("### Inside UserController register endpoint");
		
		int createStatus;
		Map<String, String> createUserMap = new HashMap<String, String>();
		createUserMap = new ObjectMapper().readValue(createUserJson, new TypeReference<Map<String, String>>() {
		});
		
		createStatus = userService.register(createUserMap.get("firstName"), createUserMap.get("lastName"), createUserMap.get("username"), createUserMap.get("password"), createUserMap.get("email"));
		
		Map<String, String> rspMsg = new HashMap<String, String>();
		switch(createStatus) {
			case 0: rspMsg.put("message", "User Created Successfully " ); return new ResponseEntity<>( rspMsg, HttpStatus.OK); 
			case 1: rspMsg.put("message", "User Already Exists"); return new ResponseEntity<>(rspMsg, HttpStatus.CONFLICT);
			case 2: rspMsg.put("message", "Invalid/Missing Form Inputs"); return new ResponseEntity<>( rspMsg, HttpStatus.BAD_REQUEST);
			default: rspMsg.put("message", "Unknown Issue Please Try Again"); return new ResponseEntity<>(rspMsg, HttpStatus.NOT_ACCEPTABLE);
		}  	 	

	}
	
	// User Login 
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> login(@RequestBody String loginCredentialsJson)
			throws JsonParseException, JsonMappingException, IOException {
	
		logger.info("Inside UserController, /login post mapping endpoint ");
	
		Map<String, String> loginCredentials = new HashMap<String, String>();
		loginCredentials = new ObjectMapper().readValue(loginCredentialsJson, new TypeReference<Map<String, String>>() {
		});

		String username = loginCredentials.get("username");
		String password = loginCredentials.get("password");

		logger.info("usersController username -> " + username + " and password -> " + password);

		Users user = userService.login(username, password);
		if (user == null) {
			logger.warn("[usersController] Invalid Credentials, Customer not found....");
			return new ResponseEntity<Users>(user, HttpStatus.UNAUTHORIZED);
		}
		// Create a cloned object to send client side
		Users userClone = new Users();
		userClone.setFirstName(user.getFirstName());
		userClone.setLastName(user.getLastName());
		userClone.setUsername(user.getUsername());
		userClone.setPassword("***********************");
		userClone.setEmail(user.getEmail());
		userClone.setUserId(user.getUserId());
		return new ResponseEntity<Users>(userClone, HttpStatus.OK);
	}



	// Update User
	

}
	
	

