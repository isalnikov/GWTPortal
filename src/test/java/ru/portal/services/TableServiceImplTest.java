/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.services;

import java.util.List;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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

}
