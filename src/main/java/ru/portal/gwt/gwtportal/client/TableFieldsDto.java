/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.client;

/**
 *
 * @author Igor Salnikov
 */
import java.io.Serializable;
import java.util.List;

import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;

public class TableFieldsDto implements Serializable, TreeStore.TreeNode<TableFieldsDto> {

    private String id;
    private String name;
    private String className;

    protected TableFieldsDto() {

    }

    public TableFieldsDto(String id, String name, String className) {
        this.id = id;
        this.name = name;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public TableFieldsDto getData() {
        return this;
    }

    @Override
    public List<? extends TreeNode<TableFieldsDto>> getChildren() {
        return null;
    }

    @Override
    public String toString() {
        return name != null ? name : super.toString();
    }

}
