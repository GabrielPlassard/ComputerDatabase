package com.excilys.computerdatabase.servlet;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputersAndTotalNumber;
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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 27/05/13
 * Time: 09:27
 * To change this template use File | Settings | File Templates.
 */

@WebServlet("/computers")
public class IndexServlet extends HttpServlet {

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

        int currentPage = Utils.intParameterOrDefault(request.getParameter("p"), 1);
        String research = Utils.stringParameterOrDefault(request.getParameter("f"), "");
        int sortedColumnNumber = Utils.intParameterOrDefault(request.getParameter("s"), 2);

        if (Math.abs(sortedColumnNumber) < 2 || Math.abs(sortedColumnNumber) > 5){
            sortedColumnNumber = 2;
        }

        String alertMessage = Utils.stringParameterOrDefault((String)request.getSession().getAttribute("alertMessage"),null);
        request.getSession().removeAttribute("alertMessage");

        int firstComputerIndice = (currentPage - 1) * C.COMPUTERS_PER_PAGE;
        int lastComputerIndice = firstComputerIndice + C.COMPUTERS_PER_PAGE;

        System.out.println("service : ");
        System.out.println(computerDatabaseService);
        ComputersAndTotalNumber queryResult = computerDatabaseService.listOfComputers(research, sortedColumnNumber, firstComputerIndice, lastComputerIndice);
        int numberOfMatchingComputers =  queryResult.getNumberOfMatchingComputers();
        List<Computer> computers = queryResult.getMatchingComputers();

        int maxPage = (int) Math.ceil((1.0 * numberOfMatchingComputers)/ C.COMPUTERS_PER_PAGE);
        if (lastComputerIndice > numberOfMatchingComputers){
            lastComputerIndice = numberOfMatchingComputers;
        }

        request.setAttribute("alertMessage",alertMessage);
        request.setAttribute("sorting",sortedColumnNumber);
        request.setAttribute("research",research);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("maxPage",maxPage);
        request.setAttribute("firstComputerIndice",firstComputerIndice + 1);
        request.setAttribute("lastComputerIndice",lastComputerIndice);
        request.setAttribute("totalComputersFound",numberOfMatchingComputers);
        request.setAttribute("computers",computers);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computers.jsp").forward(request,response);
    }


}
