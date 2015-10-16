/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.GridStateHandler;
import com.sencha.gxt.state.client.StateManager;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import java.util.HashMap;

/**
 *
 *
 * @author Igor Salnikov
 *
 * @param <M> Data Model
 * @param <CM> Column Model
 * @param <P> Proxy
 */
public class DynamicGrid<M, CM extends ColumnModel<M>, P extends RpcProxy<PagingLoadConfig, PagingLoadResult<M>>> implements IsWidget {

    private Messages messages = GWT.create(Messages.class);
    
    private CM columnModel;
    private P proxy;
    private Grid<M> grid;
    private PagingLoader<PagingLoadConfig, PagingLoadResult<M>> loader;

    public DynamicGrid(final CM columnModel, final P proxy) {
        this.columnModel = columnModel;
        this.proxy = proxy;
    }

    @Override
    public Widget asWidget() {
        if (StateManager.get().getProvider() == null) {
            StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));
        }
        ListStore<M> store = new ListStore<M>(new ModelKeyProvider<M>() {
            @Override
            public String getKey(M item) {
               HashMap<String, String> map = (HashMap<String, String>) item;
               String id = map.get("id");
//                if (item instanceof <YourCustomerClass>) {
//                    return ((YourCustomerClass) item).getId();
//                }
                //GWT.log(id);
                return id;
            }
        });
        this.loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<M>>(proxy);
        this.loader.setRemoteSort(true);
        this.loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, M, PagingLoadResult<M>>(store));

        final PagingToolBar toolBar = new PagingToolBar(50);
        toolBar.getElement().getStyle().setProperty("borderBottom", "none");
        toolBar.bind(loader);

        this.grid = new Grid<M>(store, columnModel) {
            @Override
            protected void onAfterFirstAttach() {
                super.onAfterFirstAttach();
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    @Override
                    public void execute() {
                        loader.load();
                    }
                });
            }
        };

        this.grid.getView().setStripeRows(true);
        this.grid.getView().setColumnLines(true);
        this.grid.getView().setAutoFill(true);
        this.grid.getView().setEmptyText(messages.emptyText());
        this.grid.setBorders(false);
        this.grid.setColumnReordering(true);
        
        this.grid.setStateful(true);
        this.grid.setLoadMask(true);
        this.grid.setStateId("gynamicGrid");

        GridStateHandler<M> state = new GridStateHandler<M>(this.grid);
        state.loadState();

        
        
        VerticalLayoutContainer container = new VerticalLayoutContainer();
        container.add(this.grid, new VerticalLayoutData(1, 750));
        container.add(toolBar, new VerticalLayoutData(1,-1));
        container.forceLayout();
        return container;
    }

    public void refreshDynamicGrid() {
        this.loader.load();
    }

    public Grid<M> getGrid() {
        return this.grid;
    }

}
