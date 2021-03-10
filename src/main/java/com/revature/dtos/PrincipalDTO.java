package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.entities.User;

import java.util.Objects;

public class PrincipalDTO {

    private int id;
    private String username;
    private String role;

    @JsonIgnore
    private String token;

    public PrincipalDTO(User user){

        this.id = user.getId();
        this.username = user.getUsername();
        this.role = getRole();
    }

    public PrincipalDTO(int id, String username, String role){

        this.id = id;
        this.username = username;
        this.role = role;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalDTO that = (PrincipalDTO) o;
        return id == that.id && username.equals(that.username) && role.equals(that.role) && token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role, token);
    }

    @Override
    public String toString() {
        return "PrincipalDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
