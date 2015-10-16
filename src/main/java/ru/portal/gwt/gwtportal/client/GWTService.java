/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Igor Salnikov
 */
@RemoteServiceRelativePath("gwt.rpc")
public interface GWTService extends RemoteService {

    public String myMethod(String s);

    public List<TableFieldsDto> getTableNameWithFields(TableDto tableDto);

    public List<String> fetchTableOrViewMetaData(String tableOrViewName);

    public PagingLoadResult<Map<String, String>> fetchTableOrViewData(PagingLoadConfig config, String tableOrViewName);
    
    public List<EditorDto> fetchById(String entityClass , String id);

}
