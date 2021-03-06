package com.excilys.computerdatabase.servlet;

import com.excilys.computerdatabase.form.ComputerForm;
import com.excilys.computerdatabase.service.ComputerDatabaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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

@WebServlet("/computers/new")
public class NewComputerServlet extends HttpServlet {

    private ComputerDatabaseService computerDatabaseService;
    private ApplicationContext applicationContext;

    @Override
    public void init() {
        if (applicationContext == null) {
            applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        }
        computerDatabaseService = applicationContext.getBean(ComputerDatabaseService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mode", "new");
        request.setAttribute("companies", computerDatabaseService.allCompanies());
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerForm form = new ComputerForm(request.getParameter("name"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("company"));

        if (form.isValid()) {
            request.getSession().setAttribute("alertMessage", "Computer " + form.getComputer().getName() + " added successfully");
            computerDatabaseService.createComputerAndSetCompany(form.getComputer(), form.getCompanyId());
            response.sendRedirect("../computers");
        } else {
            request.setAttribute("mode", "new");
            request.setAttribute("companies", computerDatabaseService.allCompanies());
            request.setAttribute("errorMessages", form.getErrorMessages());
            request.setAttribute("fieldValues", form.getFieldValues());
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request, response);
        }
    }

}
