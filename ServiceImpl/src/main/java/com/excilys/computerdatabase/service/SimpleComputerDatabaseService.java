package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputerAndCompanies;
import com.excilys.computerdatabase.queryresults.ComputersAndTotalNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 27/05/13
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */

@Service
public class SimpleComputerDatabaseService implements ComputerDatabaseService {

    @Autowired
    private ComputerDao computerDao;

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> allCompanies() {

        List<Company> companies = new ArrayList<Company>();
        try {
            computerDao.openConnection();
            companies = companyDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            computerDao.closeConnection();
        }
        return companies;
    }

    @Override
    public boolean deleteComputerById(long computerId) {
        boolean succesfull = false;

        try {
            computerDao.openConnection();
            computerDao.deleteById(computerId);
            succesfull = computerDao.findById(computerId) == null;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            computerDao.closeConnection();
        }
        return succesfull;
    }

    @Override
    public ComputersAndTotalNumber listOfComputers(String search, int sortedColumn, int firstComputerIndice, int lastComputerIndice) {
        try {
            computerDao.openConnection();
            List<Computer> computers = computerDao.getMatchingFromToWithSortedByColumn(search, firstComputerIndice, lastComputerIndice, sortedColumn);
            int matchingComputers = computerDao.numberOfMatching(search);
            ComputersAndTotalNumber result = new ComputersAndTotalNumber(computers, matchingComputers);
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            computerDao.closeConnection();
        }
    }

    @Override
    public ComputerAndCompanies computerByIdAndCompanies(long computerId) {
        try {
            computerDao.openConnection();

            Computer computer = computerDao.findById(computerId);
            List<Company> companies = companyDao.getAll();
            ComputerAndCompanies result = new ComputerAndCompanies(computer, companies);
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            computerDao.closeConnection();
        }
    }

    @Override
    public void createComputerAndSetCompany(Computer computer, long companyId) {
        try {
            computerDao.openConnection();

            Company company = companyDao.findById(companyId);
            computer.setCompany(company);
            computerDao.save(computer);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            computerDao.closeConnection();
        }
    }

    @Override
    public boolean updateComputerAndSetCompany(Computer computer, long companyId) {
        boolean succesfull = false;
        try {
            computerDao.openConnection();
            Company company = companyDao.findById(companyId);
            computer.setCompany(company);
            computerDao.update(computer);
            succesfull = true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            computerDao.closeConnection();
        }
        return succesfull;
    }


}
