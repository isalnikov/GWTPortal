/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.portal.interfaces.PortalColumn;
import ru.portal.interfaces.PortalTable;

@Service
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

    @Override
    public List<String> getTableOrViewMetaData(String tableOrViewName) {
        List<String> result = new ArrayList<>();
         Set<EntityType<?>> set = em.getEntityManagerFactory().getMetamodel().getEntities();
         for (EntityType<?> entityType : set) {
            if(entityType.getBindableJavaType().getAnnotation(PortalTable.class) != null){
                if(entityType.getBindableJavaType().getName().equals(tableOrViewName)){
                       Field[] fields = entityType.getBindableJavaType().getDeclaredFields();
                       for (Field field : fields) {
                         if(field.getAnnotation(Column.class)!=null){
                             result.add(field.getAnnotation(Column.class).name());
                         }
                         if(field.getAnnotation(Id.class)!=null){
                             result.add("id");
                         }
                    }
                       
                }
            }
        }
        return result;
        
        
    }

}
