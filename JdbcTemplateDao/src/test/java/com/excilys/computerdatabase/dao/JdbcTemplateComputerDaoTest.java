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
@ContextConfiguration(locations = "classpath:jdbcTemplateContext.xml")
public class JdbcTemplateComputerDaoTest extends ComputerDaoTest{

    @Override
    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    @Autowired
    public void setComputerDao(ComputerDao computerDao) {
        this.computerDao = computerDao;
    }
}
