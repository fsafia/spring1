package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.persist.repo.UserRepository;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String allUsers(Model model) throws SQLException {
        List<User> allUsers = userRepository.findAll();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) throws SQLException {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/add")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) throws SQLException {

        if (bindingResult.hasErrors()) {
            return "user";
        }

        //TODO реализовать проверку повторного ввода пароля используя bindingResult.rejectValue()56:06

        userRepository.save(user);

        return "redirect:/users";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable ("id") Integer id, Model model) throws SQLException {
        userRepository.deleteById(id);
        return "redirect:/users";
    }


//    @GetMapping
//    public String allUsers(Model model) throws SQLException {
//        List<User> allUsers = userRepository.getAllUsers();
//        model.addAttribute("users", allUsers);
//        return "users";
//    }
//
//    @GetMapping("/{id}")
//    public String editUser(@PathVariable("id") Integer id, Model model) throws SQLException {
//        User user = userRepository.findById(id);
//        model.addAttribute("user", user);
//        return "user";
//    }
//
//    @GetMapping("/newUser")
//    public String newUser(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        return "user";
//    }
//
//    @PostMapping("/add")
//    public String addUser(@Valid User user, BindingResult bindingResult, Model model) throws SQLException {
//
//        if (bindingResult.hasErrors()) {
//            return "user";
//        }
//
//        //TODO реализовать проверку повторного ввода пароля используя bindingResult.rejectValue()56:06
//
//        if (user.getId() != null) {
//            userRepository.update(user);
//        } else {
//            userRepository.insert(user);
//        }
//
//        return "redirect:/users";
//    }
//
//    @DeleteMapping("/{id}/delete")
//    public String deleteUser(@PathVariable ("id") Integer id, Model model) throws SQLException {
//        userRepository.delete(id);
//        return "redirect:/users";
//    }
}
