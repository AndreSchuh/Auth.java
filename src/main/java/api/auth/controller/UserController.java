package api.auth.controller;

import api.auth.dto.UserDto;
import api.auth.entity.User;
import api.auth.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDto userDto) {
        userService.loginUser(userDto);
        return "User logged in successfully!";
    }

    @GetMapping
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return "User with ID " + id + " deleted successfully!";
    }

}
