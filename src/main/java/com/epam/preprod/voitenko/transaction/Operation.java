package com.epam.preprod.voitenko.transaction;

import java.sql.Connection;

public interface Operation<T> {
    T operation(Connection connection);
}