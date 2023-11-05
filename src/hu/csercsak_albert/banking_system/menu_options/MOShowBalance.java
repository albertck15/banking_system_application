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
		try(var ps = connection.prepareStatement("")){ // TODO Define query
		}
	}

}
