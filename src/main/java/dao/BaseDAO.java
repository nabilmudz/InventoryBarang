package main.java.dao;

import java.sql.Connection;

import main.java.config.DBConnection;

public abstract class BaseDAO {
    protected Connection conn;

    public BaseDAO() {
        this.conn = DBConnection.getInstance().getConnection();
    }

}
