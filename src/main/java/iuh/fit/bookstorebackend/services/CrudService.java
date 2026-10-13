package iuh.fit.bookstorebackend.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public abstract class CrudService<T, ID> {
    private final JpaRepository<T, ID> repository;
    private final String entityName;

    protected CrudService(JpaRepository<T, ID> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found: " + id));
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public T update(ID id, T entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(entityName + " not found: " + id);
        }
        return repository.save(entity);
    }

    @Transactional
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(entityName + " not found: " + id);
        }
        repository.deleteById(id);
    }
}
