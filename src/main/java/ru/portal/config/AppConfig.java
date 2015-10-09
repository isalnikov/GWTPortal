package ru.portal.config;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    
    @Bean
    public static AdminTableHendlerBeanFactoryPostProcessor adminTableHendlerBeanFactoryPostProcessor() {
        return new AdminTableHendlerBeanFactoryPostProcessor();
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        return configurer;
    }

   
}
