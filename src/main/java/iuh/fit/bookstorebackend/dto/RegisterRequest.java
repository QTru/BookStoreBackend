package iuh.fit.bookstorebackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50,
            message = "Username must contain 3 to 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64,
            message = "Password must contain 8 to 64 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(max = 100,
            message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    @Size(max = 100,
            message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20,
            message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Shipping address is required")
    @Size(max = 255,
            message = "Shipping address must not exceed 255 characters")
    private String shippingAddress;
}