package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.bean.UserBean;
import com.epam.preprod.voitenko.repository.UserRepository;
import com.epam.preprod.voitenko.transaction.IOperation;
import com.epam.preprod.voitenko.transaction.TransactionManager;

import java.sql.Connection;
import java.util.List;

public class UserService {
    private TransactionManager transactionManager;
    private UserRepository repository;

    public UserService() {
        this.transactionManager = new TransactionManager();
        this.repository = new UserRepository();
    }

    public List<UserBean> getAllUsers() {
        return transactionManager.doInTransaction(new IOperation<List<UserBean>>() {
            @Override
            public List<UserBean> operation(Connection connection) {
                return repository.getAll(connection);
            }
        });
    }

    public UserBean getUserById(Integer id) {
        return transactionManager.doInTransaction(new IOperation<UserBean>() {
            @Override
            public UserBean operation(Connection connection) {
                return repository.getEntityById(connection, id);
            }
        });
    }

    public UserBean updateUser(UserBean user) {
        return transactionManager.doInTransaction(new IOperation<UserBean>() {
            @Override
            public UserBean operation(Connection connection) {
                return repository.update(connection, user);
            }
        });
    }

    public boolean deleteUser(Integer id) {
        return transactionManager.doInTransaction(new IOperation<Boolean>() {
            @Override
            public Boolean operation(Connection connection) {
                return repository.delete(connection, id);
            }
        });
    }

    public boolean registerUser(UserBean user) {
        return transactionManager.doInTransaction(new IOperation<Boolean>() {
            @Override
            public Boolean operation(Connection connection) {
                return repository.create(connection, user);
            }
        });
    }
}