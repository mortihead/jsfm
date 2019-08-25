package ru.mortihead;

import org.apache.log4j.Logger;

/**
 * Database work helper
 */
@Deprecated
public class DBHelper {
    static final private Logger logger = Logger.getLogger(DBHelper.class);


    public DBHelper() {
        logger.info("DBHelper init");
    }

    public void doTest() {
        logger.info("DBHelper doTest");
    }
}
