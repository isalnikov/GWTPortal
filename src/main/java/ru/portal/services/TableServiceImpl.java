/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.services;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tableService")
@Transactional
public class TableServiceImpl implements TableService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<EntityType<?>> getEntityTypesByAnnotationClass(Class annotationClass) {
        Set<EntityType<?>> result = new HashSet<>();
        Set<EntityType<?>> set = em.getEntityManagerFactory().getMetamodel().getEntities();
        for (EntityType<?> entityType : set) {
              if(entityType.getBindableJavaType().getAnnotation(annotationClass) != null){
                  result.add(entityType);
              }
        }
        return result;
        
    }

}
