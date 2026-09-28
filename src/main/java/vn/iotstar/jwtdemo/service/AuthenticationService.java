package vn.iotstar.jwtdemo.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.jwtdemo.api.LoginResponse;
import vn.iotstar.jwtdemo.api.LoginUserModel;
import vn.iotstar.jwtdemo.api.RegisterUserModel;
import vn.iotstar.jwtdemo.api.UserResponse;
import vn.iotstar.jwtdemo.security.JwtService;
import vn.iotstar.jwtdemo.user.User;
import vn.iotstar.jwtdemo.user.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponse signup(RegisterUserModel request) {
        String email = request.email().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        User user = new User(
                request.fullName().trim(),
                email,
                passwordEncoder.encode(request.password()));
        try {
            return UserResponse.from(userRepository.saveAndFlush(user));
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalArgumentException("Email đã tồn tại", exception);
        }
    }

    public LoginResponse authenticate(LoginUserModel request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email().trim().toLowerCase(),
                        request.password()));
        User user = (User) authentication.getPrincipal();
        return new LoginResponse(jwtService.generateToken(user), jwtService.getExpirationTime());
    }
}
