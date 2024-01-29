package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.TransactionHandler;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.transaction.Transaction;
import hu.csercsak_albert.banking_system.transaction.TransactionHandlerImpl;

class MOTransfer extends AbstractMenuOption {

	private static final Logger LOG = LogManager.getLogger(MOTransfer.class);

	private static final double TRANSACTION_FEE = 2.6d; // in %

	private static final int FEE_LIMIT = 10_000;

	private static final TransactionHandler TRANSACTION_HANDLER = TransactionHandlerImpl.getInstance();

	MOTransfer(OptionTypes type) {
		super(type.getLabel());
		LOG.info("initialized");
	}

	@Override
	void doExecute() throws OperationException, SQLException, FastQuitException {
		int amount = getAmount();
		int fee = 0;
		int total = amount;
		if (amount >= FEE_LIMIT) {
			LOG.info("transaction will have fee");
			boolean choice = userInput.inputBool("This transaction will have a %.1f%% fee. Countinue".formatted(TRANSACTION_FEE));
			if (choice) {
				LOG.info("adding fee");
				fee = (int) (amount * (TRANSACTION_FEE / 100d));
				total += fee;
			} else {
				LOG.info("cancelled");
				throw new OperationException("You've cancelled the transaction");
			}
		}
		int toAccountNum = getAccountNum();
		User to = getUserByAccNum(toAccountNum);
		String desc = userInput.inputBool("Would you like to add a description for the transaction") //
				? userInput.inputText("Description")
				: null;
		Transaction transaction = new Transaction//
		(this.user, to, amount, fee, total, LocalDateTime.now(), 0, desc); // Dummy new balance
		if (approve(transaction)) {
			LOG.info("calling transaction handler");
			TRANSACTION_HANDLER.makeTransaction(connection, transaction);
		}
	}

	private int getAmount() throws FastQuitException, SQLException, OperationException {
		int amount = 0;
		int counter = 0;
		do {
			System.out.println();
			if (counter++ > 0) {
				LOG.info("User does not have enough amount (%d$)".formatted(amount));
				System.out.printf("Your balance doesn't have enough amount!%n%n");
			}
			amount = userInput.inputInt("Please enter the amount", 0, Integer.MAX_VALUE);
		} while (!hasBalance(amount));
		LOG.info("returning (%d$) amount".formatted(amount));
		return amount;
	}

	private int getAccountNum() throws FastQuitException, SQLException {
		LOG.info("asking user for account number");
		int toAccountNum = 0;
		do {
			toAccountNum = userInput.inputInt("Please insert reciever's account number", 0, Integer.MAX_VALUE);
			System.out.println();
		} while (!isValid(toAccountNum) || toAccountNum == user.accountNumber());
		return toAccountNum;
	}

	private User getUserByAccNum(int accountNum) throws SQLException {
		LOG.info("getting user by account number");
		try (var ps = connection.prepareStatement("""
				SELECT id, username, first_name, last_name, email, account_number FROM user
				WHERE account_number = ?
				""")) {
			ps.setInt(1, accountNum);
			try (var rs = ps.executeQuery()) {
				rs.next(); // Will always have result
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
			}
		}
	}

	// *********************************
	// Helper methods
	//

	private boolean approve(Transaction transaction) throws FastQuitException {
		LOG.info("approving");
		String message = """

				Transaction details:
				 Reciever's account number : %d
				 Amount : %,d$
				 Fee : %,d$
				 Total : %,d$%s

				Apply""".formatted(transaction.to().accountNumber(), //
				transaction.amount(), transaction.feeAmount(), transaction.total(), //
				transaction.description() == null ? "" //
						: "%n Description : %s".formatted(transaction.description()));
		return userInput.inputBool(message);
	}

	private boolean hasBalance(int amount) throws SQLException {
		LOG.info("checking balance");
		try (var ps = connection.prepareStatement("SELECT balance FROM balance where user_id = ?")) {
			ps.setInt(1, user.id());
			try (var rs = ps.executeQuery()) {
				rs.next();
				return rs.getInt(1) >= amount;
			}
		}
	}

	private boolean isValid(int toAccountNum) throws SQLException {
		LOG.info("validating account number");
		try (var ps = connection.prepareStatement("SELECT COUNT(*) FROM user WHERE account_number = ?")) {
			ps.setInt(1, toAccountNum);
			try (var rs = ps.executeQuery()) {
				rs.next();
				return rs.getInt(1) != 0;
			}
		}
	}

}
