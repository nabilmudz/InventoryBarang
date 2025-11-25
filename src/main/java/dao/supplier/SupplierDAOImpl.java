package dao.supplier;

import dao.BaseDAO;
import models.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl extends BaseDAO<Supplier> implements SupplierDAO {

    @Override
    public void insert(Supplier s) throws Exception {
        String sql = "INSERT INTO supplier (nama, kontak, alamat) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, s.getNama());
        ps.setString(2, s.getKontak());
        ps.setString(3, s.getAlamat());

        ps.executeUpdate();
    }

    @Override
    public List<Supplier> FindAll() throws Exception {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Supplier s = new Supplier();
            s.setId(rs.getInt("id"));
            s.setNama(rs.getString("nama"));
            s.setKontak(rs.getString("kontak"));
            s.setAlamat(rs.getString("alamat"));
            s.setCreatedAt(rs.getTimestamp("created_at"));
            s.setUpdatedAt(rs.getTimestamp("updated_at"));
            list.add(s);
        }
        return list;
    }

    @Override
    public void update(supplier s) throws Exception{
        String sql = "UPDATE supplier SET nama = ?, kontak = ?, alamat = ?, WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, s.getNama());
        ps.setString(2, s.getKontak());
        ps.setString(3, s.getAlamat());
        ps.setInt(4, s.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM supplier WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public supplier findById(int id) throws Exception{
        String sql = "SELECT * FROM supplier WHERE id = ?";
        PreparedStatement ps = conn.preparedStatement(sql);

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            Supplier s = new Supplier();
            s.setId(rs.getInt("id"));
            s.setNama(rs.getString("nama"));
            s.setKontak(rs.getString("kontak"));
            s.setAlamat(rs.getString("alamat"));
            s.setCreatedAt(rs.getTimestamp("created_at"));
            s.setUpdatedAt(rs.getTimestamp("updated_at"));

            return s;
        }
        return null;
    }

    @Override
    public Supplier findByKontak(String nama, String kontak) throws Exception{
        String sql = "SELECT * FROM supplier nama = ?, AND kontak = ?";
        PreparedStatement ps = conn.preparedStatement(sql);

        ps.setString(1, nama);
        ps.setKontak(2, kontak);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Supplier s = new Supplier();
            s.setId(rs.getInt("id"));
            s.setNama(rs.getString("nama"));
            s.setKontak(rs.getString("kontak"));
            s.setAlamat(rs.getString("alamat"));
            s.setCreatedAt(rs.getTimestamp("created_at"));
            s.setUpdatedAt(rs.getTimestamp("updated_at"));

            return s;
        }
        return null;
    }

    @Override
    public boolean existInBarang(int supplierId) throws Exception{
        String sql = "SELECT COUNT(*) FROM barang WHEN supplier_id = ?";
        PreparedStatement ps = conn.preparedStatement(sql);

        ps.setInt(1, supplierId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    @Override
    public boolean existInTransaksi(int supplierId) throws Exception {
        String sql = """
            SELECT COUNT(*)
            FROM transaksi t
            JOIN barang b ON t.barang_id = b.id
            WHERE b.supplier_id = ?
        """;

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, supplierId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
}