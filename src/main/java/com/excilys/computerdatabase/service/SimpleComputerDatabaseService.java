package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.*;
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
        JdbcUtils.openConnection();

        List<Company> companies =  companyDao.getAll();

        JdbcUtils.closeConnection();
        return companies;
    }

    @Override
    public void deleteComputerById(int computerId) {
        JdbcUtils.openConnection();

        computerDao.deleteById(computerId);

        JdbcUtils.closeConnection();
    }

    @Override
    public ComputersAndTotalNumber listOfComputers(String search, int sortedColumn, int firstComputerIndice, int lastComputerIndice) {
        JdbcUtils.openConnection();

        List<Computer> computers = computerDao.getMatchingFromToWhithSortedByColumn(search, firstComputerIndice, lastComputerIndice,sortedColumn);
        int matchingComputers = computerDao.numberOfMatching(search);
        ComputersAndTotalNumber result = new ComputersAndTotalNumber(computers,matchingComputers);

        JdbcUtils.closeConnection();
        return result;
    }

    @Override
    public ComputerAndCompanies computerByIdAndCompanies(int computerId) {
        JdbcUtils.openConnection();

        Computer computer = computerDao.findById(computerId);
        List<Company> companies = companyDao.getAll();
        ComputerAndCompanies result = new ComputerAndCompanies(computer,companies);

        JdbcUtils.closeConnection();
        return result;
    }

    @Override
    public void createComputerAndSetCompany(Computer computer, int companyId) {
        JdbcUtils.openConnection();

        Company company = companyDao.findById(companyId);
        computer.setCompany(company);
        computerDao.save(computer);

        JdbcUtils.closeConnection();
    }

    @Override
    public void updateComputerAndSetCompany(Computer computer, int companyId) {
        JdbcUtils.openConnection();

        Company company = companyDao.findById(companyId);
        computer.setCompany(company);
        computerDao.update(computer);

        JdbcUtils.closeConnection();
    }


}
