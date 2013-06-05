package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.queryresults.ComputersAndTotalNumber;
import com.excilys.computerdatabase.service.ComputerDatabaseService;
import com.excilys.computerdatabase.utils.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 05/06/13
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class ComputerController {

    @Autowired
    private ComputerDatabaseService computerDatabaseService;


    @RequestMapping("/computers")
    public String printWelcome(ModelMap model,
                               @RequestParam(value="s",defaultValue = "2") Integer sortedColumnNumber,
                               @RequestParam(value="f",defaultValue = "") String research,
                               @RequestParam(value="p",defaultValue = "1") Integer currentPage) {

        if (Math.abs(sortedColumnNumber) < 2 || Math.abs(sortedColumnNumber) > 5) {
            sortedColumnNumber = 2;
        }

        String alertMessage = "message d'alerte";

        int firstComputerIndice = (currentPage - 1) * C.COMPUTERS_PER_PAGE;
        int lastComputerIndice = firstComputerIndice + C.COMPUTERS_PER_PAGE;

        ComputersAndTotalNumber queryResult = computerDatabaseService.listOfComputers(research, sortedColumnNumber, firstComputerIndice, lastComputerIndice);
        int numberOfMatchingComputers = queryResult.getNumberOfMatchingComputers();
        List<Computer> computers = queryResult.getMatchingComputers();
        int maxPage = (int) Math.ceil((1.0 * numberOfMatchingComputers) / C.COMPUTERS_PER_PAGE);


        model.addAttribute("alertMessage", alertMessage);
        model.addAttribute("sorting", sortedColumnNumber);
        model.addAttribute("research", research);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("firstComputerIndice", firstComputerIndice + 1);
        model.addAttribute("lastComputerIndice", lastComputerIndice);
        model.addAttribute("totalComputersFound", numberOfMatchingComputers);
        model.addAttribute("computers", computers);
        return "computers";
    }
}
