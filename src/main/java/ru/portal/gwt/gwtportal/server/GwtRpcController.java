/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.server;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.impl.LegacySerializationPolicy;
import java.lang.reflect.Field;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * https://technophiliac.wordpress.com/tag/controller/
 * http://stackoverflow.com/questions/1225152/gwt-scaffolding/30838630#30838630
 *
 * @author Igor Salnikov
 */
public class GwtRpcController extends RemoteServiceServlet implements Controller, ServletContextAware {

    private ServletContext servletContext;

    private RemoteService remoteService;

    private Class remoteServiceClass;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doPost(request, response);
        return null;
    }

    @Override
    public String processCall(String payload) throws SerializationException {
        try {
            RPCRequest rpcRequest = RPC.decodeRequest(payload, this.remoteServiceClass , this);
            // delegate work to the spring injected service
            onAfterRequestDeserialized(rpcRequest);
            //return  RPC.invokeAndEncodeResponse(this.remoteService, rpcRequest.getMethod(), rpcRequest.getParameters() ,new PortalLegacySerializationPolicy());
            return  RPC.invokeAndEncodeResponse(this.remoteService, rpcRequest.getMethod(), rpcRequest.getParameters() ,rpcRequest.getSerializationPolicy());
        } catch (IncompatibleRemoteServiceException ex) {
            getServletContext().log("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
            return RPC.encodeResponseForFailure(null, ex);
        }
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setRemoteService(RemoteService remoteService) {
        
//           Field fDelegate = myClass.getClass().getDeclaredField("delegate");
//        fDelegate.setAccessible(true);
//        fDelegate.set(this,remoteService);
        
        this.remoteService = remoteService;
        this.remoteServiceClass = this.remoteService.getClass();
    }

}
