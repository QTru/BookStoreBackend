package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Publisher;
import iuh.fit.bookstorebackend.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

@Service
public class PublisherService extends CrudService<Publisher, String> {
    public PublisherService(PublisherRepository repository) {
        super(repository, "Publisher");
    }
}
