package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.Company;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 06/06/13
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class HibernateCompanyDao implements CompanyDao {

    private final static Logger logger = LoggerFactory.getLogger(HibernateCompanyDao.class);

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public void openConnection() {
        logger.debug("Not opening connection since it's Spring's job to do it");
    }

    @Override
    public void closeConnection() {
        logger.debug("Not closing connection since it's Spring's job to do it");
    }

    @Override
    @Transactional(readOnly = true)
    public Company findByName(String name) throws DaoException {
        List<Company> companies = hibernateTemplate.find("FROM Company c WHERE c.name=?",name);
        if (companies.size() == 0) return  null;
        return companies.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> getAll() throws DaoException {
        return hibernateTemplate.find("FROM Company");
    }

    @Override
    @Transactional
    public void saveOrUpdate(Company company) throws DaoException {
        hibernateTemplate.saveOrUpdate(company);
    }

    @Override
    @Transactional
    public void deleteAll() throws DaoException {
        hibernateTemplate.bulkUpdate("DELETE FROM Company");
    }

    @Override
    @Transactional(readOnly = true)
    public Company findById(long companyId) throws DaoException {
        return hibernateTemplate.get(Company.class,companyId);
    }
}
