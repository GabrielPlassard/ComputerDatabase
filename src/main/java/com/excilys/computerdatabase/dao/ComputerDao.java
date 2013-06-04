package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.model.Computer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public interface ComputerDao {

    List<Computer> getAll() throws DaoException;
    void save(Computer computer) throws DaoException;
    void update(Computer computer) throws DaoException;
    void deleteAll() throws DaoException;
    Computer findById(int computerId) throws DaoException;
    List<Computer> getMatchingFromToWithSortedByColumn(String namePattern, int firstIndice, int lastIndice, int columnId) throws DaoException;
    int numberOfMatching(String namePattern) throws DaoException;
    void deleteById(int computerId) throws DaoException;
}
