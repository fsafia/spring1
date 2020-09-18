package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persistance.User;
import ru.geekbrains.persistance.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String allUsers(Model model) throws SQLException {
        List<User> allUsers = userRepository.getAllUsers();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) throws SQLException {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(User user) throws SQLException {
        if (user.getId() != null) {
            userRepository.updateById(user.getLogin(), user.getId());
        } else {
            userRepository.insert(user);
        }
        return "redirect:/users";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable ("id") Integer id, Model model) throws SQLException {
        userRepository.delete(id);
        return "redirect:/users";
    }
}
