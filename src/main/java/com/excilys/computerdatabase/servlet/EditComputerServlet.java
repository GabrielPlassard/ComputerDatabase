package com.excilys.computerdatabase.servlet;

import com.excilys.computerdatabase.form.ComputerForm;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.ComputerDatabaseService;
import com.excilys.computerdatabase.service.SimpleComputerDatabaseService;
import com.excilys.computerdatabase.utils.Utils;

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

@WebServlet("/computers/edit")
public class EditComputerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

    private ComputerDatabaseService computerDatabaseService = SimpleComputerDatabaseService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Utils.intParameterOrDefault(request.getParameter("id"),0);
        ComputerForm form = new ComputerForm(computerDatabaseService.computerById(id));
        request.setAttribute("mode","edit");
        request.setAttribute("fieldValues",form.getFieldValues());
        request.setAttribute("companies", computerDatabaseService.allCompanies());
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ComputerForm form = new ComputerForm(request,computerDatabaseService);

        if (form.isValid()){
            request.getSession().setAttribute("alertMessage","Computer "+form.getComputer().getName()+" modified successfully");
            Computer computer = form.getComputer();
            int id = Utils.intParameterOrDefault(request.getParameter("id"),0);
            computer.setId(id);
            computerDatabaseService.saveOrUpdateComputer(computer);
            response.sendRedirect("/computers");
        }
        else{
            request.setAttribute("mode","edit");
            request.setAttribute("companies", computerDatabaseService.allCompanies());
            request.setAttribute("errorMessages",form.getErrorMessages());
            request.setAttribute("fieldValues",form.getFieldValues());
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request,response);
        }
    }
}