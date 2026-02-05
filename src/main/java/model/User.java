package model;

public class User {
    private String username;
    private int points;
    private boolean admin;

    // Constructor
    public User(String username, int points, boolean admin) {
        this.username = username;
        this.points = points;
        this.admin = admin;
    }

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
