package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
