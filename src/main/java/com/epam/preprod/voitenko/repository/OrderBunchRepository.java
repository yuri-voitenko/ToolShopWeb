package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.entity.OrderBunchEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_CREATE_ORDER_BUNCH;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_DELETE_ORDER_BUNCH;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_ORDER_BUNCH_BY_ID;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.INFO_ORDERED_TOOL_ID;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ORDER_ID;

public class OrderBunchRepository implements GeneralRepository<List<OrderBunchEntity>, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(OrderBunchRepository.class);

    private static final String SQL_GET_ORDER_BUNCH_BY_ID = "SELECT * FROM order_bunch WHERE id=?;";
    private static final String SQL_DELETE_ORDER_BUNCH = "DELETE FROM order_bunch WHERE id=?;";
    private static final String SQL_INSERT_ORDER_BUNCH = "INSERT INTO order_bunch (orderID, infoOrderedToolID) VALUES (?, ?);";

    @Override
    public List<List<OrderBunchEntity>> getAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrderBunchEntity> getEntityById(Connection connection, Integer id) {
        List<OrderBunchEntity> orderBunchEntities = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_GET_ORDER_BUNCH_BY_ID);
            statement.setInt(++index, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                orderBunchEntities = extractOrderBunch(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ORDER_BUNCH_BY_ID, e);
        } finally {
            close(resultSet, statement);
        }
        return orderBunchEntities;
    }

    @Override
    public List<OrderBunchEntity> update(Connection connection, List<OrderBunchEntity> entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Connection connection, Integer id) {
        PreparedStatement statement = null;
        int rowsAffected = -1;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ORDER_BUNCH);
            int index = 0;
            statement.setInt(++index, id);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ORDER_BUNCH, e);
            return false;
        } finally {
            close(statement);
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean create(Connection connection, List<OrderBunchEntity> entity) {
        checkObjectIsNull(entity);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_ORDER_BUNCH);
            for (OrderBunchEntity row : entity) {
                int index = 0;
                statement.setInt(++index, row.getOrderID());
                statement.setInt(++index, row.getInfoOrderedToolID());
                statement.addBatch();
                statement.clearParameters();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_CREATE_ORDER_BUNCH, e);
            return false;
        } finally {
            close(statement);
        }
        return true;
    }

    private List<OrderBunchEntity> extractOrderBunch(ResultSet resultSet) throws SQLException {
        checkObjectIsNull(resultSet);
        List<OrderBunchEntity> orderBunchEntities = new ArrayList<>();
        do {
            int orderID = resultSet.getInt(ORDER_ID);
            int orderedToolID = resultSet.getInt(INFO_ORDERED_TOOL_ID);
            OrderBunchEntity orderBunchEntity = new OrderBunchEntity(orderID, orderedToolID);
            orderBunchEntities.add(orderBunchEntity);
        } while (resultSet.next());
        return orderBunchEntities;
    }

    private void checkObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}