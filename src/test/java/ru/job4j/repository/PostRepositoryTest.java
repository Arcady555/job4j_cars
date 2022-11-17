package ru.job4j.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PostRepositoryTest {
    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    static CrudRepository crudRepository = new CrudRepository(sf);

    @Test
    void whenCreate() throws Exception {
        try (PostRepository postRepository = new PostRepository(crudRepository);
        PriceHistoryRepository pHR = new PriceHistoryRepository(crudRepository);
        UserRepository uR = new UserRepository(crudRepository);
        CarRepository cR = new CarRepository(crudRepository);
        DriverRepository dR = new DriverRepository(crudRepository);
        EngineRepository eR = new EngineRepository(crudRepository)
        ) {
            User user = uR.findById(1).get();
            Post post1 = new Post();
            post1.setText("Mercedes is very good!");
            post1.setCreated(LocalDateTime.now());
            post1.setUser(user);
            user.setPosts(List.of(post1));
            post1.setParticipates(List.of(user));
            postRepository.create(post1);
            PriceHistory pH = new PriceHistory(
                    0, 100, 200,
                    LocalDateTime.of(2022, 10, 10, 22, 11, 11),
                    post1);
            pHR.create(pH);
            post1.setPriceHistories(List.of(pH));
            user.setPosts(List.of(post1));
            Post result = postRepository.findById(post1.getId()).get();
            assertThat(result.getText(), is(post1.getText()));
        }
    }

    @Test
    void findTheMark() {

    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }
}