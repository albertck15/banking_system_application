package hu.csercsak_albert.banking_system.general;

import hu.csercsak_albert.banking_system.main.User;

public record Transaction(User from, User to, int amount) {
}
