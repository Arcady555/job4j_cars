package ru.job4j.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PostRepositoryTest {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure().build();
    private static final SessionFactory SF = new MetadataSources(REGISTRY)
            .buildMetadata().buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SF);
    private static final PostRepository POST_REPOSITORY = new PostRepository(CRUD_REPOSITORY);
    private static final PriceHistoryRepository HISTORY_REPOSITORY = new PriceHistoryRepository(CRUD_REPOSITORY);
    private  static final UserRepository USER_REPOSITORY = new UserRepository(CRUD_REPOSITORY);
    private static Post post1;
    private static Post post2;

    @BeforeAll
    public static void init() {
        try {
            User user1 = new User();
            user1.setLogin("A");
            user1.setPassword("B");
            user1.setPosts(List.of(post1));
            USER_REPOSITORY.create(user1);
            User user2 = new User();
            user2.setLogin("C");
            user2.setPassword("D");
            user2.setPosts(List.of(post1));
            USER_REPOSITORY.create(user2);
            User user3 = new User();
            user3.setLogin("E");
            user3.setPassword("F");
            user3.setPosts(List.of(post1));
            USER_REPOSITORY.create(user1);
            post1 = new Post();
            post1.setText("Mercedes is good!");
            post1.setCreated(LocalDateTime.now());
            post1.setUser(user1);
            post1.setParticipates(List.of(user1, user2));
            PriceHistory pH1 = new PriceHistory(
                    0, 100, 200,
                    LocalDateTime.of(2022, 10, 10, 22, 11, 11),
                    post1);
            HISTORY_REPOSITORY.create(pH1);
            post1.setPriceHistories(List.of(pH1));
            POST_REPOSITORY.create(post1);
            post2 = new Post();
            post2.setText("BMW is excellent!");
            post2.setCreated(LocalDateTime.now());
            post2.setUser(user3);
            post2.setParticipates(List.of(user1));
            PriceHistory pH21 = new PriceHistory(
                    0, 150, 250,
                    LocalDateTime.of(2022, 9, 15, 22, 11, 11),
                    post2);
            PriceHistory pH22 = new PriceHistory(
                    0, 300, 200,
                    LocalDateTime.of(2022, 10, 19, 22, 11, 11),
                    post2);
            HISTORY_REPOSITORY.create(pH21);
            HISTORY_REPOSITORY.create(pH22);
            post2.setPriceHistories(List.of(pH21, pH22));
            POST_REPOSITORY.create(post2);
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(REGISTRY);
        }
    }

    @Test
    void whenCreate() {
        try {
            Post result = POST_REPOSITORY.findById(post1.getId()).get();
            assertThat(result.getText(), is(post1.getText()));
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(REGISTRY);
        }
    }

    @Test
    void findTheMark() {
        try {
            List<Post> result = POST_REPOSITORY.findTheMark("Mercedes");
            assertThat(result, is(List.of(post1)));
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(REGISTRY);
        }
    }

    @Test
    void update() {
        try {
            post1.setText("Lada is a DREAM!!!");
            POST_REPOSITORY.update(post1);
            assertThat(
                    POST_REPOSITORY.findById(post1.getId()).get().getText(),
                    is("Lada is a DREAM!!!")
            );
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(REGISTRY);
        }
    }

    @Test
    void delete() {
        try {
            POST_REPOSITORY.delete(post2.getId());
            Assertions.assertNull(POST_REPOSITORY.findById(post2.getId()));
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(REGISTRY);
        }
    }
}