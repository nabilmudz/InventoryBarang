package dao;

import config.DBConnection;
import java.sql.Connection;

public abstract class BaseDAO<T> implements CrudDAO<T> {
    protected Connection conn;

    protected BaseDAO() {
        this.conn = DBConnection.getInstance().getConnection();
    }
    
    protected Connection getConnection() {
        return DBConnection.getInstance().getConnection();
    }
}
