package hu.csercsak_albert.banking_system.transaction;

import java.time.LocalDateTime;

import hu.csercsak_albert.banking_system.main.User;

public record Transaction(User from, User to, int amount, int feeAmount, // Amount contains +/- sign
		int total, LocalDateTime date, int newBalance, String description) {
}
