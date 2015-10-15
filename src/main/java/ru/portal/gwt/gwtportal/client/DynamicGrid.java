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
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
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
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

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
                GWT.log(item.toString());
                String id = null;
                if (item != null) {
                    // {rolename=ROLE_USER, id=2}
                    String[] arr = item.toString().replaceAll("\\{", "").replaceAll("\\}", "").split(",");
                    for (String string : arr) {
                        String[] sarr = string.split("=");
                        if (sarr[0].replaceAll(" ", "").equals("id")) {
                            id = sarr[1];
                            break;
                        }
                    }
                }
//                if (item instanceof <YourCustomerClass>) {
//                    return ((YourCustomerClass) item).getId();
//                }
                GWT.log(id);
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
        this.grid.setBorders(false);
        this.grid.setColumnReordering(true);
        this.grid.setStateful(true);
        this.grid.setLoadMask(true);
        this.grid.setStateId("gridExample");

        GridStateHandler<M> state = new GridStateHandler<M>(this.grid);
        state.loadState();

        VerticalLayoutContainer container = new VerticalLayoutContainer();
        container.setBorders(true);
        container.add(this.grid, new VerticalLayoutData(-1, -1));
        container.add(toolBar, new VerticalLayoutData(1, -1));
        return container;
    }

    public void refreshDynamicGrid() {
        this.loader.load();
    }

    public Grid<M> getGrid() {
        return this.grid;
    }

}
