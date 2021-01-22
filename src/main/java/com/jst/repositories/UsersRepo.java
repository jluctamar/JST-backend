package com.jst.repositories;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jst.pojos.Users;

@Repository
@Transactional
public class UsersRepo {
    private static final Logger logger = LogManager.getLogger(UsersRepo.class);
    
    private SessionFactory sessionFactory;
    
    
	// Constructor Dependency Injection 
	@Autowired
	public UsersRepo(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;
		logger.info(
				"Inside User Repository Constructer....SessionFactory up and running: " + this.sessionFactory);
	} 
    
	
	// Create a user
	public void register(String firstName, String lastName, String username, String password, String email) {
		logger.info( "###[UserRepo] Inside of register function");
		
		Session session = sessionFactory.getCurrentSession();
		
		int createdId = (int )session.save(new Users(firstName , lastName, username, password , email));
		logger.info( "################## Result: " + createdId);
	}
	
	// User login
	public Users login(String username, String password) { 
		logger.info( "[UserRepo] Inside of login function");
		
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Users U WHERE (U.username = :username AND U.password = :password)";
		
		
		List<Users> userList = session.createQuery(hql).setParameter("username", username).setParameter("password", password).getResultList();
		
		if(userList.size() > 0) { 
		 logger.info("********************* UserList size: " + userList.size());
		 logger.info("********************* UserList - first element: " + userList.get(0));
		 return (Users)userList.get(0);
		}
		
		return null;
	}
	
	
	// User update
	
	
	
	// Validate Existing user
	public boolean isValidUser(String username, String email) {
		
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Users U WHERE (U.username = :username OR U.email = :email)";
		List<Users> userList =  session.createQuery(hql).setParameter("username", username).setParameter("email", email).getResultList();
		if(userList.size() > 0) { 
			return true;
		}
		return false;
	}
}
