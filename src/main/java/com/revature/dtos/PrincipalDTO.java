package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.entities.User;
import com.revature.entities.UserRole;

import java.util.Objects;

/**
 * DTO for Sending User data back to client after a successful login.
 */
public class PrincipalDTO {

    private int id;
    private String username;
    private String userRole;

    @JsonIgnore
    private String token;

    public PrincipalDTO(final User user){

        this.id = user.getId();
        this.username = user.getUsername();
        this.userRole = UserRole.valueOf(user.getUserRole());
    }

    public PrincipalDTO(final int id,final String username, final String userRole){

        this.id = id;
        this.username = username;
        this.userRole = userRole;

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PrincipalDTO that = (PrincipalDTO) o;
        return id == that.id && username.equals(that.username) && userRole.equals(that.userRole) && token.equals(that.token);
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

    public void setId(final int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(final String userRole) {
        this.userRole = userRole;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }



}
