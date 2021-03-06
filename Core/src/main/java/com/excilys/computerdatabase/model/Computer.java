package com.excilys.computerdatabase.model;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "computer")
public class Computer {

    private final static Logger logger = LoggerFactory.getLogger(Computer.class);

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String name;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date introduced;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date discontinued;
    @ManyToOne
    private Company company;

    public Computer() {
    }

    public Computer(String name, String stringIntroduced, String stringDiscontinued, Company company) {
        this.name = name;
        this.company = company;
        setIntroduced(stringIntroduced);
        setDiscontinued(stringDiscontinued);
    }

    public Computer(String name) {
        this(name, null, null, null);
    }

    public void setId(long id) {
        this.id = id;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getIntroduced() {
        return introduced;
    }

    public Date getDiscontinued() {
        return discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setIntroduced(Date introduced) {
        this.introduced = introduced;
    }

    public void setDiscontinued(Date discontinued) {
        this.discontinued = discontinued;
    }


    private void setIntroduced(String stringIntroduced) {
        if (stringIntroduced == null) return;
        try {
            introduced = dateFormatter.parse(stringIntroduced);
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
    }

    private void setDiscontinued(String stringDiscontinued) {
        if (stringDiscontinued == null) return;
        try {
            discontinued = dateFormatter.parse(stringDiscontinued);
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("Computer(");
        str.append(id).append(", ");
        str.append(name).append(", ");
        str.append(introduced).append(", ");
        str.append(discontinued);
        str.append(company == null ? "" : ", "+company.getName());
        str.append(")");
        return str.toString();
    }
}
