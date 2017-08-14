package com.epam.preprod.voitenko.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.*;

public interface IGeneralRepository<E, K> {
    Logger LOGGER = LogManager.getLogger(IGeneralRepository.class);

    List<E> getAll(Connection connection);

    E getEntityById(Connection connection, K id);

    E update(Connection connection, E entity);

    boolean delete(Connection connection, K id);

    boolean create(Connection connection, E entity);

    default void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_CLOSE_RESULT_SET, e);
            }
        }
    }

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_CLOSE_STATEMENT, e);
            }
        }
    }

    default void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_CLOSE_CONNECTION, e);
            }
        }
    }

    default void close(Statement statement, Connection connection) {
        close(statement);
        close(connection);
    }

    default void close(ResultSet resultSet, Statement statement, Connection connection) {
        close(resultSet);
        close(statement);
        close(connection);
    }
}