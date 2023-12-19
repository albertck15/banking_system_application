package hu.csercsak_albert.banking_system.transaction;

import java.sql.Connection;

import hu.csercsak_albert.banking_system.main.Transaction;
import hu.csercsak_albert.banking_system.main.TransactionHandler;

class TransactionHandlerImpl implements TransactionHandler {

	@Override
	public void makeTransaction(Connection connection, Transaction transaction) {
		if (transaction.to() == null) { // means withdrawing 
			makeWithdraw(connection, transaction);
		} else {
			doTransaction(connection, transaction);
		}
	}

	private void makeWithdraw(Connection connection, Transaction transaction) {
		
	}

	private void doTransaction(Connection connection, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

}
