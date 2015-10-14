
package ru.portal.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.metamodel.EntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.portal.entity.User;


public interface TableService {
    Set<EntityType<?>> getEntityTypesByAnnotationClass(Class annotationClass);

    public List<String> getTableOrViewMetaData(String tableOrViewName);

    public  Page<Map<String, String>> findAll(String tableOrViewName, Pageable pageable);
}
