package com.mtp.Model;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;


@Table(name="user")
public class User extends SugarRecord {

    @Column(name="userId")
    private String userId;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="socialMediaToken")
    private String socialMediaToken;

    public User() { }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSocialMediaToken() {
        return socialMediaToken;
    }

    public void setSocialMediaToken(String socialMediaToken) {
        this.socialMediaToken = socialMediaToken;
    }
}
