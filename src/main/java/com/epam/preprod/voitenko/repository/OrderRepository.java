package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.entity.OrderEntity;
import com.epam.preprod.voitenko.entity.OrderStatus;
import com.epam.preprod.voitenko.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.*;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class OrderRepository implements GeneralRepository<OrderEntity, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(OrderRepository.class);

    private static final String SQL_SELECT_ALL = "SELECT * FROM orders;";
    private static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id=?;";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET status=?, statusDetail=?, address=?, dateTime=?, userID=? WHERE id=?;";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id=?;";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (status, statusDetail, address, dateTime, userID) VALUES (?, ?, ?, ?, ?);";

    @Override
    public List<OrderEntity> getAll(Connection connection) {
        List<OrderEntity> orders = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractOrder(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ALL_ORDERS, e);
        } finally {
            close(resultSet, statement);
        }
        return orders;
    }

    @Override
    public OrderEntity getEntityById(Connection connection, Integer id) {
        OrderEntity orderEntity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_GET_ORDER_BY_ID);
            statement.setInt(++index, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                orderEntity = extractOrder(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ORDER_BY_ID, e);
        } finally {
            close(resultSet, statement);
        }
        return orderEntity;
    }

    @Override
    public OrderEntity update(Connection connection, OrderEntity entity) {
        checkObjectIsNull(entity);
        OrderEntity oldValue = getEntityById(connection, entity.getId());
        if (oldValue == null) {
            return null;
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ORDER);
            int index = setOrderParametersToStatement(entity, statement);
            statement.setInt(++index, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_ORDER, e);
        } finally {
            close(statement);
        }
        return oldValue;
    }

    @Override
    public boolean delete(Connection connection, Integer id) {
        PreparedStatement statement = null;
        int rowsAffected = -1;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ORDER);
            int index = 0;
            statement.setInt(++index, id);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ORDER, e);
            return false;
        } finally {
            close(statement);
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean create(Connection connection, OrderEntity entity) {
        checkObjectIsNull(entity);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_ORDER, RETURN_GENERATED_KEYS);
            setOrderParametersToStatement(entity, statement);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                int id = (int) resultSet.getLong(1);
                entity.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_CREATE_ORDER, e);
            return false;
        } finally {
            close(resultSet, statement);
        }
        return true;
    }

    private int setOrderParametersToStatement(OrderEntity entity, PreparedStatement statement) throws SQLException {
        int index = 0;
        statement.setString(++index, entity.getStatus().toString());
        statement.setString(++index, entity.getDetailStatus());
        statement.setString(++index, entity.getAddress());
        statement.setTimestamp(++index, entity.getDateTime());
        statement.setInt(++index, entity.getUser().getId());
        return index;
    }

    private OrderEntity extractOrder(ResultSet resultSet) throws SQLException {
        checkObjectIsNull(resultSet);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(resultSet.getInt(ID));
        orderEntity.setStatus(OrderStatus.valueOf(resultSet.getString(STATUS)));
        orderEntity.setDetailStatus(resultSet.getString(STATUS_DETAIL));
        orderEntity.setDateTime(resultSet.getTimestamp(DATE_TIME));
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getInt(USER_ID));
        orderEntity.setUser(userEntity);
        return orderEntity;
    }

    private void checkObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}