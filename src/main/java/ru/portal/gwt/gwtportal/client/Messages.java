package ru.portal.gwt.gwtportal.client;



import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource;

@LocalizableResource.DefaultLocale("ru")
public interface Messages extends Constants {

    @DefaultStringValue("привет мир!")
    public String hello();

    @DefaultStringValue("Пусто")
    public String emptyText();

}
