package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;

public class MOShowDetails extends AbstractMenuOption {

	private static final Logger LOG = LogManager.getLogger(MOShowDetails.class);

	MOShowDetails(OptionTypes type) {
		super(type.getLabel());
		LOG.info("initialized");
	}

	@Override
	void doExecute() throws OperationException, SQLException {
		long balance = queryBalance();
		LOG.info("printing informations");
		System.out.printf("""

				│ Name : %s %s
				│ Email : %s
				│ Account number : %,d
				│ Balance : %s$
				""", user.firstName(), user.lastName(), user.email(), user.accountNumber() //
				, "%,d".formatted(balance).replace(" ", "."));
	}

	private long queryBalance() throws SQLException, OperationException {
		LOG.info("getting balance");
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
