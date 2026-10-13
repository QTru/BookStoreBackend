package iuh.fit.bookstorebackend.controllers;

import iuh.fit.bookstorebackend.models.Publisher;
import iuh.fit.bookstorebackend.services.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Publisher> findAll() {
        return publisherService.findAll();
    }

    @GetMapping("/{id}")
    public Publisher findById(@PathVariable("id") String id) {
        return publisherService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher create(@RequestBody Publisher publisher) {
        return publisherService.save(publisher);
    }

    @PutMapping("/{id}")
    public Publisher update(@PathVariable("id") String id, @RequestBody Publisher publisher) {
        publisher.setId(id);
        return publisherService.update(id, publisher);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        publisherService.deleteById(id);
    }
}
