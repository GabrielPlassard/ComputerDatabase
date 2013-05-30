package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Computer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private static final String UPDATE_QUERY = "UPDATE computer SET name=?,introduced=?, discontinued=?, company_id=? WHERE computer.id=?";
    private static final String GET_ALL_QUERY = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private static final String DELETE_ALL_QUERY = "DELETE FROM computer";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=?";

    private static final String SELECT_QUERY = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE ? ORDER BY ISNULL(%s),%s %s LIMIT ? OFFSET ?";
    private static final String SELECT_NUMBER_MATCHING = "SELECT COUNT(id) FROM computer WHERE name LIKE ?";
    private static final String DELETE_BY_ID = "DELETE FROM computer WHERE id=?";

    static Computer computerFromTuple(ResultSet resultSet) throws SQLException{
        Computer computer = new Computer();
        int id = resultSet.getInt(COLUMN_NAMES[1]);
        String name = resultSet.getString(COLUMN_NAMES[2]);
        Date introduced = resultSet.getDate(COLUMN_NAMES[3]);
        Date discontinued = resultSet.getDate(COLUMN_NAMES[4]);
        computer.setId(id);
        computer.setName(name);
        computer.setIntroduced(introduced);
        computer.setDiscontinued(discontinued);
        computer.setCompany(JdbcCompanyDao.companyFromTuple(resultSet));
        return computer;
    }

    public void save(Computer computer) {
        PreparedStatement statement = null;
        ResultSet resultKey = null;
        try {
            statement =  JdbcUtils.getConnection().prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
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

    public void update(Computer computer) {
        PreparedStatement statement = null;
        try {
            statement =  JdbcUtils.getConnection().prepareStatement(UPDATE_QUERY);
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
            statement = JdbcUtils.getConnection().prepareStatement(GET_ALL_QUERY);
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
            statement =  JdbcUtils.getConnection().prepareStatement(DELETE_ALL_QUERY);
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
            statement = JdbcUtils.getConnection().prepareStatement(FIND_BY_ID_QUERY);
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
            statement = JdbcUtils.getConnection().prepareStatement(getSqlSelect(columnId));
            statement.setString(1, new StringBuilder("%").append(namePattern).append("%").toString());
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
        }
        return result;
    }

    private String getSqlSelect(int columnId) {
        String column = COLUMN_NAMES[Math.abs(columnId)];
        String order = "ASC";
        if (columnId < 0){
            order = "DESC";
        }
        return String.format(SELECT_QUERY, column, column, order );
    }

    @Override
    public int numberOfMatching(String namePattern) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int numberOfMatchings = 0;
        try {
            statement = JdbcUtils.getConnection().prepareStatement(SELECT_NUMBER_MATCHING);
            statement.setString(1, "%" + namePattern + "%");
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
            statement =  JdbcUtils.getConnection().prepareStatement(DELETE_BY_ID);
            statement.setInt(1,computerId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            JdbcUtils.closeStatement(statement);
        }
    }
}