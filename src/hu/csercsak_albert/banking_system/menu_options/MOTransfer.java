package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.TransactionHandler;
import hu.csercsak_albert.banking_system.transaction.TransactionHandlerImpl;

class MOTransfer extends AbstractMenuOption {

	private static final TransactionHandler TRANSACTION_HANDLER = TransactionHandlerImpl.getInstance();

	MOTransfer(OptionTypes type) {
		super(type.getLabel());
	}

	@Override
	void doExecute() throws OperationException, SQLException, FastQuitException {
		int toAccountNum = userInput.inputInt("Reciever account number", 0, Integer.MAX_VALUE);
		// TODO Implement getting user by his account number, then make a transaction
	}

}
