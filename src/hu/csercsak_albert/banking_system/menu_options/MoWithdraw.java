package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

class MoWithdraw extends AbstractMenuOption {

	@Override
	void doExecute() throws OperationException, SQLException {
		long withdrawAmount = userInput.inputInt("Enter the amount", 0, Integer.MAX_VALUE);
		long balance = getBalance();
		if (balance > withdrawAmount) {
			System.out.printf("Your balance don't have that amount!%n%n");
		} else {
			long newBalance = balance - withdrawAmount;
			updateBalance(newBalance);
		}
	}

	private void updateBalance(long newBalance) throws SQLException {
		try (var ps = connection.prepareStatement("UPDATE balance SET balance = ? WHERE userId = ?")) {
			ps.setLong(1, newBalance);
			ps.setInt(2, user.id());
		}
	}

	private long getBalance() throws SQLException {
		try (var ps = connection.prepareStatement("SELECT balance FROM balance WHERE userId = ?")) {
			ps.setInt(1, user.id());
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		throw new UnsupportedOperationException("Getting balance has been failed");
	}
}
