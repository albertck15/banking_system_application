package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.TransactionHandler;
import hu.csercsak_albert.banking_system.transaction.TransactionHandlerImpl;

class MOWithdraw extends AbstractMenuOption {

	private static final TransactionHandler TRANSACTION_HANDLER = TransactionHandlerImpl.getInstance();

	MOWithdraw(OptionTypes type) {
		super(type.getLabel());
	}

	@Override
	void doExecute() throws OperationException, SQLException, FastQuitException {
		int withdrawAmount = userInput.inputInt("Enter the amount", 0, Integer.MAX_VALUE);
		TRANSACTION_HANDLER.makeWithdraw(connection, user, withdrawAmount);
	}
}
