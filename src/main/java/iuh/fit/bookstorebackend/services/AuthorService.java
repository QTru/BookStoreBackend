package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Author;
import iuh.fit.bookstorebackend.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends CrudService<Author, String> {
    public AuthorService(AuthorRepository repository) {
        super(repository, "Author");
    }
}
