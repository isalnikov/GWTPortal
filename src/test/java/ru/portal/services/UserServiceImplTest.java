/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.services;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class UserServiceImplTest {

    @Resource
    private EntityManagerFactory emf;

    protected EntityManager em;

    @Resource
    private UserService userService;
    
    @Resource
    private RoleService roleService;

    public UserServiceImplTest() {
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
    @Ignore
    public void testFindAllUsers() {
        List<User> users = userService.findAll();
        assertTrue(users.size() == 2);
        
        System.out.println(users);
    }
    @Test
    public void testFindAllRoles() {
        List<Role> roles = roleService.findAll();
        System.out.println(roles);
    }



}
