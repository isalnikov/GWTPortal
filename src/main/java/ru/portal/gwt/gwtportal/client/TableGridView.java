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
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
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

    public interface TableField {

        String getId();
        
        String getLogin();
        
        //String[] getFieldNames();
        
        //String[] getFieldValues();


    }

    
    public  interface RecordResult extends AbstractResult<TableField>{
       
    }

    class DataRecordJsonReader extends JsonReader<ListLoadResult<TableField>, RecordResult> {

        public DataRecordJsonReader(AutoBeanFactory factory, Class<RecordResult> rootBeanType) {
            super(factory, rootBeanType);
        }

        @Override
        protected ListLoadResult<TableField> createReturnData(Object loadConfig, RecordResult incomingData) {
            return new ListLoadResultBean<>(incomingData.getRecords());
        }
    }

    interface TableFieldProperties extends PropertyAccess<TableField> {
        @Path("id")
        ModelKeyProvider<TableField> key();

        ValueProvider<TableField, String> id();
        
        ValueProvider<TableField, String> login();

        //ValueProvider<TableField, String[]> fieldNames();
    }

    private VerticalLayoutContainer panel;
    
    private ListLoader<ListLoadConfig, ListLoadResult<TableField>> loader;

    @Override
    public Widget asWidget() {
        if (panel == null) {

            TableAutoBeanFactory factory = GWT.create(TableAutoBeanFactory.class);

            DataRecordJsonReader jsonReader = new DataRecordJsonReader(factory, RecordResult.class);
            

            String path = "grid";
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);
            HttpProxy<ListLoadConfig> proxy = new HttpProxy<>(builder);

            loader = new ListLoader<>(proxy, jsonReader);
            loader.useLoadConfig(factory.create(ListLoadConfig.class).as());

            TableFieldProperties properties = GWT.create(TableFieldProperties.class);

            ListStore<TableField> store = new ListStore<>(properties.key());
            loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, TableField, ListLoadResult<TableField>>(store));

            ColumnConfig<TableField, String> senderColumn = new ColumnConfig<>(properties.id(), 100, "id");
            ColumnConfig<TableField, String> loginColumn = new ColumnConfig<>(properties.login(), 165, "login");
            
         
                
            
//            ColumnConfig<TableField, String> phoneColumn = new ColumnConfig<>(properties.phone(), 100, "Phone");
  //          ColumnConfig<TableField, String> stateColumn = new ColumnConfig<>(properties.state(), 50, "State");
//            ColumnConfig<TableField, String> zipColumn = new ColumnConfig<>(properties.zip(), 65, "Zip Code");

            List<ColumnConfig<TableField, ?>> l = new ArrayList<>();
            l.add(senderColumn);
            l.add(loginColumn);
          //  l.add(phoneColumn);
          //  l.add(stateColumn);
          //  l.add(zipColumn);

            ColumnModel<TableField> cm = new ColumnModel<>(l);

            Grid<TableField> grid = new Grid<>(store, cm);
            grid.getView().setForceFit(true);
            grid.setLoader(loader);
            grid.setLoadMask(true);
            grid.setBorders(true);
            grid.getView().setEmptyText("Please hit the load button.");

            panel = new VerticalLayoutContainer();
            panel.add(grid, new VerticalLayoutContainer.VerticalLayoutData(1, 1));
            
            
            //panel.setPixelSize(575, 350);
            //panel.addStyleName("margin-10");
           // panel.setButtonAlign(BoxLayoutPack.CENTER);
            //panel.addButton(new TextButton("Load Json", new SelectHandler() {
            //    @Override
            //    public void onSelect(SelectEvent event) {
            //        loader.load();
            //    }
            //}));

        }

        return panel;
    }
    
    public ListLoader getListLoader(){
        return loader;
    }

}
