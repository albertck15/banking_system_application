package hu.csercsak_albert.banking_system.menu_options;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.menu.MenuOptionServicePoint;

public class MenuOptionServicePointImpl implements MenuOptionServicePoint {

	private static final Logger LOG = LogManager.getLogger(MenuOptionServicePointImpl.class);

	private static final MenuOptionServicePointImpl INSTANCE = new MenuOptionServicePointImpl();

	private MenuOptionServicePointImpl() {
		LOG.info("initialized");
	}

	public static MenuOptionServicePoint getInstance() {
		LOG.info("returning singleton");
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
		LOG.info("returning class for the type (%s)".formatted(type.getLabel()));
		return switch (type) {
			case WITHDRAW -> new MOWithdraw(type);
			case SHOW_DETAILS -> new MOShowDetails(type);
			case DEPOSIT -> new MODeposit(type);
			case TRANSFER -> new MOTransfer(type);
			case SEARCH_USERS -> new MOSearchUsers(type);
			default -> new MONotImplemented(type);
		};
	}

}
