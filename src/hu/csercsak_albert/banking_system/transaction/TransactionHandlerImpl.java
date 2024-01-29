package hu.csercsak_albert.banking_system.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.TransactionHandler;
import hu.csercsak_albert.banking_system.main.User;

public class TransactionHandlerImpl implements TransactionHandler {

	private static final TransactionHandlerImpl INSTANCE = new TransactionHandlerImpl();

	private TransactionHandlerImpl() {
	}

	public static TransactionHandlerImpl getInstance() {
		return INSTANCE;
	}

	// *********************************
	// Transaction from user to user
	//

	@Override
	public void makeTransaction(Connection connection, Transaction transaction) throws OperationException, SQLException {
		int fromId = transaction.from().id();
		int toId = transaction.to().id();
		int amount = transaction.total();
		if (isAmountAvailable(connection, fromId, amount)) {
			logTransaction(connection, transaction);
			updateBalance(connection, fromId//
					, getBalance(connection, fromId) - amount);
			updateBalance(connection, toId//
					, getBalance(connection, toId) + amount);
			System.out.printf(" Transaction has been completed!%n%n");
			System.out.printf(" Your balance : $%,d%n%n", getBalance(connection, fromId));
		} else {
			throw new OperationException("Your balance doesn't have enough amount!");
		}

	}

	// *********************************
	// Withdraw
	//

	@Override
	public void makeWithdraw(Connection connection, User user, int amount) throws OperationException, SQLException {
		int balance = getBalance(connection, user.id());
		if (balance < amount) {
			System.out.printf("%n Your balance doesn't have enough amount!%n");
		} else {
			int newBalance = balance - amount;
			Withdraw withdraw = new Withdraw(user, amount, newBalance, LocalDateTime.now());
			logWithdraw(connection, withdraw);
			updateBalance(connection, user.id(), newBalance);
		}
		System.out.printf("%nOperation made succesfully!%n Your balance : $%,d%n", getBalance(connection, user.id()));
	}

	// *********************************
	// Deposit
	//

	@Override
	public void makeDeposit(Connection connection, User user, int amount) throws OperationException, SQLException {
		int newBalance = amount + getBalance(connection, user.id());
		updateBalance(connection, user.id(), newBalance);
		Deposit deposit = new Deposit(user, amount, newBalance, LocalDateTime.now());
		logDeposit(connection, deposit);
		System.out.printf("%nOperation made succesfully!%n Your balance : $%,d%n", getBalance(connection, user.id()));
	}

	// *********************************
	// Helper methods
	//

	private boolean isAmountAvailable(Connection connection, int userId, int amount) throws SQLException {
		return getBalance(connection, userId) >= amount;
	}

	private int getBalance(Connection connection, int userId) throws SQLException {
		try (var ps = connection.prepareStatement("SELECT balance FROM balance WHERE user_id = ?")) {
			ps.setInt(1, userId);
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		throw new UnsupportedOperationException("Getting balance has been failed");
	}

	private void updateBalance(Connection connection, int userId, long newBalance) throws SQLException {
		try (var ps = connection.prepareStatement("UPDATE balance SET balance = ? WHERE user_id = ?")) {
			ps.setLong(1, newBalance);
			ps.setInt(2, userId);
			ps.executeUpdate();
		}
	}

	// *********************************
	// Logging interactions to the database
	//

	private void logTransaction(Connection connection, Transaction transaction) throws SQLException, OperationException {
		try (var ps = connection.prepareStatement("""
				INSERT INTO transaction(from_id,to_id,amount,fee,date,new_balance,description)
				VALUES(?,?,?,?,?,?,?)
				""")) {
			ps.setInt(1, transaction.from().id());
			ps.setInt(2, transaction.to().id());
			ps.setInt(3, transaction.amount());
			ps.setInt(4, transaction.feeAmount());
			ps.setTimestamp(5, java.sql.Timestamp.valueOf(transaction.date()));
			ps.setInt(6, getBalance(connection, transaction.from().id()) - transaction.total());
			ps.setString(7, transaction.description());
			if (ps.executeUpdate() < 1) {
				throw new OperationException("Something went wrong while approving transaction!");
			}
		}
	}

	private void logWithdraw(Connection connection, Withdraw withdraw) throws SQLException, OperationException {
		try (var ps = connection.prepareStatement("""
				INSERT INTO withdraw(user_id,amount,new_balance,date)
				VALUES(?,?,?,?)
				""")) {
			ps.setInt(1, withdraw.user().id());
			ps.setInt(2, withdraw.amount());
			ps.setInt(3, withdraw.newBalance());
			ps.setTimestamp(4, java.sql.Timestamp.valueOf(withdraw.date()));
			if (ps.executeUpdate() < 1) {
				throw new OperationException("Something went wrong while approving transaction!");
			}
		}
	}
	
	private void logDeposit(Connection connection, Deposit deposit) throws SQLException, OperationException {
		try (var ps = connection.prepareStatement("""
				INSERT INTO deposit(user_id,amount,new_balance,date)
				VALUES(?,?,?,?)
				""")) {
			ps.setInt(1, deposit.user().id());
			ps.setInt(2, deposit.amount());
			ps.setInt(3, deposit.newBalance());
			ps.setTimestamp(4, java.sql.Timestamp.valueOf(deposit.date()));
			if (ps.executeUpdate() < 1) {
				throw new OperationException("Something went wrong while approving transaction!");
			}
		}
	}
}
