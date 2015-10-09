
package ru.portal.gwt.gwtportal.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 */
public interface PortalImages extends ClientBundle {

    public PortalImages INSTANCE = GWT.create(PortalImages.class);

    @Source("table.png")
    ImageResource table();

}
