package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.JdbcCompanyDao;
import com.excilys.computerdatabase.dao.JdbcComputerDao;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 27/05/13
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public enum SimpleComputerDatabaseService implements ComputerDatabaseService {
    INSTANCE;

    private ComputerDao computerDao = JdbcComputerDao.INSTANCE;
    private CompanyDao  companyDao  = JdbcCompanyDao.INSTANCE;

    @Override
    public List<Computer> allComputers() {
        return computerDao.getAll();
    }

    @Override
    public List<Computer> getMatchingComputersFromToSortedByColumn(String namePattern, int firstIndice, int lastIndice, int columnId) {
        return computerDao.getMatchingFromToWhithSortedByColumn(namePattern, firstIndice, lastIndice, columnId);
    }

    @Override
    public int numberOfMatchingComputers(String namePattern) {
        return computerDao.numberOfMatching(namePattern);
    }

    @Override
    public List<Company> allCompanies() {
        return companyDao.getAll();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Company companyById(int id) {
        return companyDao.findById(id);
    }

    @Override
    public void saveOrUpdateComputer(Computer computer) {
        computerDao.saveOrUpdate(computer);
    }

    @Override
    public Computer computerById(int id) {
        return computerDao.findById(id);
    }


}
