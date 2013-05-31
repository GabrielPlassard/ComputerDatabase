package com.excilys.computerdatabase.service;

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

    List<Company> allCompanies();
    void createComputerAndSetCompany(Computer computer, int companyId);
    boolean deleteComputerById(int computerId);
    ComputersAndTotalNumber listOfComputers(String search, int sortedColumn, int firstComputerIndice, int lastComputerIndice);
    ComputerAndCompanies computerByIdAndCompanies(int computerId);
    boolean updateComputerAndSetCompany(Computer computer, int companyId);

}
