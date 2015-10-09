

package ru.portal.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * @param <T>
 * @param <ID>
 */
public interface AbstractService<T, ID> {

    public long count();
    
    public long count(Specification<T> s);
    
    public void delete(T t);
    
    public void deleteInBatch(Iterable<T> itrbl);
    
    public T findOne(ID id);
     
    public Page<T> findAll(Specification<T> s, Pageable pgbl);
    
    public List<T> findAll(Specification<T> s, Sort sort);
    
    public Page<T> findAll(Pageable pgbl);
    
    public List<T> findAll();
    
    public void flush();
    
    public <S extends T> S save (S s);
    
    public <S extends T> List<S> save(Iterable<S> itrbl);

}
