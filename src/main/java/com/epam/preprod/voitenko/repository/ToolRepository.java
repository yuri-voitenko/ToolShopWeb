package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_CREATE_TOOL;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_DELETE_TOOL;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_ALL_CATEGORIES;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_ALL_MANUFACTURERS;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_ALL_TOOLS;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_TOOL_BY_ID;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_UPDATE_TOOL;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ADDITIONAL_IMAGE;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.CATEGORY;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.COST;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ID;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.MAIN_IMAGE;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.MANUFACTURER;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.MAX_ROTATION_SPEED;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.NAME;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.POWER;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.WEIGHT;
import static com.epam.preprod.voitenko.constant.Constatns.PATH_TO_TOOL_IMAGES;
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
    private static final String SQL_FOUND_ROWS = "SELECT FOUND_ROWS();";

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
        return getLisOfValuesFromColumn(connection, SQL_GET_CATEGORIES, CATEGORY, CANNOT_GET_ALL_CATEGORIES);
    }

    public List<String> getManufacturers(Connection connection) {
        return getLisOfValuesFromColumn(connection, SQL_GET_MANUFACTURERS, MANUFACTURER, CANNOT_GET_ALL_MANUFACTURERS);
    }

    public List<ElectricToolEntity> getToolsByFilter(Connection connection, String sqlQuery) {
        List<ElectricToolEntity> tools = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
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

    public long getNumberSuitableTools(Connection connection, String sqlQuery) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long numberSuitableRows = 0;
        try {
            statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery();
            statement = connection.prepareStatement(SQL_FOUND_ROWS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numberSuitableRows = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ALL_TOOLS, e);
        } finally {
            close(resultSet, statement);
        }
        return numberSuitableRows;
    }

    private List<String> getLisOfValuesFromColumn(Connection connection, String sqlQuery, String columnName, String errorMessage) {
        List<String> fieldList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                fieldList.add(resultSet.getString(columnName));
            }
        } catch (SQLException e) {
            LOGGER.error(errorMessage, e);
        } finally {
            close(resultSet, statement);
        }
        return fieldList;
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
        Object mainImage = resultSet.getObject(MAIN_IMAGE);
        if (isExistToolImage(mainImage)) {
            toolEntity.setMainImage(mainImage.toString());
        }
        Object additionalImage = resultSet.getObject(ADDITIONAL_IMAGE);
        if (isExistToolImage(additionalImage)) {
            toolEntity.setAdditionalImage(additionalImage.toString());
        }
        return toolEntity;
    }

    private boolean isExistToolImage(Object image) {
        if (image != null) {
            String fullPath = System.getProperty("user.dir") + PATH_TO_TOOL_IMAGES + image.toString();
            return Paths.get(fullPath).toFile().exists();
        }
        return false;
    }

    private void checkObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}