package com.excilys.computerdatabase.servlet;

import com.excilys.computerdatabase.form.ComputerForm;
import com.excilys.computerdatabase.service.ComputerDatabaseService;
import com.excilys.computerdatabase.service.SimpleComputerDatabaseService;
import com.excilys.computerdatabase.utils.C;
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
 * Date: 27/05/13
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */

@Component
public class NewComputerServlet implements org.springframework.web.HttpRequestHandler{

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
        request.setAttribute("mode","new");
        request.setAttribute("companies", computerDatabaseService.allCompanies());
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request,response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ComputerForm form = new ComputerForm(request);

        if (form.isValid()){
            request.getSession().setAttribute("alertMessage","Computer "+form.getComputer().getName()+" added successfully");
            computerDatabaseService.createComputerAndSetCompany(form.getComputer(), form.getCompanyId());
            response.sendRedirect("/computers");
        }
        else{
            request.setAttribute("mode","new");
            request.setAttribute("companies", computerDatabaseService.allCompanies());
            request.setAttribute("errorMessages",form.getErrorMessages());
            request.setAttribute("fieldValues", form.getFieldValues());
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request,response);
        }
    }

}
