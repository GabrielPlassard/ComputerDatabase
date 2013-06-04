package com.excilys.computerdatabase.servlet;

import com.excilys.computerdatabase.form.ComputerForm;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputerAndCompanies;
import com.excilys.computerdatabase.service.ComputerDatabaseService;
import com.excilys.computerdatabase.service.SimpleComputerDatabaseService;
import com.excilys.computerdatabase.utils.C;
import com.excilys.computerdatabase.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 28/05/13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */

@Component
public class EditComputerServlet implements org.springframework.web.HttpRequestHandler{

    @Autowired
    private ComputerDatabaseService computerDatabaseService;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(C.GET)){
            doGet(request,httpServletResponse);
        }
        else if (request.getMethod().equalsIgnoreCase(C.POST)){
            doPost(request,httpServletResponse);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Utils.intParameterOrDefault(request.getParameter("id"),0);
        ComputerAndCompanies queryResult = computerDatabaseService.computerByIdAndCompanies(id);

        ComputerForm form = new ComputerForm(queryResult.getComputer());
        request.setAttribute("mode","edit");
        request.setAttribute("fieldValues",form.getFieldValues());
        request.setAttribute("companies", queryResult.getCompanies());
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ComputerForm form = new ComputerForm(request);

        if (form.isValid()){
            Computer computer = form.getComputer();
            int id = Utils.intParameterOrDefault(request.getParameter("id"),0);
            computer.setId(id);
            boolean succesfull = computerDatabaseService.updateComputerAndSetCompany(computer, form.getCompanyId());
            if (succesfull){
                request.getSession().setAttribute("alertMessage","Computer "+computer.getName()+" modified successfully");
            }
            else{
                request.getSession().setAttribute("alertMessage","There has been a problem while updating "+computer.getName());
            }
            response.sendRedirect("/computers");
        }
        else{
            request.setAttribute("mode","edit");
            request.setAttribute("companies", computerDatabaseService.allCompanies());
            request.setAttribute("errorMessages",form.getErrorMessages());
            request.setAttribute("fieldValues",form.getFieldValues());
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request,response);
        }
    }

}