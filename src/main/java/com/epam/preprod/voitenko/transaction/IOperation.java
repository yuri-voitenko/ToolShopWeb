package com.epam.preprod.voitenko.transaction;

import java.sql.Connection;

public interface IOperation<T> {
    T operation(Connection connection);
}
