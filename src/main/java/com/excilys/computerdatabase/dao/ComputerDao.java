package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Computer;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public interface ComputerDao {

    List<Computer> getAll();
    void saveOrUpdate(Computer computer);
    void deleteAll();
    Computer findById(int computerId);
    List<Computer> getMatchingFromToWhith(String namePattern, int firstIndice, int lastIndice);
    int numberOfMatching(String namePattern);
}
