/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.portal.gwt.gwtportal.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.RowNumberer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * https://www.sencha.com/forum/showthread.php?276826-GXT-3-Dynamic-Grid-with-Dynamic-Column-Model-and-Custom-ValueProvider-WORKING-100
 *
 */
public class DynamicGridView implements IsWidget{
    
   private final GWTServiceAsync service = GWT.create(GWTService.class);
   
   
   private String tableName ;
   
   private AsyncCallback<List<String>> callback;
   
   private SimpleContainer simpleContainer;
   
   private SelectionHandler handler;
    
    private DynamicGrid<Map<String,String>, ColumnModel<Map<String,String>>, RpcProxy<PagingLoadConfig, PagingLoadResult<Map<String,String>>>> dynamicGridPanel;
    @Override
    public Widget asWidget() {
        simpleContainer = new SimpleContainer();
        simpleContainer.setBorders(true);
        simpleContainer.mask();
        //First get columns
        //TODO list of <name:column type> 
        callback = new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                RpcProxy<PagingLoadConfig, PagingLoadResult<Map<String,String>>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<Map<String,String>>>() {
                    @Override
                    public void load(PagingLoadConfig loadConfig,final AsyncCallback<PagingLoadResult<Map<String,String>>> callback) {
                        service.fetchTableOrViewData(loadConfig, tableName, callback);
                        dynamicGridPanel.getGrid().getSelectionModel().addSelectionHandler(handler);
                    }
                };


                /*Dynamic columns*/
                List<ColumnConfig<Map<String,String>,?>> lists = new ArrayList<ColumnConfig<Map<String,String>,?>>();
                
                RowNumberer rowNumberer = new RowNumberer();
                rowNumberer.setHeader("№");
                lists.add(rowNumberer);
                
                for(String column : result){
                    ColumnConfig<Map<String,String>,String> cc = new ColumnConfig<Map<String,String>, String>(new GenericMapValueProvider(column),150,column);
                    lists.add(cc);
                }
                
                ColumnModel<Map<String,String>> columnModel = new ColumnModel<Map<String,String>>(lists);
                
                dynamicGridPanel = new DynamicGrid<Map<String,String>, ColumnModel<Map<String,String>>, RpcProxy<PagingLoadConfig, PagingLoadResult<Map<String,String>>>>(columnModel, proxy);
                
                
                VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
                layoutContainer.add(dynamicGridPanel.asWidget(), new VerticalLayoutData(1, 1));
                simpleContainer.add(layoutContainer);
                simpleContainer.unmask();
                simpleContainer.forceLayout();
                
            }
            
            @Override
            public void onFailure(Throwable caught) {
                simpleContainer.unmask();
                new AlertMessageBox("Error", "Error while loading grid."+caught.getMessage()).show();
            }
        }; 
       
        return simpleContainer;
    }

   
    
    
    
   public void refreshGrid(String tableName) {
        this.tableName = tableName;
        service.fetchTableOrViewMetaData(tableName, callback); 
    }

      
    void addSelectionHandler(SelectionHandler handler) {
       this.handler =  handler;
    }

   
}