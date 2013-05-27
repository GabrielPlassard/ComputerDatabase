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
 * Time: 09:27
 * To change this template use File | Settings | File Templates.
 */
public class IndexServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet  {

    private ComputerDatabaseService computerDatabaseService = SimpleComputerDatabaseService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("currentSheet",1);
        request.setAttribute("firstComputerIndice",1);
        request.setAttribute("lastComputerIndice",10);
        request.setAttribute("totalComputersFound",578);
        request.setAttribute("computers",computerDatabaseService.allComputers());
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/computers.jsp").forward(request,response);
    }

}
