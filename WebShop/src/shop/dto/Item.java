package shop.dto;

public class Item {
	
	private Product product;
	private double quantity;
	private double price;
	
	public Item() 
	{
		super();
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
	
		this.product = product;
		
	}
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		
		if(product != null) {
			price = quantity * product.getPrice();
		}
		
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}
}
