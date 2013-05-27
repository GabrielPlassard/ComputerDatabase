package com.excilys.computerdatabase.model;

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
public class Computer{

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private int id;
    private String name;
    private Date introduced;
    private Date discontinued;
    private Company company;

    public Computer(){}

    public Computer(String name, String stringIntroduced, String stringDiscontinued, Company company) {
        this.name = name;
        this.company = company;
        setIntroduced(stringIntroduced);
        setDiscontinued(stringDiscontinued);
    }

    public Computer(String name){
        this(name, null, null, null);
    }

    public void setId(int id) {
        this.id = id;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getId() {
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void setDiscontinued(String stringDiscontinued) {
        if (stringDiscontinued == null) return;
        try {
            discontinued = dateFormatter.parse(stringDiscontinued);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
