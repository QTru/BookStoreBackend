package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Category;
import iuh.fit.bookstorebackend.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudService<Category, String> {
    public CategoryService(CategoryRepository repository) {
        super(repository, "Category");
    }
}
