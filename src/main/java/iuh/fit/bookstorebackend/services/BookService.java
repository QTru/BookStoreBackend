package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Book;
import iuh.fit.bookstorebackend.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService extends CrudService<Book, String> {
    public BookService(BookRepository repository) {
        super(repository, "Book");
    }
}
