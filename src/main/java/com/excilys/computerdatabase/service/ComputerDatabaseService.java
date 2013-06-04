package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.exceptions.ServiceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputerAndCompanies;
import com.excilys.computerdatabase.queryresults.ComputersAndTotalNumber;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 27/05/13
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public interface ComputerDatabaseService {

    List<Company> allCompanies() throws ServiceException;
    void createComputerAndSetCompany(Computer computer, long companyId) throws ServiceException;
    boolean deleteComputerById(long computerId) throws ServiceException;
    ComputersAndTotalNumber listOfComputers(String search, int sortedColumn, int firstComputerIndice, int lastComputerIndice) throws ServiceException;
    ComputerAndCompanies computerByIdAndCompanies(long computerId) throws ServiceException;
    boolean updateComputerAndSetCompany(Computer computer, long companyId) throws ServiceException;

}
