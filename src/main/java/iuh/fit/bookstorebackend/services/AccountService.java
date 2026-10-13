package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.dto.UpdateAccountRequest;
import iuh.fit.bookstorebackend.models.Account;
import iuh.fit.bookstorebackend.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public Account findByUsername(String username){
        return accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("Account not found"));
    }

    public List<Account> search(String keyword){
        return accountRepository.search(keyword);
    }

    @Transactional
    public Account updateCurrentAccount(String username, UpdateAccountRequest request){
        Account account = findByUsername(username);

        return updateInformation(account.getId(), request);
    }

    @Transactional
    public void disableAccount(String id) {
        Account account = findById(id);
        account.setEnabled(false);
        accountRepository.save(account);
    }
}