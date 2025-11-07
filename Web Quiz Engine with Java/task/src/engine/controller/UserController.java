package engine.controller;

import engine.entity.AppUser;
import engine.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "1. User Management", description = "Endpoints for user registration.")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "/register")
    @Operation(summary = "Register a new user",
            description = "Creates a new user account. This is the first step for all users. That way we can access the rest of API.")
    public String register(@Valid @RequestBody AppUser user) {
        service.saveUser(user);
        return "New user " + user.getEmail() + " successfully registered";
    }
}
