package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 04/06/13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class JdbcTemplateCompanyDao implements CompanyDao {

    private final static String SELECT_ALL = "SELECT * FROM company";
    private final static String FIND_BY_NAME = "SELECT * FROM company WHERE company.name=?";
    private final static String FIND_BY_ID = "SELECT * FROM company WHERE company.id=?";
    private final static String UPDATE = "UPDATE company SET name=? WHERE id=?";
    private final static String DELETE = "DELETE FROM company";

    private JdbcTemplate template;
    private SimpleJdbcInsert insertCompany;

    @Autowired
    public void setDataSource(DriverManagerDataSource dataSource) {
        template = new JdbcTemplate(dataSource);
        insertCompany = new SimpleJdbcInsert(dataSource).withTableName("company").usingGeneratedKeyColumns("company.id");
    }


    @Override
    @Transactional(readOnly = true)
    public Company findByName(String name) throws DaoException {
        List<Company> results = template.query(FIND_BY_NAME, new Object[]{name}, new CompanyRowMapper());
        if (results.size() == 0) return null;
        return results.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> getAll() throws DaoException {
        return template.query(SELECT_ALL, new CompanyRowMapper());
    }

    @Override
    public void saveOrUpdate(Company company) throws DaoException {
        if (findById(company.getId()) == null) {
            save(company);
        } else {
            update(company);
        }
    }

    @Transactional
    private void save(Company company) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", company.getName());
        long id = (Long) insertCompany.executeAndReturnKey(parameters);
        company.setId(id);
    }

    @Transactional
    private void update(Company company) {
        template.update(UPDATE, new Object[]{company.getName(), company.getId()});
    }

    @Override
    @Transactional
    public void deleteAll() throws DaoException {
        template.update(DELETE);
    }

    @Override
    @Transactional(readOnly = true)
    public Company findById(long companyId) throws DaoException {
        List<Company> results = template.query(FIND_BY_ID, new Object[]{companyId}, new CompanyRowMapper());
        if (results.size() == 0) return null;
        return results.get(0);
    }
}
