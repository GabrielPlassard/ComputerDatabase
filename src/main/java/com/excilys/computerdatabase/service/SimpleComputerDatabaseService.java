package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.*;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputerAndCompanies;
import com.excilys.computerdatabase.queryresults.ComputersAndTotalNumber;

import java.sql.Connection;
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
        Connection connection = JdbcUtils.getConnection();
        computerDao.setConnection(connection);
        companyDao.setConnection(connection);

        List<Company> companies =  companyDao.getAll();

        JdbcUtils.closeConnection(connection);
        return companies;
    }

    @Override
    public void deleteComputerById(int computerId) {
        Connection connection = JdbcUtils.getConnection();
        computerDao.setConnection(connection);
        companyDao.setConnection(connection);

        computerDao.deleteById(computerId);

        JdbcUtils.closeConnection(connection);
    }

    @Override
    public ComputersAndTotalNumber listOfComputers(String search, int sortedColumn, int firstComputerIndice, int lastComputerIndice) {
        Connection connection = JdbcUtils.getConnection();
        computerDao.setConnection(connection);
        companyDao.setConnection(connection);

        List<Computer> computers = computerDao.getMatchingFromToWhithSortedByColumn(search, firstComputerIndice, lastComputerIndice,sortedColumn);
        int matchingComputers = computerDao.numberOfMatching(search);
        ComputersAndTotalNumber result = new ComputersAndTotalNumber(computers,matchingComputers);

        JdbcUtils.closeConnection(connection);
        return result;
    }

    @Override
    public ComputerAndCompanies computerByIdAndCompanies(int computerId) {
        Connection connection = JdbcUtils.getConnection();
        computerDao.setConnection(connection);
        companyDao.setConnection(connection);

        Computer computer = computerDao.findById(computerId);
        List<Company> companies = companyDao.getAll();
        ComputerAndCompanies result = new ComputerAndCompanies(computer,companies);

        JdbcUtils.closeConnection(connection);
        return result;
    }

    @Override
    public void createComputerAndSetCompany(Computer computer, int companyId) {
        Connection connection = JdbcUtils.getConnection();
        computerDao.setConnection(connection);
        companyDao.setConnection(connection);

        Company company = companyDao.findById(companyId);
        computer.setCompany(company);
        computerDao.save(computer);

        JdbcUtils.closeConnection(connection);
    }

    @Override
    public void updateComputerAndSetCompany(Computer computer, int companyId) {
        Connection connection = JdbcUtils.getConnection();
        computerDao.setConnection(connection);
        companyDao.setConnection(connection);

        Company company = companyDao.findById(companyId);
        computer.setCompany(company);
        computerDao.update(computer);

        JdbcUtils.closeConnection(connection);
    }


}
