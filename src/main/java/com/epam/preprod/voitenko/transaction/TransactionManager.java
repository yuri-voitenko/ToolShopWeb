package com.epam.preprod.voitenko.transaction;

import com.epam.preprod.voitenko.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    Logger LOGGER = LogManager.getLogger(TransactionManager.class);

    public <T> T doInTransaction(IOperation<T> operation) {
        Object result = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Can not get connection", e);
        }
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                result = operation.operation(connection);
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error("Can not commit", e);
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    LOGGER.error("Can not rollback", e);
                }
            }
        }
        return (T) result;
    }
}