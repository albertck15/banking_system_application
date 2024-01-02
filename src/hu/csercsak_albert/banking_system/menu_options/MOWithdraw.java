package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;

class MOWithdraw extends AbstractMenuOption {

	MOWithdraw(OptionTypes type) {
		super(type.getLabel());
	}

	@Override
	void doExecute() throws OperationException, SQLException {
		long withdrawAmount = userInput.inputInt("Enter the amount", 0, Integer.MAX_VALUE);
		long balance = getBalance();
		if (balance > withdrawAmount) {
			System.out.printf("Your balance don't have enough amount!%n%n");
		} else {
			long newBalance = balance - withdrawAmount;
			updateBalance(newBalance);
		}
	}

	@Override
	public String getLabel() {
		return label;
	}

	private void updateBalance(long newBalance) throws SQLException {
		try (var ps = connection.prepareStatement("UPDATE balance SET balance = ? WHERE user_id = ?")) {
			ps.setLong(1, newBalance);
			ps.setInt(2, user.id());
			ps.executeUpdate();
		}
	}

	private long getBalance() throws SQLException {
		try (var ps = connection.prepareStatement("SELECT balance FROM balance WHERE user_id = ?")) {
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
