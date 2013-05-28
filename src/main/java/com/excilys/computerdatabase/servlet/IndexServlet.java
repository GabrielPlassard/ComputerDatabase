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
 * Date: 27/05/13
 * Time: 09:27
 * To change this template use File | Settings | File Templates.
 */

@WebServlet("/computers")
public class IndexServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet  {

    public final static int COMPUTERS_PER_SHEET = 10;

    private ComputerDatabaseService computerDatabaseService = SimpleComputerDatabaseService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentSheet = Utils.intParameterOrDefault(request.getParameter("p"), 1);
        String research = Utils.stringParameterOrDefault(request.getParameter("f"), "");
        int sortedColumnNumber = Utils.intParameterOrDefault(request.getParameter("s"), 2);
        String alertMessage = Utils.stringParameterOrDefault((String)request.getSession().getAttribute("alertMessage"),null);
        request.getSession().removeAttribute("alertMessage");

        int numberOfMatchingComputers =  computerDatabaseService.numberOfMatchingComputers(research);
        int maxSheet = (int) Math.ceil((1.0 * numberOfMatchingComputers)/ COMPUTERS_PER_SHEET);
        int firstComputerIndice = (currentSheet - 1) * COMPUTERS_PER_SHEET;
        int lastComputerIndice = firstComputerIndice + COMPUTERS_PER_SHEET;
        if (lastComputerIndice > numberOfMatchingComputers){
            lastComputerIndice = numberOfMatchingComputers;
        }

        request.setAttribute("alertMessage",alertMessage);
        request.setAttribute("sorting",sortedColumnNumber);
        request.setAttribute("research",research);
        request.setAttribute("currentSheet",currentSheet);
        request.setAttribute("maxSheet",maxSheet);
        request.setAttribute("firstComputerIndice",firstComputerIndice + 1);
        request.setAttribute("lastComputerIndice",lastComputerIndice);
        request.setAttribute("totalComputersFound",numberOfMatchingComputers);
        request.setAttribute("computers",computerDatabaseService.getMatchingComputersFromToSortedByColumn(research, firstComputerIndice, lastComputerIndice,sortedColumnNumber));
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/computers.jsp").forward(request,response);
    }

}
