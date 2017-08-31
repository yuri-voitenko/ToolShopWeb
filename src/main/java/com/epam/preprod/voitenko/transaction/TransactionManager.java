package com.epam.preprod.voitenko.transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_EXECUTE_COMMIT;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_EXECUTE_ROLLBACK;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_CONNECTION;

public class TransactionManager {
    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);
    private DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T doInTransaction(Operation<T> operation) {
        Object result = null;
        try (Connection connection = dataSource.getConnection()) {
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
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_CONNECTION, e);
        }
        return (T) result;
    }
}