package com.revature.entities;

public enum UserRole {

    EMPTY(""), ADMIN("Admin"), MODERATOR("Moderator"),
    BASIC_USER("Basic User"), PREMIUM_USER("Premium User");

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
