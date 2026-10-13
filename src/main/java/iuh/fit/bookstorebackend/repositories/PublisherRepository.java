package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
}
