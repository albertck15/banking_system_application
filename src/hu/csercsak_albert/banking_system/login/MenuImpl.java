package hu.csercsak_albert.banking_system.login;

import java.util.ArrayList;
import java.util.List;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.Menu;
import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.main.UserInput;

class MenuImpl implements Menu {

	private final UserInput userInput;
	private final List<MenuOption> options;

	static Menu get(User user, UserInput userInput, OptionTypes... options) {
		return new MenuImpl(user, userInput, options);
	}

	private MenuImpl(User user, UserInput userInput, OptionTypes... options) {
		this.userInput = userInput;
		this.options = getMenuOptions(options);
	}

	private List<MenuOption> getMenuOptions(OptionTypes[] options) {
		List<MenuOption> menuOptions = new ArrayList<>();
		return null;
	}

	@Override
	public MenuOption choose() throws FastQuitException, OperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
