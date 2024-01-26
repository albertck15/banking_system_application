package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;

public class MOShowDetails extends AbstractMenuOption {

	MOShowDetails(OptionTypes type) {
		super(type.getLabel());
	}

	@Override
	void doExecute() throws OperationException, SQLException {
		long balance = queryBalance();
		System.out.printf("""
				Name : %s %s
				Email : %s
				Account number : %,d
				Balance : %s$
				""", user.firstName(), user.lastName(), user.email(), user.accountNumber() //
				, "%,d".formatted(balance).replace(" ", "."));
	}

	private long queryBalance() throws SQLException, OperationException {
		try (var ps = connection.prepareStatement("SELECT balance FROM balance WHERE user_id = ?")) {
			ps.setInt(1, user.id());
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
			}
		}
		throw new OperationException("Query did not found the expected value");
	}
}
