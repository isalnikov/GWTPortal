/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.portal.entity.User;
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
            if (entityType.getBindableJavaType().getAnnotation(annotationClass) != null) {
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
            if (entityType.getBindableJavaType().getAnnotation(PortalTable.class) != null) {
                if (entityType.getBindableJavaType().getName().equals(tableOrViewName)) {
                    Field[] fields = entityType.getBindableJavaType().getDeclaredFields();
                    for (Field field : fields) {
                        if (field.getAnnotation(Column.class) != null) {
                            result.add(field.getAnnotation(Column.class).name());
                        }
                        if (field.getAnnotation(Id.class) != null) {
                            result.add("id");
                        }
                    }

                }
            }
        }
        return result;

    }

    @Override
    public Page<HashMap<String, String>> findAll(String tableOrViewName, Pageable pageable) {

        List<HashMap<String, String>> result = new ArrayList<>();

        EntityType<?> type = null;
        Set<EntityType<?>> set = em.getEntityManagerFactory().getMetamodel().getEntities();
        for (EntityType<?> entityType : set) {
            if (entityType.getBindableJavaType().getAnnotation(PortalTable.class) != null) {
                if (entityType.getBindableJavaType().getName().equals(tableOrViewName)) {
                    type = entityType;
                    break;
                }
            }
        }

        Long totalRows = 0L;

        if (type != null) {
            Class<?> bindableJavaType = type.getBindableJavaType();

            //count
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
            countQuery.select(criteriaBuilder.count(countQuery.from(bindableJavaType)));
            totalRows = em.createQuery(countQuery).getSingleResult();

            //select
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<?> cq = cb.createQuery(bindableJavaType);
            Root<?> root = cq.from(bindableJavaType);
//          cq.select(root);
            if (pageable == null) {
                pageable = new PageRequest(0, 50);
            }

            TypedQuery<?> query = em.createQuery(cq);

            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
            List<?> all = query.getResultList();

            List<String> columns = getTableOrViewMetaData(tableOrViewName);

            for (Object object : all) {

                HashMap<String, String> res = new HashMap<>(columns.size());
                Class<? extends Object> clazz = object.getClass();
                for (String fieldName : columns) {
                    try {
                        Field field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Object fieldValue = field.get(object);
                        res.put(fieldName, fieldValue.toString());
                        //TODO cast data integer long etc
                    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(TableServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                result.add(res);
            }
        }

        PageImpl<HashMap<String, String>> list = new PageImpl<>(result, pageable, totalRows);
        return list;
    }
/**
 * TODO  вернуть коллекцию ManyToMany
 * @param entityClass
 * @param id
 * @return 
 */
    @Override
    public Map<EntityType<?>, Map<String, String>> findByEntityClassId(String entityClass, String id) {

        try {
            Class<?> cl = Class.forName(entityClass);
            EntityType<?> entityType = em.getEntityManagerFactory().getMetamodel().entity(cl);
            if (entityType != null && entityType.getBindableJavaType().getAnnotation(PortalTable.class) != null) {
                if (entityType.getBindableJavaType().getName().equals(entityClass)) {
                    Class<?> bindableJavaType = entityType.getBindableJavaType();
                    //select

                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<?> cq = cb.createQuery(bindableJavaType);
                    Root<?> root = cq.from(User.class);

                    cq.where(cb.equal(root.get("id"), Long.parseLong(id)));

                    TypedQuery<?> query = em.createQuery(cq);
                    ParameterExpression<Long> parameter = cb.parameter(Long.class, "id");
                    //query.setParameter(parameter, Long.parseLong(id));
                    //query.unwrap(org.hibernate.Query.class).getQueryString();

                    Object result = query.getSingleResult();

                    List<String> columns = getTableOrViewMetaData(entityClass);

                    HashMap<String, String> res = new HashMap<>(columns.size());
                    Class<? extends Object> clazz = result.getClass();
                    for (String fieldName : columns) {
                        try {
                            Field field = clazz.getDeclaredField(fieldName);
                            field.setAccessible(true);
                            Object fieldValue = field.get(result);
                            res.put(fieldName, fieldValue.toString());
                        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                            Logger.getLogger(TableServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    System.out.println(res);
                    Map<EntityType<?>, Map<String, String>> hm = new HashMap<>();
                    hm.put(entityType, res);
                    return hm;

                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TableServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
