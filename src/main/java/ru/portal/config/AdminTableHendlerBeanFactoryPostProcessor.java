/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;


/**
 * реализация
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 */
public class AdminTableHendlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                System.out.println(beanClassName);
                if (beanClassName != null) {
                    Class<?> beanClass = Class.forName(beanClassName);
                    if (beanClass != null) {
                 ///           System.out.println(beanClassName);
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

        }

    }

}
