package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
}
