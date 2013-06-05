package com.excilys.computerdatabase.queryresults;

import com.excilys.computerdatabase.model.Computer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 29/05/13
 * Time: 14:53
 * To change this template use File | Settings | File Templates.
 */
public class ComputersAndTotalNumber {
    private int numberOfMatchingComputers;
    private List<Computer> matchingComputers;

    public ComputersAndTotalNumber(List<Computer> computers, int numberOfMatchingComputers) {
        this.numberOfMatchingComputers = numberOfMatchingComputers;
        matchingComputers = computers;
    }

    public int getNumberOfMatchingComputers() {
        return numberOfMatchingComputers;
    }

    public List<Computer> getMatchingComputers() {
        return matchingComputers;
    }
}
