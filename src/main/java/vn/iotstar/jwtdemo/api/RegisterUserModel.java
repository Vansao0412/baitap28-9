package vn.iotstar.jwtdemo.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserModel(
        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không hợp lệ")
        String email,

        @NotBlank(message = "Mật khẩu không được để trống")
        @Size(min = 6, max = 100, message = "Mật khẩu phải có từ 6 đến 100 ký tự")
        String password,

        @NotBlank(message = "Họ tên không được để trống")
        @Size(max = 50, message = "Họ tên không được quá 50 ký tự")
        String fullName) {
}
