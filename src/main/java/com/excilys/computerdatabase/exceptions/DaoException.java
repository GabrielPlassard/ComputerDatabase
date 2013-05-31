package com.excilys.computerdatabase.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 31/05/13
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class DaoException extends Exception{
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
