package ru.geekbrains.persistance;

import javax.validation.constraints.NotBlank;

public class User {

    private Integer id;

    @NotBlank   //поле логин не может быть пустым
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String password1;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User() {}

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
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