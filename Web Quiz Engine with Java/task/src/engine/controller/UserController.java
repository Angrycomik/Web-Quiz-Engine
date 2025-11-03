package engine.controller;

import engine.entity.AppUser;
import engine.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "/register")
    public String register(@Valid @RequestBody AppUser user) {
        service.saveUser(user);
        return "New user " + user.getEmail() + " successfully registered";
    }
}
