package vn.iotstar.jwtdemo.api;

import java.time.Instant;

import vn.iotstar.jwtdemo.user.User;

public record UserResponse(
        Integer id,
        String fullName,
        String email,
        String images,
        Instant createdAt,
        Instant updatedAt,
        String username) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getImages(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername());
    }
}
