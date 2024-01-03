package hu.csercsak_albert.banking_system.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.Menu;
import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.main.UserInput;
import hu.csercsak_albert.banking_system.menu_options.MenuOptionServicePointImpl;

public class MenuImpl implements Menu {

	private User user;
	private final Connection connection;
	private final UserInput userInput;
	private final String prompt;
	private final List<MenuOption> options;
	private final String menuText;

	private MenuImpl(Builder builder) {
		this.connection = builder.connection;
		this.userInput = builder.userInput;
		this.prompt = builder.prompt;
		this.options = getMenuOptions(builder.options.toArray(new OptionTypes[0]));
		addExitOption();
		this.menuText = compileMenuText();
	}

	private void addExitOption() {
		options.add(new MenuOption() {

			@Override
			public void setup(Connection connection, UserInput userInput, User user) {
			}

			@Override
			public String getLabel() {
				return "Quit";
			}

			@Override
			public void execute() throws SQLException, FastQuitException {
				throw new FastQuitException();
			}
		});
	}

	@Override
	public MenuOption choose() throws FastQuitException, OperationException {
		if (user == null) {
			try {
				user = loginOrRegister();
				setupOptions(); // Setting up options after we know the user
				printWelcome();
				System.out.println();
			} catch (SQLException e) {
				throw new RuntimeException("Login or register has been failed!(%s)".formatted(e.getMessage()));
			}
		}
		return options.get(userInput.chooseInt(menuText + prompt, 1, options.size()) - 1);
	}

	private void setupOptions() {
		for (MenuOption option : options) {
			option.setup(connection, userInput, user);
		}
	}

	private User loginOrRegister() throws SQLException, FastQuitException {
		return new LoginMenu(connection, userInput).loginOrRegister();
	}

	private List<MenuOption> getMenuOptions(OptionTypes[] options) {
		return MenuOptionServicePointImpl.getInstance().getOptions(options);
	}

	private void printWelcome() {
		System.out.printf("%n Welcome %s %s!%n", user.firstName(), user.lastName());
	}

	private String compileMenuText() {
		var menuTextBuilder = new StringBuilder();
		int i = 1;
		for (var option : options) {
			menuTextBuilder.append(" %d. %s%n".formatted(i++, option.getLabel()));
		}
		return menuTextBuilder.append("\n") + "";
	}

	public static class Builder {

		private Connection connection;
		private UserInput userInput;
		private String prompt = "--> ";
		private List<OptionTypes> options = new ArrayList<>();

		public Builder setConnection(Connection connection) {
			this.connection = connection;
			return this;
		}

		public Builder setUserInput(UserInput userInput) {
			this.userInput = userInput;
			return this;
		}

		public Builder setPrompt(String prompt) {
			this.prompt = prompt + " ";
			return this;
		}

		public Builder addOptions(OptionTypes... options) {
			this.options.addAll(List.of(options));
			return this;
		}

		public Builder addOption(OptionTypes option) {
			this.options.add(option);
			return this;
		}

		public Builder resetOptions() {
			this.options.clear();
			return this;
		}

		public Menu build() {
			return new MenuImpl(this);
		}
	}
}
