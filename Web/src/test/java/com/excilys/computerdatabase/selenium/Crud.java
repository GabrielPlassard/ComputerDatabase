package com.excilys.computerdatabase.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 06/06/13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */

public class Crud {
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/");
        selenium.start();
    }

    @Test
    public void testCRUD() throws Exception {
        selenium.open("/computers");
        assertTrue(selenium.isTextPresent("0 computer(s) found"));
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("Create a computer"));
        selenium.type("id=name", "apple 1");
        selenium.select("id=company", "label=Apple Inc.");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("Computer apple 1 added successfully"));
        assertTrue(selenium.isTextPresent("1 computer(s) found"));
        assertTrue(selenium.isTextPresent("Displaying 1 to 10 of 1"));
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "apple 2");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "apple 3");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "apple 4");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "windows 1");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "windows 2");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "windows 3");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "windows 4");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "samsung 1");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "samsung 2");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "samsung 3");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=add");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "samsung 4");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("Displaying 1 to 10 of 12"));
        selenium.click("link=Next →");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("Displaying 11 to 12 of 12"));
        selenium.click("link=← Previous");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("Displaying 1 to 10 of 12"));
        selenium.click("id=searchsubmit");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("4 computer(s) found"));
        selenium.click("link=Computer name");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=apple 3");
        selenium.waitForPageToLoad("30000");
        selenium.type("id=name", "apple 28");
        selenium.click("css=input.btn.primary");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("Computer apple 28 modified successfully"));
        selenium.click("id=searchsubmit");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("0 computer(s) found"));
        selenium.click("link=Play 2.0 sample application — Computer database");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=apple 28");
        selenium.waitForPageToLoad("30000");
        selenium.click("css=input.btn.danger");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("Computer deleted successfully"));
        assertTrue(selenium.isTextPresent("11 computer(s) found"));
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}