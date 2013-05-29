package com.excilys.computerdatabase.queryresults;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 29/05/13
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public class ComputerAndCompanies {
    private Computer computer;
    private List<Company> companies;

    public ComputerAndCompanies(Computer computer, List<Company> companies) {
        this.computer = computer;
        this.companies = companies;
    }

    public Computer getComputer() {
        return computer;
    }

    public List<Company> getCompanies() {
        return companies;
    }
}
