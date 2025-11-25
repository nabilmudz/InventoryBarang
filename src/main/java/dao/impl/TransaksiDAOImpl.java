package dao.impl;

import config.DBConnection;
import dao.TransaksiDAO;
import models.Transaksi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAOImpl implements TransaksiDAO {

    @Override
    public void create(Transaksi t) throws Exception {
        String sql = "INSERT INTO transaksi (barang_id, qty, jenis, tanggal, created_by, catatan) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, t.getBarangId());
        ps.setInt(2, t.getQty());
        ps.setString(3, t.getJenis());
        ps.setTimestamp(4, new Timestamp(t.getTanggal().getTime()));
        ps.setObject(5, t.getCreatedBy());
        ps.setString(6, t.getCatatan());

        ps.executeUpdate();
    }

    @Override
    public List<Transaksi> findAll() throws Exception {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Transaksi t = new Transaksi();
            t.setId(rs.getInt("id"));
            t.setBarangId(rs.getInt("barang_id"));
            t.setQty(rs.getInt("qty"));
            t.setJenis(rs.getString("jenis"));
            t.setTanggal(rs.getTimestamp("tanggal"));
            t.setCreatedBy((Integer) rs.getObject("created_by"));
            t.setCatatan(rs.getString("catatan"));
            list.add(t);
        }
        return list;
    }
}
