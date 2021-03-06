package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.persist.repo.UserRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
public class UserResourse {

    private final UserRepository userRepository;

    @Autowired
    public UserResourse(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/all", produces = "application/json") //paht- это путь, produces - заголовок, указ в каком формате отправим данные- json
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public User findById(@PathVariable("id") int id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("user with id = " + id + " not found"));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")// можно написать 2 одинаковых метода createUser, отличающихся только consumes = принимает xml файлы
    public User createUser(@RequestBody User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Id found in the create request");
        }

        userRepository.save(user);
        return user;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")// можно написать 2 одинаковых метода createUser, отличающихся только consumes = принимает xml файлы
    public User updateUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @DeleteMapping(path = "{id}/id", produces = "application/json")
    public void deleteById(@PathVariable("id") int id) {
        userRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ErrorResponse("BAD_REQUEST", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);//ResponseEntity<T> (T, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> sqlIntegrityConstraintViolationExceptionHandler (SQLIntegrityConstraintViolationException e) { //когда в бд запис некоорректные данные()
        return new ResponseEntity<>(new ErrorResponse("INTERNAL_SERVER_ERROR", e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
