package com.customersuccessbox.sdk.exception;

/**
 * The Class CSBException.
 */
public class CSBException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 767781181908841629L;

    /**
     * Instantiates a new CSB exception.
     *
     * @param msg
     *            the msg
     */
    public CSBException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new CSB exception.
     *
     * @param msg
     *            the msg
     * @param ex
     *            the ex
     */
    public CSBException(final String msg, final Throwable ex) {
        super(msg, ex);
    }

}
