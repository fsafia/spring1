package ru.geekbrains.persist.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String role;

    @ManyToMany(mappedBy = "roles")
//    @JoinTable(name = "users_roles",
//                joinColumns = @JoinColumn(name = "role_id"),
//                inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

}
