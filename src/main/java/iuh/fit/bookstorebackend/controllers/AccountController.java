package iuh.fit.bookstorebackend.controllers;

import iuh.fit.bookstorebackend.dto.AccountResponse;
import iuh.fit.bookstorebackend.dto.UpdateAccountRequest;
import iuh.fit.bookstorebackend.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll().stream()
                .map(AccountResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable("id") String id) {
        return AccountResponse.from(accountService.findById(id));
    }

    @PutMapping("/{id}")
    public AccountResponse update(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateAccountRequest request
    ) {
        return AccountResponse.from(
                accountService.updateInformation(id, request));
    }

    @GetMapping("/me")
    public AccountResponse getCurrentAccount(Authentication authentication){
        return AccountResponse.from(
                accountService.findByUsername(authentication.getName())
        );
    }

    @PutMapping("/me")
    public AccountResponse updateCurrentAccount(Authentication authentication, @Valid @RequestBody UpdateAccountRequest request){
        return  AccountResponse.from(
                accountService.updateCurrentAccount(authentication.getName(), request)
        );
    }

    @GetMapping("/search")
    public List<AccountResponse> search(@RequestParam String keyword) {
        return accountService.search(keyword)
                .stream()
                .map(AccountResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        accountService.disableAccount(id);
    }
}