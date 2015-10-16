/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.metamodel.EntityType;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.portal.entity.Role;
import ru.portal.entity.User;
import ru.portal.gwt.gwtportal.client.EditorDto;
import ru.portal.gwt.gwtportal.client.GWTService;
import ru.portal.gwt.gwtportal.client.TableDto;
import ru.portal.gwt.gwtportal.client.TableFieldsDto;
import ru.portal.interfaces.PortalTable;
import ru.portal.services.RoleService;
import ru.portal.services.TableService;
import ru.portal.services.UserService;

/**
 *
 * @author Igor Salnikov
 */
//@Configuration
public class GWTServiceImpl  implements GWTService {

    @Autowired
    private TableService tableService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//
//        WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
//    }


    @Override
    public String myMethod(String s) {
        return "Server says: " + s;
    }

    @Override
    public List<TableFieldsDto> getTableNameWithFields(TableDto tableDto) {
        List<TableFieldsDto> result = new ArrayList<>();
        Set<EntityType<?>> set = tableService.getEntityTypesByAnnotationClass(PortalTable.class);
        if (tableDto == null) {
            for (EntityType<?> entityType : set) {
                TableDto dto = new TableDto(entityType.getBindableJavaType().getName(), entityType.getBindableJavaType().getAnnotation(PortalTable.class).title(), entityType.getBindableJavaType().getName());
                result.add(dto);
                
                List<User> users = new ArrayList<>();
                for (int i = 0; i < 150; i++) {
                    users.add(new User("admin" + i, "admin", true));
                }
                userService.save(users);
                
                
                List<Role> roles = new ArrayList<>();
                for (int i = 0; i < 200; i++) {
                    roles.add(new Role("admin" + i));
                }
                
                roleService.save(roles);
                
            }
        } else {
            for (EntityType<?> entityType : set) {
                if (entityType.getBindableJavaType().getName().equals(tableDto.getId())) {
                    Field[] fields = entityType.getBindableJavaType().getDeclaredFields();
                    for (Field field : fields) {
                        TableFieldsDto dto = new TableFieldsDto(entityType.getBindableJavaType().getName() + field.getName(), field.getName(), field.toString());
                        result.add(dto);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<String> fetchTableOrViewMetaData(String tableOrViewName) {
        return tableService.getTableOrViewMetaData(tableOrViewName);
    }

    @Override
    public PagingLoadResult<Map<String, String>> fetchTableOrViewData(PagingLoadConfig config, String tableOrViewName) {
        //List<? extends SortInfo> sortList = config.getSortInfo();
        //Sort sort = new Sort(Sort.Direction.DESC, "login");
        int limit = 50;
        int page = 0;
        int offset = 0;
        if (config != null) {
            page = (config.getLimit() != 0 && config.getOffset() > 0) ? (config.getOffset() / config.getLimit()) : 0;
            limit = config.getLimit();
            offset = config.getOffset();
        }

        Pageable pageable = new PageRequest(page, limit); //TODO sort + page size + тут мощный сb 
        Page<HashMap<String, String>> result = tableService.findAll(tableOrViewName, pageable);
        ArrayList<HashMap<String,String>> res = new ArrayList<>( result.getContent());
        return new PagingLoadResultBean(res, (int) result.getTotalElements(), offset);
    }

    @Override
    public List<EditorDto> fetchById(String entityClass, String id) {
        ArrayList<EditorDto> result = new ArrayList<>();
        Map<EntityType<?>, Map<String, String>> hm = tableService.findByEntityClassId(entityClass,id);
        
      
        Set<EntityType<?>> set = hm.keySet();
        for (EntityType<?> entityType : set) {
            Map<String, String> map = hm.get(entityType);
            
            
            
        }
        
        return result;
    }

}
