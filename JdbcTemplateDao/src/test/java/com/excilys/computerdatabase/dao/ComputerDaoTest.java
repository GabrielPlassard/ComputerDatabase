package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
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
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:JdbcTemplateDao/src/main/resources/jdbcTemplateContext.xml")
public class ComputerDaoTest {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ComputerDao computerDao;

    @Before
    public void setUp() throws DaoException {
        companyDao.openConnection();
        companyDao.deleteAll();
        insertSomeCompanies();

        computerDao.deleteAll();
        insertSomeComputers();
    }

    @After
    public void tearDown(){
        companyDao.closeConnection();
    }

    @Test
    public void testGetAll() throws DaoException {
        assertEquals(20, computerDao.getAll().size());
    }

    @Test
    public void testAddComputer() throws DaoException {
        Computer computer = new Computer("Mon ordinateur");
        computerDao.save(computer);
        assertEquals(21, computerDao.getAll().size());
    }

    @Test
    public void testUpdate() throws DaoException {
        Computer computer = new Computer("Mon ordinateur");
        computerDao.save(computer);
        computer.setName("Mon nouvel ordinateur");
        computerDao.update(computer);
        assertEquals("Mon nouvel ordinateur", computerDao.findById(computer.getId()).getName());
        assertEquals(21, computerDao.getAll().size());
    }

    @Test
    public void testGetWithBadId() throws DaoException {
        assertEquals(null, computerDao.findById(-15));
    }

    private void insertSomeComputers() throws DaoException {
        computerDao.save(new Computer("MacBook Pro 15.4 inch", null, null, null));
        computerDao.save(new Computer("CM-2a", null, null, null));
        computerDao.save(new Computer("CM-200", null, null, null));
        computerDao.save(new Computer("CM-5e", null, null, null));
        computerDao.save(new Computer("CM-5", "1991-01-01", null, null));
        computerDao.save(new Computer("MacBook Pro", "2006-01-10", null, null));
        computerDao.save(new Computer("Apple IIe", null, null, null));
        computerDao.save(new Computer("Apple IIc", null, null, null));
        computerDao.save(new Computer("Apple IIGS", null, null, null));
        computerDao.save(new Computer("Apple IIc Plus", null, null, null));
        computerDao.save(new Computer("Apple II Plus", null, null, null));
        computerDao.save(new Computer("Apple III", "1980-05-01", "1984-04-01", null));
        computerDao.save(new Computer("Apple Lisa", null, null, null));
        computerDao.save(new Computer("CM-2", null, null, null));
        computerDao.save(new Computer("Connection Machine", "1987-01-01", null, null));
        computerDao.save(new Computer("Apple II", "1977-04-01", "1993-10-01", null));
        computerDao.save(new Computer("Apple III Plus", "1983-12-01", "1984-04-01", null));
        computerDao.save(new Computer("COSMAC ELF", null, null, null));
        computerDao.save(new Computer("COSMAC VIP", "1977-01-01", null, null));
        computerDao.save(new Computer("ELF II", "1977-01-01", null, null));
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
