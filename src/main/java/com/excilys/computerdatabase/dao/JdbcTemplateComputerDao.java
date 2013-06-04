package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.model.Computer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 04/06/13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class JdbcTemplateComputerDao implements ComputerDao{
    @Override
    public List<Computer> getAll() throws DaoException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(Computer computer) throws DaoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Computer computer) throws DaoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteAll() throws DaoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Computer findById(int computerId) throws DaoException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Computer> getMatchingFromToWithSortedByColumn(String namePattern, int firstIndice, int lastIndice, int columnId) throws DaoException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int numberOfMatching(String namePattern) throws DaoException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteById(int computerId) throws DaoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
