package vn.iotstar.jwtdemo.web;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.iotstar.jwtdemo.api.LoginResponse;
import vn.iotstar.jwtdemo.api.LoginUserModel;
import vn.iotstar.jwtdemo.api.RegisterUserModel;
import vn.iotstar.jwtdemo.api.UserResponse;
import vn.iotstar.jwtdemo.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public UserResponse signup(@Valid @RequestBody RegisterUserModel request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginUserModel request) {
        return authenticationService.authenticate(request);
    }
}
