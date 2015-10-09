/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.portal.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Абстактный класс прородитель всех репозитариев 
 * 
 * @author Igor Salnikov 
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface AbstractRepository<T , ID extends Serializable>  extends JpaRepository<T, ID>,JpaSpecificationExecutor<T>{
    
}
