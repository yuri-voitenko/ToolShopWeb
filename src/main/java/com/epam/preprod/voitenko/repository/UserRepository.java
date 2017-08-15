package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.bean.UserBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
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

public class UserRepository implements GeneralRepository<UserBean, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(UserRepository.class);

    private static final String SQL_SELECT_ALL = "SELECT * FROM users;";
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id=?;";
    private static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?;";
    private static final String SQL_UPDATE_USER = "UPDATE users SET email=?, password=?, fullName=?, phoneNumber=?, address=? WHERE id=?;";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id=?;";
    private static final String SQL_INSERT_USER = "INSERT INTO users (email, password, fullName, phoneNumber, address) VALUES (?, ?, ?, ?, ?);";

    @Override
    public List<UserBean> getAll(Connection connection) {
        checkObjectIsNull(connection);
        List<UserBean> users = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(extractUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ALL, e);
        } finally {
            close(resultSet, statement);
        }
        return users;
    }

    @Override
    public UserBean getEntityById(Connection connection, Integer id) {
        checkObjectIsNull(connection);
        UserBean userBean = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_GET_USER_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userBean = extractUser(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ENTITY_BY_ID, e);
        } finally {
            close(resultSet, statement);
        }
        return userBean;
    }


    @Override
    public UserBean update(Connection connection, UserBean entity) {
        checkObjectIsNull(connection);
        checkObjectIsNull(entity);
        UserBean oldValue = getEntityById(connection, entity.getId());
        if (oldValue == null) {
            return null;
        }
        String hashPassword = entity.getPassword();
        if (!oldValue.getPassword().equals(entity.getPassword())) {
            hashPassword = getHashPassword(hashPassword);
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, entity.getEmail());
            statement.setString(2, hashPassword);
            statement.setString(3, entity.getFullName());
            statement.setString(4, entity.getPhoneNumber());
            statement.setString(5, entity.getAddress());
            statement.setInt(6, oldValue.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_ENTITY, e);
        } finally {
            close(statement);
        }
        return oldValue;
    }

    @Override
    public boolean delete(Connection connection, Integer id) {
        checkObjectIsNull(connection);
        PreparedStatement statement = null;
        int rowsAffected = -1;
        try {
            statement = connection.prepareStatement(SQL_DELETE_USER);
            statement.setInt(1, id);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY, e);
            return false;
        } finally {
            close(statement);
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean create(Connection connection, UserBean entity) {
        checkObjectIsNull(connection);
        checkObjectIsNull(entity);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_USER);
            statement.setString(1, entity.getEmail());
            statement.setString(2, getHashPassword(entity.getPassword()));
            statement.setString(3, entity.getFullName());
            statement.setString(4, entity.getPhoneNumber());
            statement.setString(5, entity.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_CREATE_ENTITY, e);
            return false;
        } finally {
            close(statement);
            UserBean actual = getUserByEmail(connection, entity.getEmail());
            if (actual != null) {
                entity.setId(actual.getId());
            }
        }
        return true;
    }

    private UserBean getUserByEmail(Connection connection, String email) {
        checkObjectIsNull(connection);
        checkObjectIsNull(email);
        UserBean userBean = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_GET_USER_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userBean = extractUser(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ENTITY_BY_EMAIL, e);
        } finally {
            close(resultSet, statement);
        }
        return userBean;
    }

    private String getHashPassword(String password) {
        String salt = RandomStringUtils.random(10);
        return DigestUtils.md5Hex(password + salt);
    }

    private UserBean extractUser(ResultSet resultSet) throws SQLException {
        checkObjectIsNull(resultSet);
        UserBean userBean = new UserBean();
        userBean.setId(resultSet.getInt(ID));
        userBean.setEmail(resultSet.getString(EMAIL));
        userBean.setPassword(resultSet.getString(PASSWORD));
        userBean.setFullName(resultSet.getString(FULL_NAME));
        userBean.setPhoneNumber(resultSet.getString(PHONE_NUMBER));
        userBean.setAddress(resultSet.getString(ADDRESS));
        return userBean;
    }

    private void checkObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}