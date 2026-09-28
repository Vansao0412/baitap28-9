package vn.iotstar.jwtdemo.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService(
            "a-development-secret-key-with-at-least-32-bytes",
            3_600_000L);

    @Test
    void generatedTokenIsValidForItsUser() {
        UserDetails user = User.withUsername("alice@example.com")
                .password("ignored")
                .roles("USER")
                .build();

        String token = jwtService.generateToken(user);
        String header = new String(Base64.getUrlDecoder().decode(token.split("\\.")[0]), StandardCharsets.UTF_8);

        assertThat(token).isNotBlank();
        assertThat(header).contains("\"alg\":\"HS256\"");
        assertThat(jwtService.extractUsername(token)).isEqualTo("alice@example.com");
        assertThat(jwtService.isTokenValid(token, user)).isTrue();
    }

    @Test
    void generatesSigningKeyWhenNoSecretIsConfigured() {
        JwtService service = new JwtService("", 3_600_000L);
        UserDetails user = User.withUsername("alice@example.com")
                .password("ignored")
                .roles("USER")
                .build();

        String token = service.generateToken(user);

        assertThat(service.isTokenValid(token, user)).isTrue();
    }
}
