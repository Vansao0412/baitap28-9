package vn.iotstar.jwtdemo.api;

public record LoginResponse(String token, long expiresIn) {
}
