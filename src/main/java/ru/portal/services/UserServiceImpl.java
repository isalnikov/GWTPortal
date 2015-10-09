/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.portal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.portal.entity.User;
import ru.portal.repositories.UserRepository;


@Transactional
@Service("userService")
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements UserService {

    
   protected final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
        
    }

    @Override
    public User findOne(Long id) {
        return super.findOne(id); 
    }
    

    
    @Override
    public User findOneByLogin(String login) {
        return repository.findOneByLogin(login);

    }

}
