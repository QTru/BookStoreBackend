package iuh.fit.bookstorebackend.dto;

import iuh.fit.bookstorebackend.models.Account;
import iuh.fit.bookstorebackend.models.enums.Role;

import java.time.LocalDateTime;

public record AccountResponse(
        String id,
        String username,
        String fullName,
        String email,
        String phoneNumber,
        String shippingAddress,
        Role role,
        boolean enabled,
        LocalDateTime creationDate
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUsername(),
                account.getFullName(),
                account.getEmail(),
                account.getPhoneNumber(),
                account.getShippingAddress(),
                account.getRole(),
                account.isEnabled(),
                account.getCreationDate()
        );
    }
}