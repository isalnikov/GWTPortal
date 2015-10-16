/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

/**
 *
 * @author Igor Salnikov 
 */
public class DynamicEditorView implements IsWidget {

    private SimpleContainer simpleContainer;

    @Override
    public Widget asWidget() {
        if (simpleContainer == null) {
            simpleContainer = new SimpleContainer();
            simpleContainer.mask();
            
            
            
            
            

        }
        return simpleContainer;
    }



    void refreshEditForm(TableFieldsDto item, Object selectedItem) {
        
    }

}
