package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 04/06/13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class CompanyRowMapper implements RowMapper{

    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong("company.id"));
        company.setName(resultSet.getString("company.name"));
        return company;
    }
}
