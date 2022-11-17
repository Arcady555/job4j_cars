package ru.job4j.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ThreadSafe
public class DriverRepository implements AutoCloseable {
    private final CrudRepository crudRepository;

    public List<Driver> findAll() {
        return crudRepository.query(
                "from Driver",
                Driver.class
        );
    }

    public Driver create(Driver driver) {
        crudRepository.run(session -> session.persist(driver));
        return driver;
    }

    public void update(Driver driver) {
        crudRepository.run(session -> session.merge(driver));
    }

    public void delete(int driverId) {
        crudRepository.run(
                "delete from Driver where id = :fId",
                Map.of("fId", driverId)
        );
    }

    public Optional<Driver> findById(int driverId) {
        return crudRepository.optional(
                "from Driver where id = :fId", Driver.class,
                Map.of("fId", driverId)
        );
    }

    @Override
    public void close() throws Exception {

    }
}