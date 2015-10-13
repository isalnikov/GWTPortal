/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.portal.gwt.gwtportal.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent.RowDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

/**
 * https://www.sencha.com/forum/showthread.php?276826-GXT-3-Dynamic-Grid-with-Dynamic-Column-Model-and-Custom-ValueProvider-WORKING-100
 *
 */
public class DynamicGridView implements IsWidget{
    
   private final GWTServiceAsync service = GWT.create(GWTService.class);
   
   
   private String tableName ;
   
   private AsyncCallback<List<String>> callback;
   
   private SimpleContainer simpleContainer;
    
    private DynamicGrid<Map<String,String>, ColumnModel<Map<String,String>>, RpcProxy<PagingLoadConfig, PagingLoadResult<Map<String,String>>>> dynamicGridPanel;
    @Override
    public Widget asWidget() {
        simpleContainer = new SimpleContainer();
        simpleContainer.mask();
        //First get columns
        
        callback = new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                RpcProxy<PagingLoadConfig, PagingLoadResult<Map<String,String>>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<Map<String,String>>>() {
                    @Override
                    public void load(PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<Map<String,String>>> callback) {
                        service.fetchTableOrViewData(loadConfig, tableName, callback);
                    }
                };


                /*Dynamic columns*/
                List<ColumnConfig<Map<String,String>,?>> l = new ArrayList<ColumnConfig<Map<String,String>,?>>();
                for(String column:result){
                    ColumnConfig<Map<String,String>,String> cc = new ColumnConfig<Map<String,String>, String>(new GenericMapValueProvider(column),150,column);
                    l.add(cc);
                }
                ColumnModel<Map<String,String>> columnModel = new ColumnModel<Map<String,String>>(l);
                
                dynamicGridPanel = new DynamicGrid<Map<String,String>, ColumnModel<Map<String,String>>, RpcProxy<PagingLoadConfig, PagingLoadResult<Map<String,String>>>>(columnModel, proxy);
                SimpleContainer container = new SimpleContainer();
                container.add(dynamicGridPanel.asWidget());
                VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
                layoutContainer.setBorders(true);
                layoutContainer.add(container, new VerticalLayoutData(1, 1));
                simpleContainer.add(layoutContainer);
                simpleContainer.unmask();
                simpleContainer.forceLayout();
            }
            
            @Override
            public void onFailure(Throwable caught) {
                simpleContainer.unmask();
                new AlertMessageBox("Error", "Error while loading grid."+caught.getMessage()).show();;
            }
        }; 
       
        return simpleContainer;
    }

   
    
    
    
   public void refreshGrid(String tableName ,PagingLoadConfig loadConfig) {
        this.tableName = tableName;
        service.fetchTableOrViewMetaData(tableName, callback); 
        this.dynamicGridPanel.refreshGrid(loadConfig);
        
    }

}