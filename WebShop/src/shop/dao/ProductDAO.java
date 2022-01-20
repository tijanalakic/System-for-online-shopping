package shop.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.dto.Product;
import shop.util.ConnectionPool;
import shop.util.DAOUtil;

public class ProductDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		Object values[]= {id};
		
		try{
			connection = connectionPool.checkOut();
			ps = DAOUtil.prepareStatement(connection, "SELECT * FROM product WHERE Id = ?", false, values);
	
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

}
