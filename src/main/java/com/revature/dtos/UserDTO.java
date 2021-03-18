package com.revature.dtos;


import java.util.Objects;

/**
 * DTO for transferring User data between client and backend.
 */
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private String userRole;


    public UserDTO() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(final String bio) {
        this.bio = bio;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(final String userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserDTO userDTO = (UserDTO) o;
        return Objects.equals(username, userDTO.username) && Objects.equals(password, userDTO.password) && Objects.equals(email, userDTO.email) && Objects.equals(firstName, userDTO.firstName) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(bio, userDTO.bio) && Objects.equals(userRole, userDTO.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, firstName, lastName, bio, userRole);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
