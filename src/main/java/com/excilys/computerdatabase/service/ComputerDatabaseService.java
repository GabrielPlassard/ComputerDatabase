package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.*;

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
    void deleteComputerById(int computerId);
    ComputersAndTotalNumber indexGetQuery(String search, int sortedColumn, int firstComputerIndice, int lastComputerIndice);
    ComputerAndCompanies computerByIdAndCompanies(int computerId);
    void updateComputerAndSetCompany(Computer computer, int companyId);

}
