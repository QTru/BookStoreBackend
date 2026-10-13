package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
