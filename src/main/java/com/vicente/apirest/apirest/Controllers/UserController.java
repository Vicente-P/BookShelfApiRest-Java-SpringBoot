package com.vicente.apirest.apirest.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.apirest.apirest.Entities.User;
import com.vicente.apirest.apirest.Repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("The user with the ID: " + userId + " was not found"));
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("The user with the ID: " + userId + " was not found"));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());

        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("The user with the ID: " + userId + " was not found"));

        userRepository.delete(user);
        return "The user with the ID: "+ userId +" was successfully deleted";
    }
}
