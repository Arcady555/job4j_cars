package ru.job4j.repository;

import lombok.AllArgsConstructor;
import ru.job4j.model.Car;
import ru.job4j.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    public List<Engine> findAll() {
        return crudRepository.query(
                "from Engine",
                Engine.class
        );
    }

    public Engine create(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    public void update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }

    public void delete(int engineId) {
        crudRepository.run(
                "delete from Engine where id = :fId",
                Map.of("fId", engineId)
        );
    }

    public Optional<Engine> findById(int engineId) {
        return crudRepository.optional(
                "from Engine where id = :fId", Engine.class,
                Map.of("fId", engineId)
        );
    }
}
