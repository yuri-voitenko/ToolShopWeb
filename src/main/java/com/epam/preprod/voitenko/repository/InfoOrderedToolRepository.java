package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import com.epam.preprod.voitenko.entity.InfoOrderedToolEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.*;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class InfoOrderedToolRepository implements GeneralRepository<InfoOrderedToolEntity, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(InfoOrderedToolRepository.class);

    private static final String SQL_SELECT_ALL = "SELECT * FROM info_ordered_tools;";
    private static final String SQL_GET_INFO_ORDERED_TOOL_BY_ID = "SELECT * FROM info_ordered_tools WHERE id=?;";
    private static final String SQL_UPDATE_INFO_ORDERED_TOOL = "UPDATE info_ordered_tools SET toolID=?, cost=?, amount=? WHERE id=?;";
    private static final String SQL_DELETE_INFO_ORDERED_TOOL = "DELETE FROM info_ordered_tools WHERE id=?;";
    private static final String SQL_INSERT_INFO_ORDERED_TOOL = "INSERT INTO info_ordered_tools (toolID, cost, amount) VALUES (?, ?, ?);";

    @Override
    public List<InfoOrderedToolEntity> getAll(Connection connection) {
        List<InfoOrderedToolEntity> orders = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractInfoOrderedTool(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ALL_INFO_ORDERED_TOOLS, e);
        } finally {
            close(resultSet, statement);
        }
        return orders;
    }

    @Override
    public InfoOrderedToolEntity getEntityById(Connection connection, Integer id) {
        InfoOrderedToolEntity infoOrderedToolEntity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_GET_INFO_ORDERED_TOOL_BY_ID);
            statement.setInt(++index, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                infoOrderedToolEntity = extractInfoOrderedTool(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_INFO_ORDERED_TOOL_BY_ID, e);
        } finally {
            close(resultSet, statement);
        }
        return infoOrderedToolEntity;
    }

    @Override
    public InfoOrderedToolEntity update(Connection connection, InfoOrderedToolEntity entity) {
        checkObjectIsNull(entity);
        InfoOrderedToolEntity oldValue = getEntityById(connection, entity.getId());
        if (oldValue == null) {
            return null;
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_INFO_ORDERED_TOOL);
            setInfoOrderedToolParametersAndExecuteupdate(entity, statement);
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_INFO_ORDERED_TOOL, e);
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
            statement = connection.prepareStatement(SQL_DELETE_INFO_ORDERED_TOOL);
            int index = 0;
            statement.setInt(++index, id);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_INFO_ORDERED_TOOL, e);
            return false;
        } finally {
            close(statement);
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean create(Connection connection, InfoOrderedToolEntity entity) {
        checkObjectIsNull(entity);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_INFO_ORDERED_TOOL, RETURN_GENERATED_KEYS);
            setInfoOrderedToolParametersAndExecuteupdate(entity, statement);
            resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                int id = (int) resultSet.getLong(1);
                entity.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_CREATE_INFO_ORDERED_TOOL, e);
            return false;
        } finally {
            close(statement);
        }
        return true;
    }

    private void setInfoOrderedToolParametersAndExecuteupdate(InfoOrderedToolEntity entity, PreparedStatement statement) throws SQLException {
        int index = 0;
        statement.setInt(++index, entity.getElectricTool().getId());
        statement.setBigDecimal(++index, entity.getUnitPrice());
        statement.setInt(++index, entity.getAmount());
        statement.executeUpdate();
    }

    private InfoOrderedToolEntity extractInfoOrderedTool(ResultSet resultSet) throws SQLException {
        checkObjectIsNull(resultSet);
        int id = resultSet.getInt(ID);
        ElectricToolEntity electricToolEntity = new ElectricToolEntity();
        electricToolEntity.setId(resultSet.getInt(TOOL_ID));
        int amount = resultSet.getInt(AMOUNT);
        BigDecimal cost = resultSet.getBigDecimal(COST);
        return new InfoOrderedToolEntity(id, electricToolEntity, cost, amount);
    }

    private void checkObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}