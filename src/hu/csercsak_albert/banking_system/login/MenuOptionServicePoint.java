package hu.csercsak_albert.banking_system.login;

import java.util.List;

import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.OptionTypes;

public interface MenuOptionServicePoint {

	List<MenuOption> getOptions(OptionTypes... types);

}
