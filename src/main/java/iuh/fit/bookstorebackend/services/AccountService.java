package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.dto.UpdateAccountRequest;
import iuh.fit.bookstorebackend.models.Account;
import iuh.fit.bookstorebackend.repositories.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService extends CrudService<Account, String> {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository repository) {
        super(repository, "Account");
        this.accountRepository = repository;
    }

    @Transactional
    public Account updateInformation(String id, UpdateAccountRequest request) {
        Account account = findById(id);

        if (!request.getEmail().equals(account.getEmail())
                && accountRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email is already in use");
        }

        if (!request.getPhoneNumber().equals(account.getPhoneNumber())
                && accountRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Phone number is already in use");
        }

        account.setFullName(request.getFullName());
        account.setEmail(request.getEmail());
        account.setPhoneNumber(request.getPhoneNumber());
        account.setShippingAddress(request.getShippingAddress());

        return accountRepository.save(account);
    }
}