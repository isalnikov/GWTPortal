package ru.portal.gwt.gwtportal.client;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.client.writer.UrlEncodingWriter;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.JsonReader;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CheckChangeEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;
import java.util.List;

/**
 * Список всех таблиц доступных для редактирования
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 */
public class TableTreeView implements IsWidget {

    public interface JsonTreeAutoBeanFactory extends AutoBeanFactory {

        AutoBean<RecordResult> items();
    }

    public interface RecordResult extends AbstractResult<Record> {

    }

    /**
     * Defines the structure of our JSON records.
     */
    public interface Record {

        String getId();

        String getName();
        
        boolean isTable();
    }

    private class RecordKeyProvider implements ModelKeyProvider<Record> {

        @Override
        public String getKey(Record item) {
            return item.toString();
        }
    }

    private class DataRecordJsonReader extends JsonReader<List<Record>, RecordResult> {

        public DataRecordJsonReader(AutoBeanFactory factory, Class<RecordResult> rootBeanType) {
            super(factory, rootBeanType);
        }

        @Override
        protected List<Record> createReturnData(Object loadConfig, RecordResult incomingData) {
            return incomingData.getRecords();
        }
    }

    private VerticalLayoutContainer panel;

    @Override
    public Widget asWidget() {
        if (panel == null) {
            JsonTreeAutoBeanFactory factory = GWT.create(JsonTreeAutoBeanFactory.class);

            DataRecordJsonReader reader = new DataRecordJsonReader(factory, RecordResult.class);

            RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "tables");

            HttpProxy<Record> jsonProxy = new HttpProxy<>(requestBuilder);

            jsonProxy.setWriter(new UrlEncodingWriter<>(factory, Record.class));

            TreeLoader<Record> loader = new TreeLoader<Record>(jsonProxy, reader) {
                @Override
                public boolean hasChildren(Record parent) {
                    return parent.isTable();
                }
            };

            TreeStore<Record> store = new TreeStore<>(new RecordKeyProvider());
            loader.addLoadHandler(new ChildTreeStoreBinding<>(store));

            final Tree<Record, String> tree = new Tree<>(store, new ValueProvider<Record, String>() {
                @Override
                public String getValue(Record object) {
                    return object.getName();
                }

                @Override
                public void setValue(Record object, String value) {
                }

                @Override
                public String getPath() {
                    return "name";
                }
            });
            tree.setLoader(loader);
            tree.getStyle().setLeafIcon(PortalImages.INSTANCE.table());
            tree.setContextMenu(null);

            ToolBar buttonBar = new ToolBar();
            buttonBar.add(new TextButton("Expand All", new SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    tree.expandAll();
                }
            }));
            buttonBar.add(new TextButton("Collapse All", new SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    tree.collapseAll();
                }
            }));

            SimpleSafeHtmlCell<String> cell = new SimpleSafeHtmlCell<String>(SimpleSafeHtmlRenderer.getInstance(), "click", "touchend") {
                @Override
                public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event,
                        ValueUpdater<String> valueUpdater) {
                    super.onBrowserEvent(context, parent, value, event, valueUpdater);
                    if ("touchend".equals(event.getType()) || "click".equals(event.getType())) {
                        Info.display("Selected", "You selected \"" + value + "\"!");
                    }
                }
            };
            
            tree.setCell(cell);

            panel = new VerticalLayoutContainer();
            panel.add(buttonBar, new VerticalLayoutData(1, -1));
            panel.add(tree, new VerticalLayoutData(1, 1));

        }

        return panel;
    }

}
