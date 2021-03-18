package com.revature.entities;

/**
 * Enum which represents a user's role on the site.
 */
public enum UserRole {

    EMPTY(""), ADMIN("Admin"), MODERATOR("Moderator"),
    BASIC_USER("Basic User"), PREMIUM_USER("Premium User");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public static String valueOf(final UserRole role) {
        return role.name;
    }



    @Override
    public String toString() {
        return name;
    }
}
