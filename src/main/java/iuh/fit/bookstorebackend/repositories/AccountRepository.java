package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Account> findByUsernameContainingIgnoreCase(String username);

    List<Account> findByEmailContainingIgnoreCase(String email);

    List<Account> findByFullNameContainingIgnoreCase(String fullName);

    @Query("""
        SELECT a FROM Account a
        WHERE LOWER(a.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(a.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(a.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)

    List<Account> search(@Param("keyword") String keyword);
}
