package com.mitrais.study.bootcamp.model.rs;

import java.io.Serializable;

/**
 * @author Rudi_W144
 */
public class UserInfo implements Serializable {

    private boolean authenticated;

    private String username;

    private String rawPassword;

    public UserInfo() {
        super();
    }

    public UserInfo(boolean authenticated, String username) {
        super();
        this.authenticated = authenticated;
        this.username = username;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "authenticated=" + authenticated +
                ", username='" + username + '\'' +
                '}';
    }
}
