package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.persistance.User;
import ru.geekbrains.persistance.UserRepository;

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
        List<User> allUsers = userRepository.getAllUsers();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) throws SQLException {
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

    @PostMapping("/add")
    public String addUser(@Valid UserDto userDto, BindingResult bindingResult, Model model) throws SQLException {

        if (!userDto.getPassword().equals(userDto.getPassword1())) {
            bindingResult.rejectValue("password", "must be equals");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "user";
        }



        //TODO реализовать проверку повторного ввода пароля используя bindingResult.rejectValue()56:06
        User user = new User(userDto.getId(), userDto.getLogin(), userDto.getPassword());

        if (user.getId() != null) {
            userRepository.update(user);
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
