package shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shop.dto.Role;
import shop.dto.User;
import shop.util.ConnectionPool;
import shop.util.DAOUtil;

public class UserDAO  {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	
	
	public static User getByUsername(String username) {
		User user = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object values[] = {username};

		
		try{
			connection = connectionPool.checkOut();
			 ps = DAOUtil.prepareStatement(connection, "SELECT * FROM user WHERE Username = ?", false, values);
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
	

}
