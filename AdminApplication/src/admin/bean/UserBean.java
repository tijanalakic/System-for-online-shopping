package admin.bean;

import java.io.Serializable;
import java.util.List;

import admin.dao.UserDAO;
import admin.dto.User;

public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user=new User();
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	private boolean loggedIn=false;
	
	public boolean addUser(User user) {
		
		return UserDAO.create(user);
	}
	
	public boolean deleteUser(int id) {
		return UserDAO.delete(id);
	}
	
	public  List<User> getAll(){
		return UserDAO.getAll();
	}
	
	public void logout() {
		user=new User();
		setLoggedIn(false);
	}

}
