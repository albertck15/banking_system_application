package hu.csercsak_albert.banking_system.main;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.general.FastQuitException;

public class UserInput implements AutoCloseable {

	private static final Logger LOG = LogManager.getLogger(UserInput.class);

	private final Scanner scanner;

	public UserInput() {
		scanner = new Scanner(System.in);
		LOG.info("Scanner initialized");
	}

	public String inputText(String prompt) throws FastQuitException {
		for (;;) {
			System.out.print(prompt + " : ");
			String input = scanner.nextLine().strip();
			checkQuit(input);
			if (!input.isBlank()) {
				LOG.info("returning input from inputText");
				return input;
			}
		}
	}

	public int chooseInt(String prompt, int min, int max) throws FastQuitException {
		for (;;) {
			System.out.print(prompt);
			try {
				String inputText = scanner.nextLine().strip();
				checkQuit(inputText);
				int input = Integer.parseInt(inputText);
				if (input >= min && input <= max) {
					LOG.info("returning input from chooseInt");
					return input;
				}
			} catch (NumberFormatException e) {
			}
		}
	}

	public int inputInt(String prompt, int min, int max) throws FastQuitException {
		for (;;) {
			System.out.print(prompt + " : ");
			try {
				String inputText = scanner.nextLine().trim();
				checkQuit(inputText);
				int input = Integer.parseInt(inputText);
				if (input >= min && input <= max) {
					LOG.info("returning input from inputInt");
					return input;
				}
			} catch (NumberFormatException e) {
			}
		}
	}

	public boolean inputBool(String prompt) throws FastQuitException {
		for (;;) {
			System.out.print(prompt + " (yes/no) ? ");
			try {
				String input = scanner.nextLine().strip();
				checkQuit(input);
				if (!input.isBlank()) {
					LOG.info("returning input from inputBool");
					return input.startsWith("y");
				}
			} catch (NumberFormatException e) {
			}
		}
	}

	private void checkQuit(String input) throws FastQuitException {
		if (input.toLowerCase().equals("quit") || input.toLowerCase().equals("exit")) {
			LOG.info("User inserted quit/exit");
			throw new FastQuitException();
		}
	}

	@Override
	public void close() {
		scanner.close();
		LOG.info("Scanner closed inside UserInput");
	}
}
