package services.base;

import java.util.List;

public interface BaseService<T> {
    void create(T t) throws Exception;
    List<T> getAll() throws Exception;
}
