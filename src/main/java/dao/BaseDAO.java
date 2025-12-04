package dao;

import config.DBConnection;
import java.sql.Connection;

public abstract class BaseDAO<T> implements CrudDAO<T> {
    protected Connection getConnection() {
        return DBConnection.getInstance().getConnection();
    }
}
