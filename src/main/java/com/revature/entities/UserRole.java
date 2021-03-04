package com.revature.entities;

public enum UserRole {

    ADMIN("Admin"), BASIC_USER("Basic User"),
    PREMIUM_USER("Premium User"), MODERATOR("Moderator");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public static String valueOf(UserRole role) {
        return role.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
