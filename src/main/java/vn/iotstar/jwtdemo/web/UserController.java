package vn.iotstar.jwtdemo.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.iotstar.jwtdemo.api.UserResponse;
import vn.iotstar.jwtdemo.service.UserService;
import vn.iotstar.jwtdemo.user.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal User user) {
        return UserResponse.from(user);
    }

    @GetMapping
    public List<UserResponse> allUsers() {
        return userService.allUsers();
    }
}
