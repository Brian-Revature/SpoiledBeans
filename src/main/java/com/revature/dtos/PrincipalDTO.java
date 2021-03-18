package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.entities.User;
import com.revature.entities.UserRole;

import java.util.Objects;

public class PrincipalDTO {

    private int id;
    private String username;
    private String userRole;

    @JsonIgnore
    private String token;

    public PrincipalDTO(User user){

        this.id = user.getId();
        this.username = user.getUsername();
        this.userRole = UserRole.valueOf(user.getUserRole());
    }

    public PrincipalDTO(int id, String username, String userRole){

        this.id = id;
        this.username = username;
        this.userRole = userRole;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalDTO that = (PrincipalDTO) o;
        return id == that.id && username.equals(that.username) && userRole.equals(that.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userRole, token);
    }

    @Override
    public String toString() {
        return "PrincipalDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + userRole + '\'' +
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
