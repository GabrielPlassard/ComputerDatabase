package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Computer;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public enum JdbcComputerDao implements ComputerDao {
    INSTANCE;
    private static final String[] COLUMN_NAMES = {"","computer.id","computer.name","computer.introduced","computer.discontinued","company.name"};

    private static final String SAVE_QUERY = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE computer SET name=?,introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String GET_ALL_QUERY = "SELECT * FROM computer";
    private static final String DELETE_ALL_QUERY = "DELETE FROM computer";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM computer WHERE id=?";
    private static final String SELECT_COMPUTER = "SELECT * FROM computer";
    private static final String JOIN_COMPANY = " LEFT JOIN company ON computer.company_id=company.id ";
    private static final String WHERE_AND_ORDER = " WHERE computer.name LIKE ? ORDER BY ";
    private static final String DESC = " DESC ";
    private static final String LIMIT_OFFSET = " LIMIT ? OFFSET ?";
    private static final String SELECT_NUMBER_MATCHING = "SELECT COUNT(id) FROM computer WHERE name LIKE ?";
    private static final String DELETE_BY_ID = "DELETE FROM computer WHERE id=?";

    private CompanyDao companyDao = JdbcCompanyDao.INSTANCE;

    private Computer computerFromTuple(ResultSet resultSet) throws SQLException{
        Computer computer = new Computer();
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Date introduced = resultSet.getDate("introduced");
        Date discontinued = resultSet.getDate("discontinued");
        int companyId = resultSet.getInt("company_id");
        computer.setId(id);
        computer.setName(name);
        computer.setIntroduced(introduced);
        computer.setDiscontinued(discontinued);
        computer.setCompany(companyDao.findById(companyId));
        return computer;
    }

    private void save(Computer computer) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultKey = null;
        try {
            connection = JdbcUtils.getConnection();
            statement =  connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, computer.getName());
            statement.setDate(2, JdbcUtils.dateUtilToSql(computer.getIntroduced()));
            statement.setDate(3, JdbcUtils.dateUtilToSql(computer.getDiscontinued()));
            if (computer.getCompany() != null){
                statement.setInt(4, computer.getCompany().getId());
            }
            else{
                statement.setNull(4,java.sql.Types.INTEGER);
            }
            statement.execute();
            resultKey = statement.getGeneratedKeys();
            resultKey.next();
            int id = resultKey.getInt(1);
            computer.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeResultSet(resultKey);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }

    private void update(Computer computer) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JdbcUtils.getConnection();
            statement =  connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, computer.getName());
            statement.setDate(2, JdbcUtils.dateUtilToSql(computer.getIntroduced()));
            statement.setDate(3, JdbcUtils.dateUtilToSql(computer.getDiscontinued()));
            if (computer.getCompany() != null){
                statement.setInt(4, computer.getCompany().getId());
            }
            else{
                statement.setNull(4,java.sql.Types.INTEGER);
            }
            statement.setInt(5,computer.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }


    @Override
    public List<Computer> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Computer> result = new ArrayList<Computer>();
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.prepareStatement(GET_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(computerFromTuple(resultSet));
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
    public void saveOrUpdate(Computer computer) {
        if (findById(computer.getId()) == null){
            save(computer);
        }
        else{
            update(computer);
        }
    }

    @Override
    public void deleteAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JdbcUtils.getConnection();
            statement =  connection.prepareStatement(DELETE_ALL_QUERY);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }

    @Override
    public Computer findById(int computerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setInt(1,computerId);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                return computerFromTuple(resultSet);
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
    public List<Computer> getMatchingFromToWhithSortedByColumn(String namePattern, int firstIndice, int lastIndice, int columnId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Computer> result = new ArrayList<Computer>();
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.prepareStatement(getSqlSelect(columnId));
            statement.setString(1,"%"+namePattern+"%");
            statement.setInt(2, lastIndice - firstIndice);
            statement.setInt(3, firstIndice);
            System.out.println("query : " + statement.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(computerFromTuple(resultSet));
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

    private String getSqlSelect(int columnId) {
        StringBuilder request = new StringBuilder(SELECT_COMPUTER);
        if (Math.abs(columnId) == 5){
            request.append(JOIN_COMPANY);
        }
        request.append(WHERE_AND_ORDER);
        request.append(COLUMN_NAMES[Math.abs(columnId)]);
        if (columnId < 0){
            request.append(DESC);
        }
        request.append(LIMIT_OFFSET);
        return request.toString();
    }

    @Override
    public int numberOfMatching(String namePattern) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int numberOfMatchings = 0;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.prepareStatement(SELECT_NUMBER_MATCHING);
            statement.setString(1,"%"+namePattern+"%");
            System.out.println("query : "+statement.toString());
            resultSet = statement.executeQuery();
            resultSet.next();
            numberOfMatchings = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return numberOfMatchings;
    }

    @Override
    public void deleteById(int computerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JdbcUtils.getConnection();
            statement =  connection.prepareStatement(DELETE_BY_ID);
            statement.setInt(1,computerId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }
}
