/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.client;

import java.util.List;
 
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.state.client.TreeStateHandler;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 *
 * @author Igor Salnikov
 * 
 */

public class DynamicTreeView implements IsWidget {

    private final GWTServiceAsync service = GWT.create(GWTService.class);

    private VerticalLayoutContainer panel;
    
     private Tree<TableFieldsDto, String> tree;



    class KeyProvider implements ModelKeyProvider<TableFieldsDto> {

        @Override
        public String getKey(TableFieldsDto item) {
            return (item instanceof TableDto ? "t-" : "f-") + item.getId().toString();
        }
    }
     
     
     
    @Override
    public Widget asWidget() {
        if (panel == null) {
            
            RpcProxy<TableFieldsDto, List<TableFieldsDto>> proxy = new RpcProxy<TableFieldsDto, List<TableFieldsDto>>() {
                @Override
                public void load(TableFieldsDto loadConfig, AsyncCallback<List<TableFieldsDto>> callback) {
                    service.getTableNameWithFields((TableDto) loadConfig, callback);
                }
            };
            
            TreeLoader<TableFieldsDto> loader = new TreeLoader<TableFieldsDto>(proxy) {
                @Override
                public boolean hasChildren(TableFieldsDto parent) {
                    return parent instanceof TableDto;
                }
            };
    
            TreeStore<TableFieldsDto> store = new TreeStore<TableFieldsDto>(new KeyProvider());
            loader.addLoadHandler(new ChildTreeStoreBinding<TableFieldsDto>(store));

          

             tree = new Tree<TableFieldsDto, String>(store, new ValueProvider<TableFieldsDto, String>() {
                @Override
                public String getValue(TableFieldsDto object) {
                    return object.getName();
                }

                @Override
                public void setValue(TableFieldsDto object, String value) {
                }

                @Override
                public String getPath() {
                    return "name";
                }
            });
            tree.setLoader(loader);
            tree.getStyle().setLeafIcon(PortalImages.INSTANCE.table());
            tree.setContextMenu(null);
            tree.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
            
            
            panel = new VerticalLayoutContainer();
            panel.add(tree, new VerticalLayoutData(1, 1));
            
            
            TreeStateHandler<TableFieldsDto> stateHandler = new TreeStateHandler<TableFieldsDto>(tree);
            stateHandler.loadState();
            
        }
        return panel;
    }
    
    void addSelectionHandler(SelectionHandler handler) {
         tree.getSelectionModel().addSelectionHandler(handler);
    }

}
