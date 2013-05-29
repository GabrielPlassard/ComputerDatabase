package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.JdbcCompanyDao;
import com.excilys.computerdatabase.dao.JdbcComputerDao;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputerAndCompanies;
import com.excilys.computerdatabase.queryresults.ComputersAndTotalNumber;

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
    public List<Company> allCompanies() {
        return companyDao.getAll();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteComputerById(int computerId) {
        computerDao.deleteById(computerId);
    }

    @Override
    public ComputersAndTotalNumber indexGetQuery(String search, int sortedColumn, int firstComputerIndice, int lastComputerIndice) {

        List<Computer> computers = computerDao.getMatchingFromToWhithSortedByColumn(search, firstComputerIndice, lastComputerIndice,sortedColumn);
        int matchingComputers = computerDao.numberOfMatching(search);
        ComputersAndTotalNumber result = new ComputersAndTotalNumber(computers,matchingComputers);

        return result;
    }

    @Override
    public ComputerAndCompanies computerByIdAndCompanies(int computerId) {
        Computer computer = computerDao.findById(computerId);
        List<Company> companies = companyDao.getAll();
        ComputerAndCompanies result = new ComputerAndCompanies(computer,companies);
        return result;
    }

    @Override
    public void createComputerAndSetCompany(Computer computer, int companyId) {
        Company company = companyDao.findById(companyId);
        computer.setCompany(company);
        computerDao.save(computer);
    }

    @Override
    public void updateComputerAndSetCompany(Computer computer, int companyId) {
        Company company = companyDao.findById(companyId);
        computer.setCompany(company);
        computerDao.update(computer);
    }


}
