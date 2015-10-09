
package ru.portal.gwt.gwtportal.client;

import java.util.List;

/**
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 * @param <T>
 */
   public  interface AbstractResult<T> {
        List<T> getRecords();
    }
