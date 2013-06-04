package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
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
public class JdbcTemplateComputerDao implements ComputerDao{

    private static final String[] COLUMN_NAMES = {"","computer.id","computer.name","computer.introduced","computer.discontinued","company.name"};

    private static final String SELECT_ALL = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private static final String UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String DELETE_ALL = "DELETE FROM computer";
    private final static String FIND_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=?";
    private static final String DELETE_BY_ID = "DELETE FROM computer WHERE id=?";
    private static final String SELECT_QUERY = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE ? ORDER BY ISNULL(%s),%s %s LIMIT ? OFFSET ?";
    private static final String SELECT_NUMBER_MATCHING = "SELECT COUNT(id) FROM computer WHERE name LIKE ?";

    private JdbcTemplate template;
    private SimpleJdbcInsert insertComputer;

    @Autowired
    public void setDataSource(DriverManagerDataSource dataSource){
        template = new JdbcTemplate(dataSource);
        insertComputer = new SimpleJdbcInsert(dataSource).withTableName("computer").usingGeneratedKeyColumns("computer.id");
    }


    @Override
    public List<Computer> getAll() throws DaoException {
        return template.query(SELECT_ALL, new ComputerRowMapper());
    }

    @Override
    @Transactional
    public void save(Computer computer) throws DaoException {
        Map<String, Object> parameters = new HashMap<String,Object>();
        parameters.put("name",computer.getName());
        parameters.put("introduced",computer.getIntroduced());
        parameters.put("discontinued",computer.getDiscontinued());
        if (computer.getCompany() != null){
            parameters.put("company_id",computer.getCompany().getId());
        }
        else{
            parameters.put("company_id",null);
        }
        long id = (Long) insertComputer.executeAndReturnKey(parameters);
        computer.setId( id);
    }

    @Override
    @Transactional
    public void update(Computer computer) throws DaoException {
        long companyId = computer.getCompany() == null ? 0 : computer.getCompany().getId();
        template.update(UPDATE,new Object[]{computer.getName(),computer.getIntroduced(),computer.getDiscontinued(),companyId,computer.getId()});
    }

    @Override
    @Transactional
    public void deleteAll() throws DaoException {
        template.update(DELETE_ALL);
    }

    @Override
    @Transactional(readOnly = true)
    public Computer findById(long computerId) throws DaoException {
        List<Computer> results = template.query(FIND_BY_ID,new Object[]{computerId},new ComputerRowMapper());
        if (results.size() == 0) return null;
        return results.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Computer> getMatchingFromToWithSortedByColumn(String namePattern, int firstIndice, int lastIndice, int columnId) throws DaoException {
        return template.query(getSqlSelect(columnId),new Object[]{"%"+namePattern+"%", lastIndice-firstIndice, firstIndice}, new ComputerRowMapper());
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
    @Transactional(readOnly = true)
    public int numberOfMatching(String namePattern) throws DaoException {
        return template.queryForObject(SELECT_NUMBER_MATCHING, new Object[]{"%"+namePattern+"%"} , Integer.class);
    }

    @Override
    @Transactional
    public void deleteById(long computerId) throws DaoException {
        template.update(DELETE_BY_ID, new Object[]{computerId});
    }
}
