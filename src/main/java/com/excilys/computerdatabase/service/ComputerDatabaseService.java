package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Computer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 27/05/13
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public interface ComputerDatabaseService {

    List<Computer> allComputers();
    List<Computer> getMatchingComputersFromToSortedByColumn(String namePattern, int firstIndice, int lastIndice, int columnId);
    int numberOfMatchingComputers(String namePattern);
}
