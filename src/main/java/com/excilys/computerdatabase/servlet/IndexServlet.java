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


    private int intParameterOrDefault(String parameter, int defaultValue){
        try{
            return Integer.valueOf(parameter);
        }catch(NumberFormatException e){

        }
        return defaultValue;
    }

    private String stringParameterOrDefault(String parameter, String defaultValue){
        if (parameter != null) return parameter;
        return defaultValue;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentSheet = intParameterOrDefault(request.getParameter("p"),1);
        String research = stringParameterOrDefault(request.getParameter("f"),"");

        int numberOfMatchingComputers =  computerDatabaseService.numberOfMatchingComputers(research);
        int maxSheet = (int) Math.ceil((1.0 * numberOfMatchingComputers)/ 10);
        int firstComputerIndice = (currentSheet - 1) * 10 + 1;
        int lastComputerIndice = firstComputerIndice + 10;

        request.setAttribute("research",research);
        request.setAttribute("currentSheet",currentSheet);
        request.setAttribute("maxSheet",maxSheet);
        request.setAttribute("firstComputerIndice",firstComputerIndice);
        request.setAttribute("lastComputerIndice",lastComputerIndice);
        request.setAttribute("totalComputersFound",numberOfMatchingComputers);
        request.setAttribute("computers",computerDatabaseService.getMatchingComputersFromTo(research,firstComputerIndice, lastComputerIndice));
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/computers.jsp").forward(request,response);
    }

}
