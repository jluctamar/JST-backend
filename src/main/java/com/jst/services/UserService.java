package com.jst.services;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jst.pojos.Users;
import com.jst.repositories.UsersRepo;


@Service
@Transactional
public class UserService {		
	
	 private static final Logger logger = LogManager.getLogger(UserService.class);
	 private UsersRepo userRepo;
	 
	 @Autowired
	 public UserService (UsersRepo userRepo) {
		 this.userRepo = userRepo;
		
	 }
	 
	 
	 // Create  user 
	 public int register(String firstName, String lastName, String username, String password, String email) {  
		
		 // check to see if this user exist within the database
		 if(username == null || password == null) return 2; // Invalid Form input
		 boolean isUser = userRepo.isValidUser(username, email);
		
		 logger.info("###[UserService Create user] value of isUser variable: "+ isUser);
		 // if the user is not found in the database, then add the new use 
		 	// else notify the client to update their information 
		 
		 if(isUser) {
			 return 1; // User Already Exists
		 }
		 userRepo.register(firstName, lastName, username, password, email);
		 return 0; // User Created Successfully 
	 }
	 
	 // User Login
	 public Users login(String username, String password) {  
		 return userRepo.login(username, password);
	 }
	 
	 
	 // Updating a User 
	 public Users update(Map<String, String> userUpdate) {
		 logger.info("[UserService] Update user");
		 Users updatedUser = userRepo.update(userUpdate.get("firstName"), userUpdate.get("lastName"), userUpdate.get("username"), userUpdate.get("password"), userUpdate.get("email")); 
		 return updatedUser; 
	 }
	 
	 // Delete a User 
	 public boolean delete(Map<String, String> userDelete) {
		 logger.info("[UserService] Delete user");
		 
		 return userRepo.delete(userDelete.get("username"));
	 }
	 
	 
	 // Get All Users 
	 public List<Users> getAllUsers() {
		 return userRepo.getAll();
	 }
}
