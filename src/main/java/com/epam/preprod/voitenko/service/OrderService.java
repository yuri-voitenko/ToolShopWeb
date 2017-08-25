package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.entity.OrderEntity;
import com.epam.preprod.voitenko.repository.OrderRepository;
import com.epam.preprod.voitenko.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.List;

public class OrderService {
    private TransactionManager transactionManager;
    private OrderRepository repository;

    public OrderService(DataSource dataSource) {
        this.transactionManager = new TransactionManager(dataSource);
        this.repository = new OrderRepository();
    }

    public List<OrderEntity> getAllOrders() {
        return transactionManager.doInTransaction(connection -> repository.getAll(connection));
    }

    public OrderEntity getOrderById(Integer id) {
        return transactionManager.doInTransaction(connection -> repository.getEntityById(connection, id));
    }

    public OrderEntity updateOrder(OrderEntity orderEntity) {
        return transactionManager.doInTransaction(connection -> repository.update(connection, orderEntity));
    }

    public boolean deleteOrder(Integer id) {
        return transactionManager.doInTransaction(connection -> repository.delete(connection, id));
    }

    public boolean createOrder(OrderEntity orderEntity) {
        return transactionManager.doInTransaction(connection -> repository.create(connection, orderEntity));
    }
}