package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
