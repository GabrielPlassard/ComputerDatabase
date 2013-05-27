package com.excilys.computerdatabase.servlet;

import com.excilys.computerdatabase.service.ComputerDatabaseService;
import com.excilys.computerdatabase.service.SimpleComputerDatabaseService;

import javax.servlet.ServletException;
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
public class NewComputerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

    private ComputerDatabaseService computerDatabaseService = SimpleComputerDatabaseService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request,response);
    }

}
