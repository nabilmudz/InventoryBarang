package dao;

import java.util.List;

public interface CrudDAO<T> {
    void insert(T entity);
    void update(T entity);
    void delete(int id);
    T findById(int id);
    List<T> findAll();
}
