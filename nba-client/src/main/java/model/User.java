package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private Long id;
    private String username;
    private String password;

    @JsonProperty("puntos")   // ← Mapea "puntos" del backend a "points" del cliente
    private int points;

    private boolean admin;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
