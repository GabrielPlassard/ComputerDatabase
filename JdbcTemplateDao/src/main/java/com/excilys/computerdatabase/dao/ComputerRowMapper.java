package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 04/06/13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class ComputerRowMapper implements RowMapper {

    @Override
    public Computer mapRow(ResultSet resultSet, int i) throws SQLException {
        Computer computer = new Computer();
        computer.setId(resultSet.getLong("computer.id"));
        computer.setName(resultSet.getString("computer.name"));
        computer.setIntroduced(resultSet.getDate("computer.introduced"));
        computer.setDiscontinued(resultSet.getDate("computer.discontinued"));
        computer.setCompany(new CompanyRowMapper().mapRow(resultSet, i));
        return computer;
    }
}
