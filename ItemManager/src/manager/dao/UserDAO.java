package manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manager.dto.User;
import manager.dto.Role;
import manager.util.ConnectionPool;
import manager.util.DAOUtil;

public class UserDAO  {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	public static List<User> getAll() {
		List<User> userList = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		
		try{
			connection = connectionPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, "SELECT * FROM user", false, values);
			rs = ps.executeQuery();
			
			while(rs.next()){				
				User user = new User(rs.getInt("Id"));
				user.setUsername(rs.getString("Username"));
				user.setPassword(rs.getString("Password"));
				if(rs.getString("RoleName").equals(Role.ADMIN.getRole())) {
					user.setRole(Role.ADMIN);
				}
				else if(rs.getString("RoleName").equals(Role.ADMIN_PRODUCT.getRole())) {
					user.setRole(Role.ADMIN_PRODUCT);
				}
				else {
					user.setRole(Role.CUSTOMER);
				}	
				
				
				userList.add(user);
			}
			ps.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
            
			connectionPool.checkIn(connection);
		}
		
		return userList;
	}

	
	public User getById(int id) {
		User user = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object values[]= {id};
		
		try{
			connection = connectionPool.checkOut();
			 ps = DAOUtil.prepareStatement(connection, "SELECT * FROM user WHERE Id = ?", false, values);
			rs = ps.executeQuery();
			
			if(rs.next()){				
				user = new User(rs.getInt("Id"));
				user.setUsername(rs.getString("Username"));
				user.setPassword(rs.getString("Password"));
				
				if(rs.getString("RoleName").equals(Role.ADMIN.getRole())) {
					user.setRole(Role.ADMIN);
				}
				else if(rs.getString("RoleName").equals(Role.ADMIN_PRODUCT.getRole())) {
					user.setRole(Role.ADMIN_PRODUCT);
				}
				else {
					user.setRole(Role.CUSTOMER);
				}							
			}
			ps.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
            
			connectionPool.checkIn(connection);
		}
		
		return user;
	}
	
	public static User getByUsernameAndPassword(String username, String password) {
		User user = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object values[] = {username,password};

		
		try{
			connection = connectionPool.checkOut();
			 ps = DAOUtil.prepareStatement(connection, "SELECT * FROM user WHERE Username = ? AND Password=?", false, values);
			rs = ps.executeQuery();
			
			
			if(rs.next()){				
				user = new User(rs.getInt("Id"));
				user.setUsername(rs.getString("Username"));
				user.setPassword(rs.getString("Password"));
				
				if(rs.getString("RoleName").equals(Role.ADMIN.getRole())) {
					user.setRole(Role.ADMIN);
				}
				else if(rs.getString("RoleName").equals(Role.ADMIN_PRODUCT.getRole())) {
					user.setRole(Role.ADMIN_PRODUCT);
				}
				else {
					user.setRole(Role.CUSTOMER);
				}				
			}
			ps.close();

			
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
			
			connectionPool.checkIn(connection);
		}
		
		return user;
	}
	

	public static boolean create(User user) {
		Connection connection = null;
		int result;
		Object values[] = { user.getUsername(),  user.getPassword() , user.getRole().getRole()};

		
		try{
			connection = connectionPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, "INSERT INTO user (Username, Password, RoleName) VALUES (?, ?, ?)", true, values);
			
			result=ps.executeUpdate();
	
	        if(result != 0) {
	        	return true;
	        }
	    	ps.close();
		
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			
			connectionPool.checkIn(connection);
		}
		
		return false;
	}


	public static boolean update(User user) {
		Connection connection = null;
		Object values[] = { user.getUsername(),  user.getPassword() , user.getRole().getRole() , user.getId()};
		int result;
		try{
			connection = connectionPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, "UPDATE user SET Username = ?, Password = ?, Admin = ? "
					+ "WHERE Id = ?", true, values);
			 result=ps.executeUpdate();
		
	        
	        if(result != 0) {
	        	return true;
	        }
			ps.close();

		
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			
			connectionPool.checkIn(connection);
		}
		
		return false;
	}


	public static boolean delete(int id) {
		Connection connection = null;
		PreparedStatement ps = null;
		int result;
		Object values[] = {id};
		try {
			connection = connectionPool.checkOut();
		    ps = DAOUtil.prepareStatement(connection, "DELETE FROM user WHERE Id = ?", false, values);
			result=ps.executeUpdate();
				
			
			if(result != 0) {
				return true;
			}
			ps.close();

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			
			connectionPool.checkIn(connection);
		}
		
		return false;
	}

}
