package com.excilys.computerdatabase.servlet;

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
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */

@WebServlet("/computers/delete")
public class DeleteComputerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

    private ComputerDatabaseService computerDatabaseService = SimpleComputerDatabaseService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int computerId = Utils.intParameterOrDefault(request.getParameter("id"),0);
        computerDatabaseService.deleteComputerById(computerId);
        request.getSession().setAttribute("alertMessage","Computer deleted successfully");
        response.sendRedirect("/computers");
    }
}