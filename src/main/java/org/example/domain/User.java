package org.example.domain;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "susers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "user_guid")
    private String guid;
    @Column(name = "user_name")
    private String name;

    public User() {
    }
    public User(int id, String guid, String name) {
        this.id = id;
        this.guid = guid;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(guid, user.guid) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guid, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
