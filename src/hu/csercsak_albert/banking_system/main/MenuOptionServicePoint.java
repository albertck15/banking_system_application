package hu.csercsak_albert.banking_system.main;

import java.util.List;

public interface MenuOptionServicePoint {

	List<MenuOption> getOptions(OptionTypes... types);

}
