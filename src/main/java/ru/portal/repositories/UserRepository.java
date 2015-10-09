package ru.portal.repositories;

import ru.portal.entity.User;

public interface UserRepository extends AbstractRepository<User, Long>{
    
    User findOneByLogin(String login);
}
