package hu.csercsak_albert.banking_system.menu_options;

import java.util.ArrayList;
import java.util.List;

import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.menu.MenuOptionServicePoint;

public class MenuOptionServicePointImpl implements MenuOptionServicePoint {

	private static final MenuOptionServicePointImpl INSTANCE = new MenuOptionServicePointImpl();

	private MenuOptionServicePointImpl() {
	}

	public static MenuOptionServicePoint getInstance() {
		return INSTANCE;
	}

	@Override
	public List<MenuOption> getOptions(OptionTypes... types) {
		List<MenuOption> options = new ArrayList<>();
		for (var type : types) {
			options.add(getOption(type));
		}
		return options;
	}

	private MenuOption getOption(OptionTypes type) {
		return switch (type) {
			case WITHDRAW -> new MOWithdraw(type);
			case SHOW_BALANCE -> new MOShowBalance(type);
			case DEPOSIT -> new MODeposit(type);
			default -> new MONotImplemented(type);
		};
	}

}
