package ru.geekbrains.dto;

import javax.validation.constraints.NotBlank;

public class UserDto {
    private Integer id;

    @NotBlank   //поле логин не может быть пустым
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String password1;

    public UserDto() {
    }

    public UserDto(Integer id, @NotBlank String login, @NotBlank String password, @NotBlank String password1) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.password1 = password1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }
}
