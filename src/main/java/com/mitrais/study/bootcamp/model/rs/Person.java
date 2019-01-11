package com.mitrais.study.bootcamp.model.rs;

import java.io.Serializable;

public class Person implements Serializable {

    private String username;
    private String name;
    private String rawPassword;

    public Person() {
        super();
    }

    public Person copy(final com.mitrais.study.bootcamp.model.jpa.Person jpa) {
        setName(jpa.getName());
        setUsername(jpa.getUsername());
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
