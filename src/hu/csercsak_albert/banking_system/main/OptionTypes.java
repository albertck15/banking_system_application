package hu.csercsak_albert.banking_system.main;

public enum OptionTypes {

	// Main menu after logging in
	SHOW_DETAILS("Show details"),
	SEARCH_USERS("Search users"),
	WITHDRAW("Withdraw"),
	DEPOSIT("Deposit"),
	TRANSFER("Transfer money"),
	TRANSACTION_HISTORY("Transaction history");

	private final String label;

	OptionTypes(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
