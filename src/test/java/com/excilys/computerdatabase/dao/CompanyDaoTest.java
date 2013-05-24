package com.excilys.computerdatabase.dao;


import com.excilys.computerdatabase.model.Company;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public class CompanyDaoTest {

    private CompanyDao companyDao = new JdbcCompanyDao();

    @Before
    public void setUp(){
        companyDao.deleteAll();

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

    @Test
    public void testGetAll(){
        assertEquals(10, companyDao.getAll().size());
    }

    @Test
    public void testAddCompany(){
        Company c = new Company("Excilys");
        companyDao.saveOrUpdate(c);
        assertEquals(11, companyDao.getAll().size());
    }

    @Test
    public void testUpdate(){
        Company c = new Company("Excilys");
        companyDao.saveOrUpdate(c);
        c.setName("eBusiness");
        companyDao.saveOrUpdate(c);
        assertEquals("eBusiness", companyDao.findById(c.getId()).getName());
        assertEquals(11, companyDao.getAll().size());
    }

}
