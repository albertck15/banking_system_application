package hu.csercsak_albert.banking_system.main;

public record Transaction(User from, User to, int amount) {
}
