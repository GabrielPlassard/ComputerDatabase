package com.excilys.computerdatabase.dao;


import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.model.Company;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/test/resources/testContext.xml")
public class CompanyDaoTest {

    @Autowired
    private CompanyDao companyDao;

    @Before
    public void setUp() throws DaoException {
        JdbcUtils.openConnection();
        companyDao.deleteAll();
        insertSomeCompanies();
    }

    @After
    public void tearDown(){
        JdbcUtils.closeConnection();
    }

    @Test
    public void testGetAll() throws DaoException {
        assertEquals(10, companyDao.getAll().size());
    }

    @Test
    public void testAddCompany() throws DaoException {
        Company c = new Company("Excilys");
        companyDao.saveOrUpdate(c);
        assertEquals(11, companyDao.getAll().size());
    }

    @Test
    public void testUpdate() throws DaoException {
        Company c = new Company("Excilys");
        companyDao.saveOrUpdate(c);
        c.setName("eBusiness");
        companyDao.saveOrUpdate(c);
        assertEquals("eBusiness", companyDao.findById(c.getId()).getName());
        assertEquals(11, companyDao.getAll().size());
    }

    @Test
    public void testGetWithBadId() throws DaoException {
        assertEquals(null, companyDao.findById(-15));
    }


    private void insertSomeCompanies() throws DaoException {
        companyDao.saveOrUpdate(new Company("Apple Inc."));
        companyDao.saveOrUpdate(new Company("RCA"));
        companyDao.saveOrUpdate(new Company("Thinking Machines"));
        companyDao.saveOrUpdate(new Company("Netronics"));
        companyDao.saveOrUpdate(new Company("Tandy Corporation"));
        companyDao.saveOrUpdate(new Company("Commodore International"));
        companyDao.saveOrUpdate(new Company("MOS Technology"));
        companyDao.saveOrUpdate(new Company("Micro Instrumentation and Telemetry Systems"));
        companyDao.saveOrUpdate(new Company("IMS Associates, Inc."));
        companyDao.saveOrUpdate(new Company("Digital Equipment Corporation"));
    }

}
