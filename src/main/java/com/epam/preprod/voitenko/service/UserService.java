package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.bean.UserBean;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.repository.UserRepository;
import com.epam.preprod.voitenko.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.List;

public class UserService {
    private TransactionManager transactionManager;
    private UserRepository repository;

    public UserService() {
        DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
        this.transactionManager = new TransactionManager(dataSource);
        this.repository = new UserRepository();
    }

    public List<UserBean> getAllUsers() {
        return transactionManager.doInTransaction(connection -> repository.getAll(connection));
    }

    public UserBean getUserById(Integer id) {
        return transactionManager.doInTransaction(connection -> repository.getEntityById(connection, id));
    }

    public UserBean updateUser(UserBean user) {
        return transactionManager.doInTransaction(connection -> repository.update(connection, user));
    }

    public boolean deleteUser(Integer id) {
        return transactionManager.doInTransaction(connection -> repository.delete(connection, id));
    }

    public boolean registerUser(UserBean user) {
        return transactionManager.doInTransaction(connection -> repository.create(connection, user));
    }

    public UserBean getUserByEmail(String email) {
        return transactionManager.doInTransaction(connection -> repository.getUserByEmail(connection, email));
    }
}