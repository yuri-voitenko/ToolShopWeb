package com.epam.preprod.voitenko.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_CREATE_DATA_SOURCE;

public class DataSourceHandler {
    private static final Logger LOGGER = LogManager.getLogger(DataSourceHandler.class);
    private static DataSourceHandler instance;
    private DataSource dataSource;

    private DataSourceHandler() {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/toolshopwebDB");
        } catch (NamingException e) {
            LOGGER.error(CANNOT_CREATE_DATA_SOURCE, e);
        }
    }

    public static DataSourceHandler getInstance() {
        if (instance == null) {
            instance = new DataSourceHandler();
        }
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}