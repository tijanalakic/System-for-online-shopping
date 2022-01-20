package shop.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import shop.dto.Item;
import shop.dto.Transaction;
import shop.util.ConnectionPool;
import shop.util.DAOUtil;

public class TransactionDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	

	public boolean create(Transaction transaction) {
		Connection connection = null;
		Connection connection1 = null;
		CallableStatement cs = null;
		PreparedStatement ps1 = null;
		Object values[] = { transaction.getUser().getId(), transaction.getTotalPrice(),
				new java.sql.Date(transaction.getDate().getTime()), Types.INTEGER };
		try {
			int transactionId = 0;

			connection = connectionPool.checkOut();
			cs = DAOUtil.prepareCall(connection, "CALL transaction_insert(?, ?, ?, ?)", values);
			cs.executeUpdate();

			transactionId = cs.getInt(4);

			connection1 = connectionPool.checkOut();

			for (Item item : transaction.getItems()) {

				Object valuesItem[] = { item.getProduct().getId(), transactionId, item.getQuantity(), item.getPrice() };
				ps1 = DAOUtil.prepareStatement(connection1,
						"INSERT INTO item (ProductId, TransactionId, Quantity, Price)" + " VALUES (?, ?, ?, ?)", false,
						valuesItem);
				ps1.executeUpdate();
			}

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}

		return false;
	}

}
