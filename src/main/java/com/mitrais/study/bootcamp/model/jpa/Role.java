package com.mitrais.study.bootcamp.model.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rudi_W144
 */
@Entity(name = "Role")
@Table(name = "role",
        uniqueConstraints = {@UniqueConstraint(name = "role_name_key", columnNames = "name")},
        indexes = {
            @Index(name = "role_title_index", columnList = "title")
        }
    )
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private String title;

    @Basic(optional = true)
    @Column(nullable = true)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    @Version
    private long version;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                '}';
    }
}
