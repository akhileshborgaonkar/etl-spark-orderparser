package com.sample.spark.exceptions;

/**
 * @author Steve Johnson <sjohnson@12digitmedia.com> on 4/19/16
 */
public class SparkUtilException extends Exception {

    @SuppressWarnings("unused")
    public SparkUtilException(Exception e) {
        super(e);
    }

    @SuppressWarnings("unused")
    public SparkUtilException(String msg) {
        super(msg);
    }

    @SuppressWarnings("unused")
    public SparkUtilException(String msg, Exception e) {
        super(msg, e);
    }
}
