/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.client;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Igor Salnikov
 */
public class EditorDto implements Serializable {

    private String id;
    private String labelText;
    private String classType;
    private String value;

    public EditorDto() {
    }

    public EditorDto(String id, String labelText, String classType, String value) {
        this.id = id;
        this.labelText = labelText;
        this.classType = classType;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.labelText);
        hash = 83 * hash + Objects.hashCode(this.classType);
        hash = 83 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EditorDto other = (EditorDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.labelText, other.labelText)) {
            return false;
        }
        if (!Objects.equals(this.classType, other.classType)) {
            return false;
        }
        return Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return "EditorDto{" + "id=" + id + ", labelText=" + labelText + ", classType=" + classType + ", value=" + value + '}';
    }

}
