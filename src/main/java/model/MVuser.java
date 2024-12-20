package model;

public class MVuser {

    private int id = -1;
    private String login = "";
    private String password = "";
    private int privileges = -1;
    // -1 user not logged in
    // 1 user logged in
    // 2 administrator



    public int getId() { return id; }

    public void setId(int id) { this.id = id; }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getPrivileges () {
        return privileges;
    }

    public void setPrivileges (int privileges) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return "DGuser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", privileges=" + privileges +
                '}';
    }
}
