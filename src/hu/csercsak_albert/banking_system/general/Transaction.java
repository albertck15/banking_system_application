package hu.csercsak_albert.banking_system.general;

import java.time.LocalDateTime;

import hu.csercsak_albert.banking_system.main.User;

public record Transaction(User from, User to, int amount, int feeAmount, int total, LocalDateTime time, int newBalance) {
}
