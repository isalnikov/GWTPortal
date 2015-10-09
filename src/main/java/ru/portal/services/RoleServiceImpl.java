
package ru.portal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.portal.entity.Role;
import ru.portal.repositories.RoleRepository;

@Transactional
@Service("roleService")
public class RoleServiceImpl extends AbstractServiceImpl<Role, Long> implements RoleService {

    protected  final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
