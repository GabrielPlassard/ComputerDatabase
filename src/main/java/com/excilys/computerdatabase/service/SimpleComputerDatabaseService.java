package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.*;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.exceptions.ServiceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputerAndCompanies;
import com.excilys.computerdatabase.queryresults.ComputersAndTotalNumber;

import java.util.ArrayList;
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

        List<Company> companies = new ArrayList<Company>();
        try {
            JdbcUtils.openConnection();
            companies = companyDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }finally {
            JdbcUtils.closeConnection();
        }
        return companies;
    }

    @Override
    public boolean deleteComputerById(int computerId) {
        boolean succesfull = false;

        try{
            JdbcUtils.openConnection();
            computerDao.deleteById(computerId);
            succesfull = computerDao.findById(computerId) == null;
        }  catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            JdbcUtils.closeConnection();
        }
        return succesfull;
    }

    @Override
    public ComputersAndTotalNumber listOfComputers(String search, int sortedColumn, int firstComputerIndice, int lastComputerIndice) {
        try{
            JdbcUtils.openConnection();
            List<Computer> computers = computerDao.getMatchingFromToWhithSortedByColumn(search, firstComputerIndice, lastComputerIndice,sortedColumn);
            int matchingComputers = computerDao.numberOfMatching(search);
            ComputersAndTotalNumber result = new ComputersAndTotalNumber(computers,matchingComputers);
            return result;
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally{
            JdbcUtils.closeConnection();
        }
    }

    @Override
    public ComputerAndCompanies computerByIdAndCompanies(int computerId) {
        try{
            JdbcUtils.openConnection();

            Computer computer = computerDao.findById(computerId);
            List<Company> companies = companyDao.getAll();
            ComputerAndCompanies result = new ComputerAndCompanies(computer,companies);
            return result;
        }catch (DaoException e){
            throw new ServiceException(e);
        }finally {
              JdbcUtils.closeConnection();
        }
    }

    @Override
    public void createComputerAndSetCompany(Computer computer, int companyId) {
        try{
            JdbcUtils.openConnection();

            Company company = companyDao.findById(companyId);
            computer.setCompany(company);
            computerDao.save(computer);
        }catch (DaoException e){
            throw new ServiceException(e);
        }finally {
            JdbcUtils.closeConnection();
        }
    }

    @Override
    public boolean updateComputerAndSetCompany(Computer computer, int companyId) {
        boolean succesfull = false;
        try{
            JdbcUtils.openConnection();
            Company company = companyDao.findById(companyId);
            computer.setCompany(company);
            computerDao.update(computer);
            succesfull = true;
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        finally {
            JdbcUtils.closeConnection();
        }
        return succesfull;
    }


}
