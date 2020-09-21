package ru.geekbrains.persist.entity;

import javax.validation.constraints.NotBlank;

public class User {

    private Integer id;

    @NotBlank   //поле логин не может быть пустым
    private String login;

    @NotBlank
    private String password;

    private String matchingPassword;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User() {}

    public String getPassword1() {
        return matchingPassword;
    }

    public void setPassword1(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
