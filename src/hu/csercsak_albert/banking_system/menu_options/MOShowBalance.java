package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;

public class MOShowBalance extends AbstractMenuOption {

	MOShowBalance(OptionTypes type) {
		super(type.getLabel());
	}

	@Override
	void doExecute() throws OperationException, SQLException {
		try (var ps = connection.prepareStatement("SELECT balance FROM balance WHERE user_id = ?")) {
			ps.setInt(1, user.id());
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					System.out.printf("%n Your balance : $%,d%n%n", rs.getLong(1));
				}
			}
		}
	}

}
