package com.epam.preprod.voitenko.transaction;

import com.epam.preprod.voitenko.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.*;

public class TransactionManager {
    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);

    public <T> T doInTransaction(Operation<T> operation) {
        Object result = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_CONNECTION, e);
        }
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                result = operation.operation(connection);
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_EXECUTE_COMMIT, e);
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    LOGGER.error(CANNOT_EXECUTE_ROLLBACK, e);
                }
            }
        }
        return (T) result;
    }
}