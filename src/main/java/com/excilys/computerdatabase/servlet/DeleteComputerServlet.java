package com.excilys.computerdatabase.servlet;

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
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */

@Component
public class DeleteComputerServlet implements org.springframework.web.HttpRequestHandler{

    @Autowired
    private ComputerDatabaseService computerDatabaseService;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException {
    if (request.getMethod().equalsIgnoreCase(C.POST)){
            doPost(request,httpServletResponse);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int computerId = Utils.intParameterOrDefault(request.getParameter("id"),0);
        boolean succesfull = computerDatabaseService.deleteComputerById(computerId);
        if (succesfull){
            request.getSession().setAttribute("alertMessage","Computer deleted successfully");
        }
        else{
            request.getSession().setAttribute("alertMessage","There has been a problem while deleting the computer");
        }
        response.sendRedirect("/computers");
    }
}