package hu.csercsak_albert.banking_system.main;

import java.util.Scanner;

public class UserInput implements AutoCloseable {

	private final Scanner scanner;

	public UserInput() {
		scanner = new Scanner(System.in);
	}

	public String inputText(String prompt) {
		for (;;) {
			System.out.print(prompt + " : ");
			String input = scanner.nextLine().strip();
			if (!input.isBlank()) {
				return input;
			}
		}
	}

	public int inputInt(String prompt, int min, int max) {
		for (;;) {
			System.out.print(prompt + " : ");
			try {
				int input = Integer.parseInt(scanner.nextLine().strip());
				if (input >= min && input <= max) {
					return input;
				}
			} catch (NumberFormatException e) {
			}
		}
	}

	public boolean inputBool(String prompt) {
		for (;;) {
			System.out.print(prompt + " (yes/no) ? ");
			try {
				String input = scanner.nextLine().strip();
				if (!input.isBlank()) {
					return input.startsWith("");
				}
			} catch (NumberFormatException e) {
			}
		}
	}

	@Override
	public void close() {
		scanner.close();
	}
}
