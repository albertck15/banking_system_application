package hu.csercsak_albert.banking_system.main;

public enum OptionTypes {

	//Login menu
	LOGIN("Login"),
	REGISTER("Register"),
	
	//Main menu after logging in
	WITHDRAW("Withdraw"),
	DEPOSIT("Deposit"),
	SHOW_BALANCE("Show balance");
	

	private final String label;

	OptionTypes(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
