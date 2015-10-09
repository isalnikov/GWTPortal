
package ru.portal.services;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.portal.repositories.AbstractRepository;


/**
 *
 * @author Igor Salnikov 
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractServiceImpl<T, ID extends Serializable> implements AbstractService<T, ID>{
    
    private final AbstractRepository<T , ID > repository;
    
    protected AbstractServiceImpl(AbstractRepository<T , ID > repository){
        this.repository = repository;
        
    }

    @Override
    public long count() {
        return repository.count();
    }
    
    @Override
    public long count(Specification<T> s) {
        return repository.count(s);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteInBatch(Iterable<T> itrbl) {
        repository.deleteInBatch(itrbl);
    }

    @Override
    public T findOne(ID id) {
        return repository.findOne(id);
    }

    @Override
    public Page<T> findAll(Specification<T> s, Pageable pgbl) {
       return repository.findAll(s, pgbl);
    }

    @Override
    public List<T> findAll(Specification<T> s, Sort sort) {
        return repository.findAll(s, sort);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends T> S save(S s) {
        return repository.saveAndFlush(s);
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> itrbl) {
       return repository.save(itrbl);
    }

    @Override
    public Page<T>  findAll(Pageable pgbl) {
        return repository.findAll(pgbl);
    }

   
}
