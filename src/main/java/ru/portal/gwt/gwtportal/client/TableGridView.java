package ru.portal.gwt.gwtportal.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.JsonReader;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 */
public class TableGridView implements IsWidget {

    public interface TableAutoBeanFactory extends AutoBeanFactory {

        AutoBean<RecordResult> items();

        AutoBean<ListLoadConfig> loadConfig();
    }

    public interface Email {

        String getName();

        String getEmail();

        String getPhone();

        String getState();

        String getZip();
    }

    
    public  interface RecordResult extends AbstractResult<Email>{
       
    }

    class DataRecordJsonReader extends JsonReader<ListLoadResult<Email>, RecordResult> {

        public DataRecordJsonReader(AutoBeanFactory factory, Class<RecordResult> rootBeanType) {
            super(factory, rootBeanType);
        }

        @Override
        protected ListLoadResult<Email> createReturnData(Object loadConfig, RecordResult incomingData) {
            return new ListLoadResultBean<>(incomingData.getRecords());
        }
    }

    interface EmailProperties extends PropertyAccess<Email> {

        @Path("name")
        ModelKeyProvider<Email> key();

        ValueProvider<Email, String> name();

        ValueProvider<Email, String> email();

        ValueProvider<Email, String> phone();

        ValueProvider<Email, String> state();

        ValueProvider<Email, String> zip();
    }

    private FramedPanel panel;

    @Override
    public Widget asWidget() {
        if (panel == null) {

            TableAutoBeanFactory factory = GWT.create(TableAutoBeanFactory.class);

            DataRecordJsonReader jsonReader = new DataRecordJsonReader(factory, RecordResult.class);
            

            String path = "grid";
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);
            HttpProxy<ListLoadConfig> proxy = new HttpProxy<>(builder);

            final ListLoader<ListLoadConfig, ListLoadResult<Email>> loader = new ListLoader<>(proxy, jsonReader);
            loader.useLoadConfig(factory.create(ListLoadConfig.class).as());

            EmailProperties properties = GWT.create(EmailProperties.class);

            ListStore<Email> store = new ListStore<>(properties.key());
            loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, Email, ListLoadResult<Email>>(store));

            ColumnConfig<Email, String> senderColumn = new ColumnConfig<>(properties.name(), 100, "Sender");
            ColumnConfig<Email, String> emailColumn = new ColumnConfig<>(properties.email(), 165, "Email");
            ColumnConfig<Email, String> phoneColumn = new ColumnConfig<>(properties.phone(), 100, "Phone");
            ColumnConfig<Email, String> stateColumn = new ColumnConfig<>(properties.state(), 50, "State");
            ColumnConfig<Email, String> zipColumn = new ColumnConfig<>(properties.zip(), 65, "Zip Code");

            List<ColumnConfig<Email, ?>> l = new ArrayList<>();
            l.add(senderColumn);
            l.add(emailColumn);
            l.add(phoneColumn);
            l.add(stateColumn);
            l.add(zipColumn);

            ColumnModel<Email> cm = new ColumnModel<>(l);

            Grid<Email> grid = new Grid<>(store, cm);
            grid.getView().setForceFit(true);
            grid.setLoader(loader);
            grid.setLoadMask(true);
            grid.setBorders(true);
            grid.getView().setEmptyText("Please hit the load button.");

            panel = new FramedPanel();
            panel = new FramedPanel();
            panel.setHeaderVisible(false);
            panel.setWidget(grid);
            panel.setHeight("100%");
            //panel.setPixelSize(575, 350);
            //panel.addStyleName("margin-10");
            panel.setButtonAlign(BoxLayoutPack.CENTER);
            panel.addButton(new TextButton("Load Json", new SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    loader.load();
                }
            }));

        }

        return panel;
    }

}
