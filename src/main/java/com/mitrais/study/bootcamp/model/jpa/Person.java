package com.mitrais.study.bootcamp.model.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rudi_W144
 */
@Entity(name = "Person")
@Table(name = "person",
        uniqueConstraints = {@UniqueConstraint(name = "person_username_key", columnNames = "username")},
        indexes = {@Index(name = "person_name_index", columnList = "name")}
    )
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = true)
    @Column(nullable = true)
    private String password;

    @Basic(optional = false)
    @Column(nullable = false)
    private long version;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private Set<Long> roleIds = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", version=" + version +
                '}';
    }
}
