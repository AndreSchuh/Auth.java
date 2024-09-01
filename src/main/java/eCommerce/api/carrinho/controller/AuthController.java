package eCommerce.api.carrinho.controller;

import eCommerce.api.carrinho.dto.LoginDto;
import eCommerce.api.carrinho.dto.RegisterDto;
import eCommerce.api.carrinho.entity.User;
import eCommerce.api.carrinho.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        authService.registerUser(registerDto);
        return ResponseEntity.ok("Registration successful");
    }

    @GetMapping
    public List<User> listAllUsers() {
        return authService.listAllUsers();
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable UUID id) {
        authService.deleteUserById(id);
        return "User with ID " + id + " deleted successfully!";
    }

    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
    public String authenticateUserFromForm(@RequestParam("email") String email,
                                           @RequestParam("password") String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/index"; // Redireciona para a página inicial após o login
    }

    @PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded")
    public String registerUserFromForm(@RequestParam("email") String email,
                                       @RequestParam("password") String password) {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail(email);
        registerDto.setPassword(password);

        authService.registerUser(registerDto);
        return "redirect:/login"; // Redireciona para a página de login após o registro
    }
}

