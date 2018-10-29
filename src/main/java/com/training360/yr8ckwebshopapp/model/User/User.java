package com.training360.yr8ckwebshopapp.model.User;

public class User {

    private long id;
    private String userName;
    private String password;
    private UserRole userRole;
    private String familyName;
    private String givenName;
    private UserStatus userStatus;

    public User() {
    }

    public User(String password, String familyName, String givenName) {
        this.password = password;
        this.familyName = familyName;
        this.givenName = givenName;

    }

    public User(String userName, String password, String familyName, String givenName) {
        this.userName = userName;
        this.password = password;
        this.familyName = familyName;
        this.givenName = givenName;
    }

    public User(long id, String userName, UserRole userRole, String familyName, String givenName, UserStatus userStatus) {
        this.id = id;
        this.userName = userName;
        this.password = null;
        this.userRole = userRole;
        this.familyName = familyName;
        this.givenName = givenName;
        this.userStatus = userStatus;

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }
}
