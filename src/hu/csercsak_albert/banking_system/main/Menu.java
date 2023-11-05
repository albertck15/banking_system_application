package hu.csercsak_albert.banking_system.main;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;

public interface Menu {

	MenuOption choose() throws OperationException, FastQuitException;

}
