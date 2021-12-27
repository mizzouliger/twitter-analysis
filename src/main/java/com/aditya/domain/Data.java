package com.aditya.twitter;

import java.sql.Timestamp;
import java.util.Objects;

public class Data {
    String name;
    String id;
    String description;
    String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(name, data.name) && Objects.equals(id, data.id) && Objects.equals(description, data.description) && Objects.equals(username, data.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, description, username);
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
