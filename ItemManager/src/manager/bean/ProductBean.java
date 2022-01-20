package manager.bean;

import java.io.Serializable;
import java.util.List;

import manager.dao.ProductDAO;
import manager.dto.Product;

public class ProductBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static List<Product> getAll() {
		return ProductDAO.getAll();
	}
	
	public static boolean addProduct(Product product) {
		return ProductDAO.create(product);
	}
	
	public boolean deleteProduct(int id) {
		return new ProductDAO().delete(id);
	}

}
