package manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manager.dto.Product;
import manager.util.ConnectionPool;
import manager.util.DAOUtil;

public class ProductDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM product";

	public static List<Product> getAll() {
		List<Product> productList = new ArrayList<>();
		Connection connection=null;
		ResultSet rs = null;
		
		try{
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false);
			rs = pstmt.executeQuery();			
			while(rs.next()){				
				Product product = new Product(rs.getInt("Id"));
				product.setName(rs.getString("Name"));
				product.setDescription(rs.getString("Description"));
				product.setPrice(rs.getDouble("Price"));
				product.setActive(rs.getBoolean("Active"));
				
				productList.add(product);
			}
			
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{		
            
			connectionPool.checkIn(connection);
		}
		
		return productList;
	}
 
	
	public Product getById(int id) {
		Product product = null;
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try{
			connection = connectionPool.checkOut();
			ps = DAOUtil.prepareStatement(connection, "SELECT * FROM product WHERE Id = ?", false);
			ps.setInt(1, id);
			rs = ps.executeQuery();	
			
			if(rs.next()){				
				product = new Product(rs.getInt("Id"));
				product.setName(rs.getString("Name"));
				product.setDescription(rs.getString("Description"));
				product.setPrice(rs.getDouble("Price"));
				product.setActive(rs.getBoolean("Active"));
			}
			ps.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
			
            
			connectionPool.checkIn(connection);
		}
		
		return product;
	}


	public static boolean create(Product product) {
		boolean result=false;
		Connection connection = null;
		PreparedStatement ps = null;

		Object values[]= {product.getName(),product.getDescription(),product.getPrice(),product.isActive()};
		
		try{
			connection = connectionPool.checkOut();
			ps = DAOUtil.prepareStatement(connection,"INSERT INTO product (Name, Description, Price, Active) VALUES (?, ?, ?, ?)", true,values);			
			ps.executeUpdate();
			
			if(ps.getUpdateCount()>0) {
				result = true;
			}
			

			ps.close();			        
		
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			
			connectionPool.checkIn(connection);
		}
		
		return result;
	}


	public boolean update(Product product) {
		Connection connection = null;
		PreparedStatement ps = null;
		Object values[]= {product.getName(),product.getDescription(),product.getPrice(),product.isActive(),product.getId()};
		
		try{
			connection = connectionPool.checkOut();
			ps = DAOUtil.prepareStatement(connection,"UPDATE product SET Name = ?, Description = ?, Price = ?, Active = ? "
					+ "WHERE Id = ?",true, values);			

	        if(ps.executeUpdate() != 0) {
	        	return true;
	        }	      
	        ps.close();
		
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			
			connectionPool.checkIn(connection);		}
		
		return false;
	}


	public boolean delete(int id) {
		Product product = getById(id);
		product.setActive(false);
		if(update(product)) {
			return true;
		}
		
		return false;
	}
	
}
