/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.portal.gwt.gwtportal.client;

import com.sencha.gxt.core.client.ValueProvider;
import java.util.Map;

/**
 * Create a ValueProvider which can fetch data from Map<String,String>
 * @author Igor Salnikov
 */
public class GenericMapValueProvider implements ValueProvider<Map<String, String>, String> {
   
    private String key;

    public GenericMapValueProvider(String key) {
        this.key = key;
    }
   
    @Override
    public String getValue(Map<String, String> object) {
      return object.get(key);
    }

    @Override
    public void setValue(Map<String, String> object, String value) {
        object.put(key, value); 
    }

    @Override
    public String getPath() {
      return key;
    }
    
}
