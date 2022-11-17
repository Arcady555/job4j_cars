package ru.job4j.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.PriceHistory;

@Repository
@AllArgsConstructor
@ThreadSafe
public class PriceHistoryRepository implements AutoCloseable {
    private final CrudRepository crudRepository;

    public PriceHistory create(PriceHistory pH) {
        crudRepository.run(session -> session.persist(pH));
        return pH;
    }

    @Override
    public void close() throws Exception {

    }
}
