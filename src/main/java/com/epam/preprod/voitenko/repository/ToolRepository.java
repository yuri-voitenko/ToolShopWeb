package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.entity.ElectricToolEntity;
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

public class ToolRepository implements GeneralRepository<ElectricToolEntity, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(ToolRepository.class);

    private static final String SQL_SELECT_ALL = "SELECT * FROM tools;";
    private static final String SQL_GET_CATEGORIES = "SELECT DISTINCT category FROM tools ORDER BY category;";
    private static final String SQL_GET_MANUFACTURERS = "SELECT DISTINCT manufacturer FROM tools ORDER BY manufacturer;";
    private static final String SQL_GET_TOOL_BY_ID = "SELECT * FROM tools WHERE id=?;";
    private static final String SQL_UPDATE_TOOL = "UPDATE tools SET name=?, category=?, manufacturer=?, power=?, maxRotationSpeed=?, weight=?, cost=?, mainImage=?, additionalImage=? WHERE id=?;";
    private static final String SQL_DELETE_TOOL = "DELETE FROM tools WHERE id=?;";
    private static final String SQL_INSERT_TOOL = "INSERT INTO tools (name, category, manufacturer, power, maxRotationSpeed, weight, cost, mainImage, additionalImage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

    @Override
    public List<ElectricToolEntity> getAll(Connection connection) {
        List<ElectricToolEntity> tools = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tools.add(extractTool(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ALL_TOOLS, e);
        } finally {
            close(resultSet, statement);
        }
        return tools;
    }

    @Override
    public ElectricToolEntity getEntityById(Connection connection, Integer id) {
        ElectricToolEntity toolEntity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_GET_TOOL_BY_ID);
            statement.setInt(++index, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                toolEntity = extractTool(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_TOOL_BY_ID, e);
        } finally {
            close(resultSet, statement);
        }
        return toolEntity;
    }

    @Override
    public ElectricToolEntity update(Connection connection, ElectricToolEntity entity) {
        checkObjectIsNull(entity);
        ElectricToolEntity oldValue = getEntityById(connection, entity.getId());
        if (oldValue == null || oldValue.equals(entity)) {
            return null;
        }
        PreparedStatement statement = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_UPDATE_TOOL);
            statement.setString(++index, entity.getName());
            statement.setString(++index, entity.getCategory());
            statement.setString(++index, entity.getManufacturer());
            statement.setInt(++index, entity.getPower());
            statement.setInt(++index, entity.getMaxRotationSpeed());
            statement.setBigDecimal(++index, entity.getWeight());
            statement.setBigDecimal(++index, entity.getCost());
            statement.setString(++index, entity.getMainImage());
            statement.setString(++index, entity.getAdditionalImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_TOOL, e);
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
            statement = connection.prepareStatement(SQL_DELETE_TOOL);
            int index = 0;
            statement.setInt(++index, id);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_TOOL, e);
            return false;
        } finally {
            close(statement);
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean create(Connection connection, ElectricToolEntity entity) {
        checkObjectIsNull(entity);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_INSERT_TOOL, RETURN_GENERATED_KEYS);
            statement.setString(++index, entity.getName());
            statement.setString(++index, entity.getCategory());
            statement.setString(++index, entity.getManufacturer());
            statement.setInt(++index, entity.getPower());
            statement.setInt(++index, entity.getMaxRotationSpeed());
            statement.setBigDecimal(++index, entity.getWeight());
            statement.setBigDecimal(++index, entity.getCost());
            statement.setString(++index, entity.getMainImage());
            statement.setString(++index, entity.getAdditionalImage());

            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                int id = (int) resultSet.getLong(1);
                entity.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_CREATE_TOOL, e);
            return false;
        } finally {
            close(resultSet, statement);
        }
        return true;
    }

    public List<String> getCategories(Connection connection) {
        List<String> categories = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_GET_CATEGORIES);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                categories.add(resultSet.getString(CATEGORY));
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ALL_CATEGORIES, e);
        } finally {
            close(resultSet, statement);
        }
        return categories;
    }

    public List<String> getManufacturers(Connection connection) {
        List<String> manufacturers = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_GET_MANUFACTURERS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(resultSet.getString(MANUFACTURER));
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ALL_MANUFACTURERS, e);
        } finally {
            close(resultSet, statement);
        }
        return manufacturers;
    }

    private ElectricToolEntity extractTool(ResultSet resultSet) throws SQLException {
        checkObjectIsNull(resultSet);
        ElectricToolEntity toolEntity = new ElectricToolEntity();
        toolEntity.setId(resultSet.getInt(ID));
        toolEntity.setName(resultSet.getString(NAME));
        toolEntity.setCategory(resultSet.getString(CATEGORY));
        toolEntity.setManufacturer(resultSet.getString(MANUFACTURER));
        toolEntity.setPower(resultSet.getInt(POWER));
        toolEntity.setMaxRotationSpeed(resultSet.getInt(MAX_ROTATION_SPEED));
        toolEntity.setWeight(resultSet.getBigDecimal(WEIGHT));
        toolEntity.setCost(resultSet.getBigDecimal(COST));
        toolEntity.setMainImage(resultSet.getString(MAIN_IMAGE));
        toolEntity.setAdditionalImage(resultSet.getString(ADDITIONAL_IMAGE));
        return toolEntity;
    }

    private void checkObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}