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
import java.util.List;
 
@SuppressWarnings("serial")
public class TableDto extends TableFieldsDto {
 
  private List<TableFieldsDto> children;
 
  protected TableDto() {
 
  }
 
  public TableDto(String id, String name , String className) {
    super(id, name, className);
  }
 
  @Override
  public List<TableFieldsDto> getChildren() {
    return children;
  }
 
  public void setChildren(List<TableFieldsDto> children) {
    this.children = children;
  }
 
  public void addChild(TableFieldsDto child) {
    getChildren().add(child);
  }
}