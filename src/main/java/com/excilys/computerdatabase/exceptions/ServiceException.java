package com.excilys.computerdatabase.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 31/05/13
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
