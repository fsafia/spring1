package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.persist.repo.UserRepository;
import ru.geekbrains.persist.repo.UserSpecification;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String allUsers(Model model, @RequestParam(value = "name", required = false) String name,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        logger.info("Filtering by name; {}", name);

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(2), Sort.by(Sort.Direction.DESC, "login"));

        Specification<User> specUser = UserSpecification.trueLiteral();
        if (name != null && !name.isEmpty()) {
           specUser = specUser.and(UserSpecification.loginLike(name)); //к спецификации добавл в конец критерий API
        }

        model.addAttribute("usersPage", userRepository.findAll(specUser, pageRequest)); //при передачи Page на страницу users
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) throws SQLException {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("user with id = " + id + " not found"));
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

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable ("id") Integer id, Model model) throws SQLException {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
