package com.excilys.computerdatabase.servlet;

import com.excilys.computerdatabase.form.ComputerForm;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputerAndCompanies;
import com.excilys.computerdatabase.service.ComputerDatabaseService;
import com.excilys.computerdatabase.utils.Utils;
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
 * Date: 28/05/13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */

@WebServlet("/computers/edit")
public class EditComputerServlet extends HttpServlet {

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
        long id = Utils.longParameterOrDefault(request.getParameter("id"), 0);
        ComputerAndCompanies queryResult = computerDatabaseService.computerByIdAndCompanies(id);

        ComputerForm form = new ComputerForm(queryResult.getComputer());
        request.setAttribute("mode", "edit");
        request.setAttribute("fieldValues", form.getFieldValues());
        request.setAttribute("companies", queryResult.getCompanies());
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerForm form = new ComputerForm(request);

        if (form.isValid()) {
            Computer computer = form.getComputer();
            long id = Utils.longParameterOrDefault(request.getParameter("id"), 0);
            computer.setId(id);
            boolean succesfull = computerDatabaseService.updateComputerAndSetCompany(computer, form.getCompanyId());
            if (succesfull) {
                request.getSession().setAttribute("alertMessage", "Computer " + computer.getName() + " modified successfully");
            } else {
                request.getSession().setAttribute("alertMessage", "There has been a problem while updating " + computer.getName());
            }
            response.sendRedirect("../computers");
        } else {
            request.setAttribute("mode", "edit");
            request.setAttribute("companies", computerDatabaseService.allCompanies());
            request.setAttribute("errorMessages", form.getErrorMessages());
            request.setAttribute("fieldValues", form.getFieldValues());
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/computer.jsp").forward(request, response);
        }
    }

}