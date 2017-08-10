package com.example.gilian.bars_coop.Entity;

/**
 * Created by Gilian on 06/08/2017.
 */

public class User
{
    private String login;
    private String password;
    private String userName;

    public User()
    {

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
