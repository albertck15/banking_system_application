package hu.csercsak_albert.banking_system.menu_options;

import java.util.ArrayList;
import java.util.List;

import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.MenuOptionServicePoint;
import hu.csercsak_albert.banking_system.main.OptionTypes;

class MenuOptionServicePointImpl implements MenuOptionServicePoint {

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
			default -> new MONotImplemented(type);
		};
	}

}
