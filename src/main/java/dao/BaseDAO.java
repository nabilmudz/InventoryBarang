package dao;

import java.sql.Connection;

import config.DBConnection;

public abstract class BaseDAO {
    protected Connection conn;

    public BaseDAO() {
        this.conn = DBConnection.getInstance().getConnection();
    }

}
