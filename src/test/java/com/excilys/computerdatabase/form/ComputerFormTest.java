package com.excilys.computerdatabase.form;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 28/05/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class ComputerFormTest {

    private static final ComputerForm form = new ComputerForm();

    @Test
    public void testDateWellFormatted(){
        assertFalse(form.isBadFormatted("2012-11-25"));
    }

    @Test
    public void testDateNotExisting(){
        assertTrue(form.isBadFormatted("2012-15-06"));
    }

    @Test
    public void testDateEmpty(){
        assertFalse(form.isBadFormatted(""));
    }

    @Test
    public void testDateNull(){
        assertFalse(form.isBadFormatted(null));
    }
}
