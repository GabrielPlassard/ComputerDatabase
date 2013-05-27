package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Computer;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 27/05/13
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public interface ComputerDatabaseService {

    Set<Computer> allComputers();

}
