package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Company;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 12:20
 * To change this template use File | Settings | File Templates.
 */
public enum JdbcCompanyDao implements CompanyDao{
    INSTANCE;

    private Company companyFromTuple(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        company.setId(id);
        company.setName(name);
        return company;
    }

    @Override
    public Company findById(int companyId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.prepareStatement("SELECT * FROM company WHERE id=?");
            statement.setInt(1,companyId);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                return companyFromTuple(resultSet);
            }


        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Company findByName(String companyName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.prepareStatement("SELECT * FROM company WHERE name=?");
            statement.setString(1, companyName);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                return companyFromTuple(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    private void save(Company company) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JdbcUtils.getConnection();
            statement =  connection.prepareStatement("INSERT INTO company(name) VALUES(?)");
            statement.setString(1, company.getName());
            statement.execute();
            int id = findByName(company.getName()).getId();
            company.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }

    private void update(Company company) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JdbcUtils.getConnection();
            statement =  connection.prepareStatement("UPDATE company SET name=? WHERE id=?");
            statement.setString(1, company.getName());
            statement.setInt(2,company.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }

    @Override
    public Set<Company> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Set<Company> result = new HashSet<Company>();
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.prepareStatement("SELECT * FROM company");
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(companyFromTuple(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return result;
    }


    @Override
    public void saveOrUpdate(Company company) {
        if (findById(company.getId()) == null){
            save(company);
        }
        else{
            update(company);
        }
    }

    @Override
    public void deleteAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JdbcUtils.getConnection();
            statement =  connection.prepareStatement("DELETE FROM company");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }
}
