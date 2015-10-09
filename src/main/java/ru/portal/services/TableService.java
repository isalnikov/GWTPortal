
package ru.portal.services;

import java.util.Set;
import javax.persistence.metamodel.EntityType;


public interface TableService {
    Set<EntityType<?>> getEntityTypesByAnnotationClass(Class annotationClass);
}
