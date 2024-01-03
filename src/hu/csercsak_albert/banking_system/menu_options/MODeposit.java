package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.TransactionHandler;
import hu.csercsak_albert.banking_system.transaction.TransactionHandlerImpl;

class MODeposit extends AbstractMenuOption {

	private static final TransactionHandler TRANSACTION_HANDLER = TransactionHandlerImpl.getInstance();

	MODeposit(OptionTypes type) {
		super(type.getLabel());
	}

	@Override
	void doExecute() throws OperationException, SQLException, FastQuitException {
		int amount = userInput.inputInt("Enter deposit amount", 0, Integer.MAX_VALUE);
		TRANSACTION_HANDLER.makeDeposit(connection, user.id(), amount);
	}
}
