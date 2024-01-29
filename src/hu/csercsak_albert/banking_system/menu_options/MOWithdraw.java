package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.TransactionHandler;
import hu.csercsak_albert.banking_system.transaction.TransactionHandlerImpl;

class MOWithdraw extends AbstractMenuOption {

	private static final Logger LOG = LogManager.getLogger(MOWithdraw.class);

	private static final TransactionHandler TRANSACTION_HANDLER = TransactionHandlerImpl.getInstance();

	MOWithdraw(OptionTypes type) {
		super(type.getLabel());
		LOG.info("initialized");
	}

	@Override
	void doExecute() throws OperationException, SQLException, FastQuitException {
		int withdrawAmount = userInput.inputInt("Enter the amount", 0, Integer.MAX_VALUE);
		LOG.info("withdrawing (%d) amount".formatted(withdrawAmount));
		TRANSACTION_HANDLER.makeWithdraw(connection, user, withdrawAmount);
	}
}
