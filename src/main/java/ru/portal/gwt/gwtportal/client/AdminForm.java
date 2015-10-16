/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import java.util.Date;

/**
 * Главная форма панели администратора
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 */
public class AdminForm implements IsWidget {

    private BorderLayoutContainer container;
    private DynamicTreeView tableTreeView;
    private DynamicGridView tableGridView;
    private DynamicEditorView dynamicEditorView;

    private HTML logsHtml;

    public AdminForm() {
    }

    @Override
    public Widget asWidget() {
        if (container == null) {

            
            
            tableTreeView = new DynamicTreeView();
            tableGridView = new DynamicGridView();
            dynamicEditorView = new DynamicEditorView();

            container = new BorderLayoutContainer();
            container.setContextMenu(null);
            container.setBorders(true);

            //top menu
            MenuBar bar = new MenuBar();
            bar.addStyleName(ThemeStyles.get().style().borderBottom());
            MenuBarItem item = new MenuBarItem("Настройки");

            Menu menu = new Menu();
//          //menu.addSelectionHandler(handler);
//
            MenuItem prop = new MenuItem("User property");
            menu.add(prop);
//
            item.setMenu(menu);
            bar.add(item);

            //
            BorderLayoutData northData = new BorderLayoutData(20);
            northData.setMargins(new Margins(5));
            northData.setCollapsible(false);
            northData.setCollapseMini(false);
            //
            BorderLayoutData westData = new BorderLayoutData(350);
            westData.setCollapsible(true);
            westData.setCollapseMini(true);
            westData.setSplit(true);
            westData.setMargins(new Margins(5));
            westData.setMinSize(100);
            westData.setMaxSize(350);
            //
            MarginData centerData = new MarginData(5);
            //
            BorderLayoutData eastData = new BorderLayoutData(150);
            eastData.setMargins(new Margins(5));
            eastData.setCollapsible(true);
            eastData.setCollapseMini(true);
            eastData.setSplit(true);
            // логирование истории запросов и ответов
            BorderLayoutData southData = new BorderLayoutData(100);
            southData.setMargins(new Margins(5));
            southData.setCollapsible(true);
            southData.setCollapseMini(true);
            southData.setSplit(true);
            //
            NorthSouthContainer north = new NorthSouthContainer();
            north.setResize(false);
            north.setNorthWidget(bar);
            //
            ContentPanel west = new ContentPanel();
            west.add(tablesTreeView());
            //
            ContentPanel center = new ContentPanel();
            center.setResize(false);
            center.add(tablesGridView());

            ContentPanel south = new ContentPanel();
            ContentPanel east = new ContentPanel();

            //
            logsHtml = new HTML("loggin...", HasDirection.Direction.LTR) {
                @Override
                public void setHTML(String html) {
                    if (html != null && "clear".equals(html)) {
                        super.setHTML(" ");
                        return;
                    }
                    StringBuilder log = new StringBuilder(this.getHTML());
                    log.append("<br/>");
                    log.append((new Date()).toLocaleString()).append(" : ");
                    log.append(html);
                    super.setHTML(log.toString());
                }
            };
            ScrollPanel logScrollPanel = new ScrollPanel(logsHtml);
            south.add(logScrollPanel);

            east.add(editorView());
            //

            container.setNorthWidget(north, northData);
            container.setWestWidget(west, westData);
            container.setCenterWidget(center, centerData);
            container.setEastWidget(east, eastData);
            container.setSouthWidget(south, southData);

            addSelectionHandlers();

        }

        return container;
    }

    private IsWidget tablesTreeView() {
        return tableTreeView.asWidget();
    }

    private IsWidget tablesGridView() {
        return tableGridView.asWidget();
    }
    
    private IsWidget editorView(){
        return dynamicEditorView.asWidget();
    }

    private void addSelectionHandlers() {

        tableTreeView.addSelectionHandler(new SelectionHandler<TableFieldsDto>() {

            @Override
            public void onSelection(SelectionEvent<TableFieldsDto> event) {
                TableFieldsDto select = event.getSelectedItem();
//                GWT.log(select.getClassName());
                 logsHtml.setHTML(select.getClassName());
                if (TableDto.class.equals(select.getClass())) {
                    tableGridView.refreshGrid(select.getClassName());
                }
            }
        });

        
        
        tableGridView.addSelectionHandler(new SelectionHandler() {
            @Override
            public void onSelection(SelectionEvent event) {
             //   GWT.log(event.getSelectedItem().toString());
                logsHtml.setHTML(event.getSelectedItem().toString());
                //TODO  взять элемент из таблицы с сгенерить форму редактирования ...
                TableFieldsDto item = tableTreeView.getSelectedItem();
                dynamicEditorView.refreshEditForm(item,event.getSelectedItem());
            }
        });

    }
}
