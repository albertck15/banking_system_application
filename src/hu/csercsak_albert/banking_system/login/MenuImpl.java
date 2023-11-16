package hu.csercsak_albert.banking_system.login;

import java.util.List;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.Menu;
import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.main.UserInput;
import hu.csercsak_albert.banking_system.menu_options.MenuOptionServicePointImpl;

class MenuImpl implements Menu {

	private final UserInput userInput;
	private final List<MenuOption> options;
	private final String menuText;

	static Menu get(User user, UserInput userInput, OptionTypes... options) {
		return new MenuImpl(user, userInput, options);
	}

	private MenuImpl(User user, UserInput userInput, OptionTypes... options) {
		this.userInput = userInput;
		this.options = getMenuOptions(options);
		this.menuText = compileMenuText();
	}

	@Override
	public MenuOption choose() throws FastQuitException, OperationException {
		return options.get(userInput.inputInt(menuText + "-->", 1, options.size()) - 1);
	}

	private List<MenuOption> getMenuOptions(OptionTypes[] options) {
		return MenuOptionServicePointImpl.getInstance().getOptions(options);
	}

	private String compileMenuText() {
		var menuTextBuilder = new StringBuilder();
		int i = 1;
		for (var option : options) {
			menuTextBuilder.append(" %d. %s%n".formatted(i++, option.getLabel()));
		}
		return menuTextBuilder + "";
	}

}
