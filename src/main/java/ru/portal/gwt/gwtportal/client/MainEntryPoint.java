package ru.portal.gwt.gwtportal.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

public class MainEntryPoint implements EntryPoint {

    protected Messages messages = (Messages) GWT.create(Messages.class);

    @Override
    public void onModuleLoad() {

        AdminForm adminForm = new AdminForm();
        Viewport viewport = new Viewport();
        viewport.add(adminForm.asWidget());
        RootPanel.get().add(viewport);

    }

    public static <T extends JavaScriptObject> T parseJson(String jsonStr) {
        return JsonUtils.safeEval(jsonStr);
    }

}
