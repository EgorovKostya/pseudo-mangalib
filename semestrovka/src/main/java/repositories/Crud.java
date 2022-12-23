package repositories;

import java.util.List;
import java.util.Optional;

public interface Crud <E, ID>{

    void save(E entity);

    void delete(ID id);

    Optional<E> findById(ID id);

    List<E> findAll();

    void update(E entity);
}
