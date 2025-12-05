package dao.transaksi;

import dao.BaseDAO;
import models.Transaksi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAOImpl extends BaseDAO<Transaksi> implements TransaksiDAO {

    @Override
    public void insert(Transaksi t) throws Exception {
        String sql = "INSERT INTO transaksi (barang_id, qty, jenis, tanggal, created_by, catatan) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, t.getBarangId());
            ps.setInt(2, t.getQty());
            ps.setString(3, t.getJenis());
            ps.setTimestamp(4, new Timestamp(t.getTanggal().getTime()));
            ps.setObject(5, t.getCreatedBy());
            ps.setString(6, t.getCatatan());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        }
    }


    @Override
    public List<Transaksi> findAll() throws Exception {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT id, barang_id, qty, jenis, tanggal, created_by, catatan FROM transaksi ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        }

        return list;
    }

    @Override public void update(Transaksi t) { /* tidak dipakai */ }

    @Override public void delete(int id) { /* tidak dipakai */ }

    @Override public Transaksi findById(int id) { return null; }
}
