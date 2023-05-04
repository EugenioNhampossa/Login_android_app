package mz.ac.isutc.i31.logintutorial.Model;

import java.util.Objects;

public class User {
    private  int id;
    private String username;
    private String cellNumber;
    private String password;
    private String tempToken;

    public User(int id, String username, String cellNumber, String password, String tempToken) {
        this.id = id;
        this.username = username;
        this.cellNumber = cellNumber;
        this.password = password;
        this.tempToken = tempToken;
    }

    public User(int id, String username, String cellNumber, String password) {
        this.id = id;
        this.username = username;
        this.cellNumber = cellNumber;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTempToken() {
        return tempToken;
    }

    public void setTempToken(String tempToken) {
        this.tempToken = tempToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username) && cellNumber.equals(user.cellNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, cellNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", cellNumber='" + cellNumber + '\'' +
                ", password='" + password + '\'' +
                ", tempToken='" + tempToken + '\'' +
                '}';
    }
}
