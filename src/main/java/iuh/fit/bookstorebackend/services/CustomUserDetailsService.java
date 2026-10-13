package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Account;
import iuh.fit.bookstorebackend.models.enums.Role;
import iuh.fit.bookstorebackend.repositories.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Account not found"));

        String[] roles = account.getRole() == Role.ADMIN
                ? new String[]{"ADMIN", "CUSTOMER"}
                : new String[]{"CUSTOMER"};

        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .disabled(!account.isEnabled())
                .roles(roles)
                .build();
    }
}