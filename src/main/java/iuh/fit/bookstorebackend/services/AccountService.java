package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Account;
import iuh.fit.bookstorebackend.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends CrudService<Account, String> {
    public AccountService(AccountRepository repository) {
        super(repository, "Account");
    }
}
