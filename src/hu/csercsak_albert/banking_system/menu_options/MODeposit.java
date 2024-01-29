package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.TransactionHandler;
import hu.csercsak_albert.banking_system.transaction.TransactionHandlerImpl;

class MODeposit extends AbstractMenuOption {

	private static final Logger LOG = LogManager.getLogger(MODeposit.class);

	private static final TransactionHandler TRANSACTION_HANDLER = TransactionHandlerImpl.getInstance();

	MODeposit(OptionTypes type) {
		super(type.getLabel());
		LOG.info("initialized");
	}

	@Override
	void doExecute() throws OperationException, SQLException, FastQuitException {
		int amount = userInput.inputInt("Enter deposit amount", 0, Integer.MAX_VALUE);
		LOG.info("User(%d) depositing %,d$ amount".formatted(user.id(), amount));
		TRANSACTION_HANDLER.makeDeposit(connection, user, amount);
	}
}
