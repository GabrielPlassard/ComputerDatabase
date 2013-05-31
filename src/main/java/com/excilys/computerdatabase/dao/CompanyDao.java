package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.model.Company;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyDao {
    Company findByName(String name) throws DaoException;
    List<Company> getAll() throws DaoException;
    void saveOrUpdate(Company company) throws DaoException;
    void deleteAll() throws DaoException;
    Company findById(int companyId) throws DaoException;
}
