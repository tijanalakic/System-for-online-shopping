package shop.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import shop.dao.TransactionDAO;
import shop.dao.UserDAO;
import shop.dto.Item;
import shop.dto.Transaction;

public class TransactionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Transaction transaction = new Transaction();
	private TransactionDAO transactionDAO = new TransactionDAO();
	private static List<Item> items = new ArrayList<>();

	public void addItem(Item item) {
		items.add(item);
	}

	public List<Item> getItems() {
		return transaction.getItems();
	}

	public double getTotal() {
		return transaction.getTotalPrice();
	}

	public void setUser(String username) {

		transaction.setUser(UserDAO.getByUsername(username));
	}

	public void createTransaction() {
		transaction.setItems(items);
		transactionDAO.create(transaction);
		items = new ArrayList<>();
	}

}
