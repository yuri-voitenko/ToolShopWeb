package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import com.epam.preprod.voitenko.repository.ToolRepository;
import com.epam.preprod.voitenko.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.List;

public class ToolService {
    private TransactionManager transactionManager;
    private ToolRepository repository;

    public ToolService(DataSource dataSource) {
        this.transactionManager = new TransactionManager(dataSource);
        this.repository = new ToolRepository();
    }

    public List<ElectricToolEntity> getAllTools() {
        return transactionManager.doInTransaction(connection -> repository.getAll(connection));
    }

    public ElectricToolEntity getToolById(Integer id) {
        return transactionManager.doInTransaction(connection -> repository.getEntityById(connection, id));
    }

    public ElectricToolEntity updateTool(ElectricToolEntity toolEntity) {
        return transactionManager.doInTransaction(connection -> repository.update(connection, toolEntity));
    }

    public boolean deleteTool(Integer id) {
        return transactionManager.doInTransaction(connection -> repository.delete(connection, id));
    }

    public boolean registerTool(ElectricToolEntity toolEntity) {
        return transactionManager.doInTransaction(connection -> repository.create(connection, toolEntity));
    }

    public List<String> getCategories() {
        return transactionManager.doInTransaction(connection -> repository.getCategories(connection));
    }

    public List<String> getManufacturers() {
        return transactionManager.doInTransaction(connection -> repository.getManufacturers(connection));
    }
}