package shop.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Transaction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private User user;
	private List<Item> items;
	private double totalPrice;
	private Date date;
	
	public Transaction() {
		date = new Date();
	}
	
	public Transaction(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
		
		totalPrice = 0;
		
		for(Item item : items) {
			totalPrice += item.getPrice();
		}
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
