package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 12:20
 * To change this template use File | Settings | File Templates.
 */

//@Repository
public class JdbcCompanyDao implements CompanyDao{

    private final static Logger logger = LoggerFactory.getLogger(JdbcCompanyDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM company WHERE id=?";
    private static final String FIND_BY_NAME = "SELECT * FROM company WHERE name=?";
    private static final String SAVE = "INSERT INTO company(name) VALUES(?)";
    private static final String UPDATE = "UPDATE company SET name=? WHERE id=?";
    private static final String GET_ALL = "SELECT * FROM company ORDER BY name";
    private static final String DELETE = "DELETE FROM company";

    static Company companyFromTuple(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        long id = resultSet.getLong("company.id");
        String name = resultSet.getString("company.name");
        company.setId(id);
        company.setName(name);
        return company;
    }

    @Override
    public Company findById(long companyId)  throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = JdbcUtils.getConnection().prepareStatement(FIND_BY_ID);
            statement.setLong(1,companyId);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                return companyFromTuple(resultSet);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new DaoException(e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
        }
        return null;
    }

    @Override
    public Company findByName(String companyName)  throws DaoException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = JdbcUtils.getConnection().prepareStatement(FIND_BY_NAME);
            statement.setString(1, companyName);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                return companyFromTuple(resultSet);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new DaoException(e);
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
        }
        return null;
    }

    private void save(Company company)  throws DaoException{
        PreparedStatement statement = null;
        ResultSet resultKey = null;
        try {
            statement =  JdbcUtils.getConnection().prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, company.getName());
            statement.execute();
            resultKey = statement.getGeneratedKeys();
            resultKey.next();
            long id = resultKey.getLong(1);
            company.setId(id);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new DaoException(e);
        }finally {
            JdbcUtils.closeResultSet(resultKey);
            JdbcUtils.closeStatement(statement);
        }
    }

    private void update(Company company)  throws DaoException{
        PreparedStatement statement = null;
        try {
            statement =  JdbcUtils.getConnection().prepareStatement(UPDATE);
            statement.setString(1, company.getName());
            statement.setLong(2,company.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new DaoException(e);
        }finally {
            JdbcUtils.closeStatement(statement);
        }
    }

    @Override
    public List<Company> getAll()  throws DaoException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Company> result = new ArrayList<Company>();
        try {
            statement = JdbcUtils.getConnection().prepareStatement(GET_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(companyFromTuple(resultSet));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new DaoException(e);
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
        }
        return result;
    }


    @Override
    public void saveOrUpdate(Company company)  throws DaoException{
        if (findById(company.getId()) == null){
            save(company);
        }
        else{
            update(company);
        }
    }

    @Override
    public void deleteAll()  throws DaoException{
        PreparedStatement statement = null;
        try {
            statement =  JdbcUtils.getConnection().prepareStatement(DELETE);
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new DaoException(e);
        } finally {
            JdbcUtils.closeStatement(statement);
        }
    }
}