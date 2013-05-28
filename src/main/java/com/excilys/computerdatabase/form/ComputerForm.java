package com.excilys.computerdatabase.form;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.ComputerDatabaseService;
import com.excilys.computerdatabase.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 28/05/13
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class ComputerForm {
    private ComputerDatabaseService computerDatabaseService;

    private String name;
    private String stringIntroduced;
    private String stringDiscontinued;
    private String stringCompanyId;

    private Computer computer;

    private Map<String,String> errorMessages = new HashMap<String, String>();
    private Map<String,String> fieldValues = new HashMap<String, String>();

    ComputerForm(){}

    public ComputerForm(HttpServletRequest request, ComputerDatabaseService computerDatabaseService){
        this.computerDatabaseService = computerDatabaseService;
        name = request.getParameter("name");
        stringIntroduced = request.getParameter("introduced");
        stringDiscontinued = request.getParameter("discontinued");
        stringCompanyId = request.getParameter("company");

        buildComputer();
        buildErrorMessages();
        buildFieldValues();
    }

    public ComputerForm(Computer computer){
        if (computer == null) return;

        this.computer = computer;
        name = computer.getName();
        stringIntroduced = Utils.format(computer.getIntroduced());
        stringDiscontinued = Utils.format(computer.getDiscontinued());
        if (computer.getCompany() != null){
            stringCompanyId = ""+computer.getCompany().getId();
        }
        buildFieldValues();
    }

    private void buildFieldValues() {
        fieldValues.put("name",name);
        fieldValues.put("introduced",stringIntroduced);
        fieldValues.put("discontinued",stringDiscontinued);
        fieldValues.put("company",stringCompanyId);
        if (computer != null){
            fieldValues.put("id",""+computer.getId());
        }
    }

    private void buildErrorMessages() {
        if (name == null || name.trim().isEmpty()){
            errorMessages.put("name","Name should not be empty");
        }
        if (isBadFormatted(stringIntroduced)){
            errorMessages.put("introduced","Wrong format, should be 'yyyy-MM-dd'");
        }
        if (isBadFormatted(stringDiscontinued)){
            errorMessages.put("discontinued","Wrong format, should be 'yyyy-MM-dd'");
        }
    }

    private void buildComputer() {
        computer = new Computer();
        computer.setName(name);
        computer.setIntroduced(Utils.dateParameterOrDefault(stringIntroduced, null));
        computer.setDiscontinued(Utils.dateParameterOrDefault(stringDiscontinued,null));
        computer.setCompany(computerDatabaseService.companyById(Utils.intParameterOrDefault(stringCompanyId, 0)));
    }

    boolean isBadFormatted(String date){
        if (date == null) return false;
        if (date.trim().isEmpty()) return false;
        return Utils.dateParameterOrDefault(date,null) == null;
    }

    public boolean isValid(){
        return errorMessages.size() == 0;
    }

    public Map<String,String> getErrorMessages(){
        return errorMessages;
    }

    public Map<String,String> getFieldValues(){
        return fieldValues;
    }

    public Computer getComputer(){
        if (!isValid()) throw new IllegalStateException("Computer is bad formatted");
        return computer;
    }


}
