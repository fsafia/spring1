package ru.geekbrains.persist.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank   //поле логин не может быть пустым
    private String login;

    @NotBlank
    @Column(length = 512)
    private String password; //char[]

    @ManyToMany
    @JoinTable(name = "users_roles",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @Transient  //это поле не надо сохранять в базу данных
    private String matchingPassword;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User() {}

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
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
