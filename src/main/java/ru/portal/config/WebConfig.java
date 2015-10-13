/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.portal.gwt.gwtportal.server.GWTServiceImpl;
import ru.portal.gwt.gwtportal.server.GwtRpcController;

@Configuration
@EnableWebMvc
@ComponentScan({
    "ru.portal.config",
    "ru.portal.controllers",
    "ru.portal.repositories",
    "ru.portal.services",
    "ru.portal.gwt.gwtportal.server"
})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public SimpleUrlHandlerMapping GWTUrlHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MAX_VALUE - 2);

        Properties urlProperties = new Properties();
        //TODO значит нужно замапить все имена на этот контроллер 
        //и в нем сделать массив контроллеров для обработки запросов
        urlProperties.put("/**/gwt.rpc", "quoteController");

        mapping.setMappings(urlProperties);
        return mapping;
    }

    @Bean
    public GWTServiceImpl gwtServiceImpl() {
        return new GWTServiceImpl();
    }

    @Bean(name = "quoteController")
    public GwtRpcController gwtRpcController() {
        GwtRpcController controller = new GwtRpcController();
        controller.setRemoteService(gwtServiceImpl());
        return controller;
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setDefaultErrorView("404");
        resolver.setDefaultStatusCode(HttpStatus.NOT_FOUND.value());
        resolver.setOrder(1);
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/media/favicon.ico").setCachePeriod(31556926);
        registry.addResourceHandler("/application/**").addResourceLocations("/application/").setCachePeriod(31556926);
    }

}
