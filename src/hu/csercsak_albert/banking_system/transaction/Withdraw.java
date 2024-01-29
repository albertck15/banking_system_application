package hu.csercsak_albert.banking_system.transaction;

import java.time.LocalDateTime;

import hu.csercsak_albert.banking_system.main.User;

public record Withdraw(User user, int amount, int newBalance, LocalDateTime date) {
}
