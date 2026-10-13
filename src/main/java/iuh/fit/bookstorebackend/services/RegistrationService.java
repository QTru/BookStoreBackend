package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.dto.AccountResponse;
import iuh.fit.bookstorebackend.dto.RegisterRequest;
import iuh.fit.bookstorebackend.models.Account;
import iuh.fit.bookstorebackend.models.enums.Role;
import iuh.fit.bookstorebackend.repositories.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

@Service
public class RegistrationService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AccountResponse register(RegisterRequest request) {
        String username = request.getUsername().trim();
        String fullName = request.getFullName().trim();
        String email = request.getEmail().trim();
        String phoneNumber = request.getPhoneNumber().trim();
        String shippingAddress = request.getShippingAddress().trim();

        if (username.length() < 3) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Username must contain at least 3 characters");
        }

        if (request.getPassword()
                .getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password must not exceed 72 UTF-8 bytes");
        }

        if (accountRepository.existsByUsername(username)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username is already in use");
        }

        if (accountRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already in use");
        }

        if (accountRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number is already in use");
        }

        Account account = Account.builder()
                .username(username)
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(fullName)
                .email(email)
                .phoneNumber(phoneNumber)
                .shippingAddress(shippingAddress)
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();

        return AccountResponse.from(accountRepository.save(account));
    }
}