package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
    private Integer userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;

    public User(Integer userid, String username, String salt, String password, String firstname, String lastname) {
        this.userid = userid;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getUserid() { return userid;}
    public void setUserid(final Integer userid) { this.userid = userid; }

    public String getUsername() { return username;}
    public void setUsername(final String username) { this.username = username;}

    public String getSalt() { return salt;}
    public void setSalt(final String salt) { this.salt = salt;}

    public String getPassword() { return password;}
    public void setPassword(final String password) { this.password = password;}

    public String getFirstname() { return firstname;}
    public void setFirstname(final String firstname) { this.firstname = firstname;}

    public String getLastname() { return lastname;}
    public void setLastname(final String lastname) { this.lastname = lastname;}
}