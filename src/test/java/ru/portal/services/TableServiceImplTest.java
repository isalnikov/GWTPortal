/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.metamodel.EntityType;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.portal.entity.Role;
import ru.portal.entity.User;

/**
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
@WebAppConfiguration
public class TableServiceImplTest {

    @Resource
    private TableService tableService;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    public TableServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetTableOrViewMetaData() {
        System.out.println("getTableOrViewMetaData");
        String tableOrViewName = "Users";
        List<String> expResult = tableService.getTableOrViewMetaData(tableOrViewName);
        //assertEquals(expResult.size(), 3);
        System.out.println(expResult);
    }

    @Test
    public void testFindAll() {

        List<User> users = new ArrayList<>();

        for (int i = 0; i < 150; i++) {
            users.add(new User("admin" + i, "admin", true));
        }

        userService.save(users);

        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            roles.add(new Role("admin" + i));
        }

        roleService.save(roles);

        System.out.println("findAll");
        String tableOrViewName = User.class.getCanonicalName();
        System.out.println(tableOrViewName);
        Pageable pageable = null;
        Page<List<String>> result = tableService.findAll(tableOrViewName, pageable);
        System.out.println("findAll");
        tableOrViewName = Role.class.getCanonicalName();
        System.out.println(tableOrViewName);
        pageable = new PageRequest(2, 10);
        tableService.findAll(tableOrViewName, pageable);
    }

}
