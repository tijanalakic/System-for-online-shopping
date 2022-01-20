package shop.bean;

import java.io.Serializable;
import java.util.List;
import shop.dao.ProductDAO;
import shop.dto.Product;

public class ProductBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProductDAO productDAO=new ProductDAO();
	public static List<Product> getAll() {
		return ProductDAO.getAll();
	}

	public Product getProductById(int id) {
		return productDAO.getById(id);
	}

}
