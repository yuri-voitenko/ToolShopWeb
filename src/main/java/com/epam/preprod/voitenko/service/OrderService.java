package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.entity.*;
import com.epam.preprod.voitenko.repository.*;
import com.epam.preprod.voitenko.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private TransactionManager transactionManager;
    private OrderRepository orderRepository;
    private InfoOrderedToolRepository infoOrderedToolRepository;
    private OrderBunchRepository orderBunchRepository;
    private UserRepository userRepository;
    private ToolRepository toolRepository;

    public OrderService(DataSource dataSource) {
        this.transactionManager = new TransactionManager(dataSource);
        this.orderRepository = new OrderRepository();
        this.infoOrderedToolRepository = new InfoOrderedToolRepository();
        this.orderBunchRepository = new OrderBunchRepository();
        this.userRepository = new UserRepository();
        this.toolRepository = new ToolRepository();
    }

    public OrderEntity getOrderById(Integer id) {
        return transactionManager.doInTransaction(connection -> {
            OrderEntity orderEntity = orderRepository.getEntityById(connection, id);
            if (orderEntity != null) {
                UserEntity userEntity = userRepository.getEntityById(connection, orderEntity.getUser().getId());
                orderEntity.setUser(userEntity);
                List<InfoOrderedToolEntity> orderedToolEntityList = new ArrayList<>();
                List<OrderBunchEntity> orderBunchEntities = orderBunchRepository.getEntityById(connection, orderEntity.getId());
                for (OrderBunchEntity orderBunchEntity : orderBunchEntities) {
                    InfoOrderedToolEntity orderedToolEntity = infoOrderedToolRepository.getEntityById(connection, orderBunchEntity.getInfoOrderedToolID());
                    ElectricToolEntity toolEntity = toolRepository.getEntityById(connection, orderedToolEntity.getElectricTool().getId());
                    orderedToolEntity = orderedToolEntity.setElectricTool(toolEntity);
                    orderedToolEntityList.add(orderedToolEntity);
                }
                orderEntity.setOrders(orderedToolEntityList);
            }
            return orderEntity;
        });
    }

    public boolean deleteOrderBunch(Integer id) {
        return transactionManager.doInTransaction(connection -> orderRepository.delete(connection, id));
    }

    public boolean createOrder(OrderEntity orderEntity) {
        return transactionManager.doInTransaction(connection -> {
            if (!orderRepository.create(connection, orderEntity)) {
                return false;
            }
            List<OrderBunchEntity> orderBunchEntities = new ArrayList<>();
            for (InfoOrderedToolEntity orderedToolEntity : orderEntity.getOrders()) {
                if (!infoOrderedToolRepository.create(connection, orderedToolEntity)) {
                    return false;
                }
                orderBunchEntities.add(new OrderBunchEntity(orderEntity.getId(), orderedToolEntity.getId()));
            }
            return orderBunchRepository.create(connection, orderBunchEntities);
        });
    }
}