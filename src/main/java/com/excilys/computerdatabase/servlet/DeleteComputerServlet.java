package com.excilys.computerdatabase.servlet;

import com.excilys.computerdatabase.service.ComputerDatabaseService;
import com.excilys.computerdatabase.service.SimpleComputerDatabaseService;
import com.excilys.computerdatabase.utils.C;
import com.excilys.computerdatabase.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
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
 * Date: 28/05/13
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */

@WebServlet("/computers/delete")
public class DeleteComputerServlet extends HttpServlet {

    private ComputerDatabaseService computerDatabaseService;
    private ApplicationContext applicationContext;

    @Override
    public void init() {
        if (applicationContext == null) {
            applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        }
        computerDatabaseService = applicationContext.getBean(ComputerDatabaseService.class);
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