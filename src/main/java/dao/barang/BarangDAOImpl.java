package dao.barang;

import dao.BaseDAO;
import models.Barang;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BarangDAOImpl extends BaseDAO<Barang> implements BarangDAO {

    @Override
    public void insert(Barang b) throws Exception {
        String sql = "INSERT INTO barang (nama, stok, harga, supplier_id) VALUES (?, ?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, b.getNama());
            ps.setInt(2, b.getStok());
            ps.setDouble(3, b.getHarga());
            ps.setInt(4, b.getSupplierId());
            ps.executeUpdate();

            // ambil ID yang baru dibuat
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    b.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public List<Barang> findAll() throws Exception {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Barang b = mapResultSetToBarang(rs);
                list.add(b);
            }
        }

        return list;
    }

    @Override
    public void update(Barang b) throws Exception {
        String sql = "UPDATE barang SET nama = ?, stok = ?, harga = ?, supplier_id = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, b.getNama());
            ps.setInt(2, b.getStok());
            ps.setDouble(3, b.getHarga());
            ps.setInt(4, b.getSupplierId());
            ps.setInt(5, b.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM barang WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Barang findById(int id) throws Exception {
        String sql = "SELECT * FROM barang WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBarang(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Barang findByNama(String nama) throws Exception {
        String sql = "SELECT * FROM barang WHERE nama = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, nama);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBarang(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean existInTransaksi(int barangId) throws Exception {
        String sql = "SELECT COUNT(*) FROM transaksi WHERE barang_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, barangId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private Barang mapResultSetToBarang(ResultSet rs) throws SQLException {
        Barang b = new Barang();
        b.setId(rs.getInt("id"));
        b.setNama(rs.getString("nama"));
        b.setStok(rs.getInt("stok"));
        b.setHarga(rs.getDouble("harga"));
        b.setSupplierId(rs.getInt("supplier_id"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        if (createdAt != null) {
            b.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            b.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return b;
    }
}
