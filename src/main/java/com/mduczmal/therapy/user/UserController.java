package com.mduczmal.therapy.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v2/logged")
    public Map<String, Boolean> isLogged() {
        User user = userService.getCurrentUser();
        if (user != null) {
            return Map.of("logged", true);
        } else {
            return Map.of("logged", false);
        }
    }

    @GetMapping("/v2/user")
    public Map<String, UUID> user() {
        User user = userService.getCurrentUser();
        if (user != null) {
            return Map.of("user", user.getId());
        } else {
            return Map.of("user", UUID.fromString(""));
        }
    }

}
