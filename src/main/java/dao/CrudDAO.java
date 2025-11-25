package dao;
import java.util.List;

public interface CrudDAO<T> {
    void insert(T entity) throws Exception;
    void update(T entity) throws Exception;
    void delete(int id) throws Exception;
    T findById(int id) throws Exception;
    List<T> findAll() throws Exception;
}
