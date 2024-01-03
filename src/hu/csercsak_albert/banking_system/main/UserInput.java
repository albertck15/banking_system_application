package hu.csercsak_albert.banking_system.main;

import java.util.Scanner;

import hu.csercsak_albert.banking_system.general.FastQuitException;

public class UserInput implements AutoCloseable {

	private final Scanner scanner;

	public UserInput() {
		scanner = new Scanner(System.in);
	}

	public String inputText(String prompt) throws FastQuitException {
		for (;;) {
			System.out.print(prompt + " : ");
			String input = scanner.nextLine().strip();
			checkQuit(input);
			if (!input.isBlank()) {
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
				String inputText = scanner.nextLine().strip();
				checkQuit(inputText);
				int input = Integer.parseInt(inputText);
				if (input >= min && input <= max) {
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
					return input.startsWith("y");
				}
			} catch (NumberFormatException e) {
			}
		}
	}

	private void checkQuit(String input) throws FastQuitException {
		if (input.toLowerCase().equals("quit") || input.toLowerCase().equals("exit")) {
			throw new FastQuitException();
		}
	}

	@Override
	public void close() {
		scanner.close();
	}
}
