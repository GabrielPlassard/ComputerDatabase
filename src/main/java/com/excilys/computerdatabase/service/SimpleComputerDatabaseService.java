package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.JdbcComputerDao;
import com.excilys.computerdatabase.model.Computer;

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

    @Override
    public List<Computer> allComputers() {
        return computerDao.getAll();
    }

    @Override
    public List<Computer> getMatchingComputersFromToSortedByColumn(String namePattern, int firstIndice, int lastIndice, int columnId) {
        return computerDao.getMatchingFromToWhithSortedByColumn(namePattern, firstIndice, lastIndice, columnId);
    }

    @Override
    public int numberOfMatchingComputers(String namePattern) {
        return computerDao.numberOfMatching(namePattern);
    }


}
