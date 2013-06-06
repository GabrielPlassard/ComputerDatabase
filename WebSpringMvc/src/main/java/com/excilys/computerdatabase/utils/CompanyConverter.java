package com.excilys.computerdatabase.utils;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.ComputerDatabaseService;

import java.beans.PropertyEditorSupport;


public class CompanyConverter extends PropertyEditorSupport {

    private ComputerDatabaseService companyService;

    public CompanyConverter(ComputerDatabaseService companyService) {
        this.companyService = companyService;
    }

    @Override
    public String getAsText() {
        Company company = (Company) getValue();
        if (company == null) {
            return null;
        }
        return String.valueOf(company.getId());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        long id = Utils.longParameterOrDefault(text,0);
        setValue(companyService.companyById(id));
    }

}