package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Company;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyDao {

    Company findByName(String name);
    List<Company> getAll();
    void saveOrUpdate(Company company);
    void deleteAll();
    Company findById(int companyId);
}
