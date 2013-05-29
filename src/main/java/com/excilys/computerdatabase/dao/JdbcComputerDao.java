package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Computer;
import com.sun.javafx.binding.StringFormatter;

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

    private static final String SELECT_QUERY = "SELECT * FROM computer %s WHERE computer.name LIKE ? ORDER BY ISNULL(%s),%s %s LIMIT ? OFFSET ?";
    private static final String JOIN_COMPANY = " LEFT JOIN company ON computer.company_id=company.id ";
    private static final String SELECT_NUMBER_MATCHING = "SELECT COUNT(id) FROM computer WHERE name LIKE ?";
    private static final String DELETE_BY_ID = "DELETE FROM computer WHERE id=?";

    private CompanyDao companyDao = JdbcCompanyDao.INSTANCE;
    private ThreadLocal<Connection> threadLocalConnection;

    @Override
    public void setConnection(Connection connection){
        threadLocalConnection = new ThreadLocal<Connection>();
        threadLocalConnection.set(connection);
    }

    @Override
    public Connection getConnection(){
        return threadLocalConnection.get();
    }

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

    @Override
    public void save(Computer computer) {
        PreparedStatement statement = null;
        ResultSet resultKey = null;
        try {
            statement =  threadLocalConnection.get().prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
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
        }
    }

    @Override
    public void update(Computer computer) {
        PreparedStatement statement = null;
        try {
            statement =  threadLocalConnection.get().prepareStatement(UPDATE_QUERY);
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
        }
    }


    @Override
    public List<Computer> getAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Computer> result = new ArrayList<Computer>();
        try {
            statement = threadLocalConnection.get().prepareStatement(GET_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(computerFromTuple(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
        }
        return result;
    }

    @Override
    public void deleteAll() {
        PreparedStatement statement = null;
        try {
            statement =  threadLocalConnection.get().prepareStatement(DELETE_ALL_QUERY);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            JdbcUtils.closeStatement(statement);
        }
    }

    @Override
    public Computer findById(int computerId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = threadLocalConnection.get().prepareStatement(FIND_BY_ID_QUERY);
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
        }
        return null;
    }

    @Override
    public List<Computer> getMatchingFromToWhithSortedByColumn(String namePattern, int firstIndice, int lastIndice, int columnId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Computer> result = new ArrayList<Computer>();
        try {
            statement = threadLocalConnection.get().prepareStatement(getSqlSelect(columnId));
            statement.setString(1,new StringBuilder("%").append(namePattern).append("%").toString());
            statement.setInt(2, lastIndice - firstIndice);
            statement.setInt(3, firstIndice);
            System.out.println("thread : "+Thread.currentThread().getName());
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
        }
        return result;
    }

    private String getSqlSelect(int columnId) {
        String leftJoin = "";
        if (Math.abs(columnId) == 5){
            leftJoin = JOIN_COMPANY;
        }
        String column = COLUMN_NAMES[Math.abs(columnId)];
        String order = "ASC";
        if (columnId < 0){
            order = "DESC";
        }
        return String.format(SELECT_QUERY, leftJoin, column, column, order );
    }

    @Override
    public int numberOfMatching(String namePattern) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int numberOfMatchings = 0;
        try {
            statement = threadLocalConnection.get().prepareStatement(SELECT_NUMBER_MATCHING);
            statement.setString(1,"%"+namePattern+"%");
            System.out.println("thread : "+Thread.currentThread().getName());
            System.out.println("query : "+statement.toString());
            resultSet = statement.executeQuery();
            resultSet.next();
            numberOfMatchings = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
        }
        return numberOfMatchings;
    }

    @Override
    public void deleteById(int computerId) {
        PreparedStatement statement = null;
        try {
            statement =  threadLocalConnection.get().prepareStatement(DELETE_BY_ID);
            statement.setInt(1,computerId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            JdbcUtils.closeStatement(statement);
        }
    }
}
