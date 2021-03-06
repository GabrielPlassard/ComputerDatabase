package com.excilys.computerdatabase.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id = -1;
    private String name = "defaultName";

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString(){
        return "Company "+name;
    }
}
