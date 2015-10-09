
package ru.portal.services;

import ru.portal.entity.User;

public interface UserService extends AbstractService<User,Long> {
    
     User findOneByLogin(String login);
}
