package ru.portal.gwt.gwtportal.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.info.Info;

public class MainEntryPoint implements EntryPoint {

    protected Messages messages = (Messages) GWT.create(Messages.class);

    @Override
    public void onModuleLoad() {

        final LongField random = new LongField();

        final Dialog simple = new Dialog();
        simple.setHeadingText("Dialog Test");
        simple.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
        simple.setBodyStyleName("pad-text");

        simple.getBody().addClassName("pad-text");
        simple.setHideOnButtonClick(true);
        simple.setModal(true);
        simple.setWidth(300);

        SelectHandler selectHandler = new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {

                //GWT.log(GWT.getModuleBaseURL());
                GWT.log(GWT.getHostPageBaseURL());
                //GWT.log(GWT.getModuleBaseForStaticFiles());
                //GWT.log(GWT.getModuleName());
                //GWT.log(GWT.getPermutationStrongName());
                Info.display("Click", ((TextButton) event.getSource()).getText() + " clicked");

                StringBuilder param = new StringBuilder();
                param.append("random/").append(random.getCurrentValue() == null ? "100" : random.getCurrentValue());

                RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + param.toString());

                try {

                    builder.sendRequest(null, new RequestCallback() {

                        @Override
                        public void onError(Request request, Throwable exception) {
                            simple.add(new Label(exception.getLocalizedMessage()));
                            simple.show();
                        }
                        
                        @Override
                        public void onResponseReceived(Request request, Response response) {
                            
                            if (200 == response.getStatusCode()){
                              simple.add(new Label(response.getText()));
                              simple.show();
                            } else {
                               simple.add(new Label(response.getStatusText() +":"+ response.getStatusCode() ));
                              simple.show(); 
                            }
                            
                        }

            
                    });

                } catch (RequestException e) {
                    GWT.log("RequestBuilder Exception", e);
                }

            }
        };

        TextButton button = new TextButton(messages.hello(), selectHandler);

        RootPanel.get().add(button);
        RootPanel.get().add(random);

    }

}
