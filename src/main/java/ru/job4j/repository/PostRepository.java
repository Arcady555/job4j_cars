package ru.job4j.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ThreadSafe
public class PostRepository implements AutoCloseable {
    private final CrudRepository crudRepository;

    public List<Post> findAll() {
        return crudRepository.query(
                "select distinct p from Post p join fetch p.priceHistories join fetch p.participates",
                Post.class
        );
    }

    public List<Post> findForLastDay() {
        return crudRepository.query(
                "select distinct p from Post p join fetch p.priceHistories join fetch p.participates"
                        + " WHERE p.created BETWEEN :fTimeNow AND :fTimeYesterday;",
                Post.class,
                Map.of("fTimeNow", LocalDateTime.now(), "fTimeYesterday", LocalDateTime.now().minusHours(24))
        );
    }

    public List<Post> findWithPhoto() {
        return crudRepository.query(
                "select distinct p from Post p join fetch p.priceHistories join fetch p.participates"
                        + " WHERE p.photo != null;",
                Post.class
        );
    }

    public List<Post> findTheMark(String brand) {
        return crudRepository.query(
                "select distinct p from Post p join fetch p.priceHistories join fetch p.participates"
                        + " WHERE p.text LIKE %:fBrand% ",
                Post.class,
                Map.of("fBrand", brand)

        );
    }

    public Post create(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    public void delete(int postId) {
        Post post = findById(postId).get();
        crudRepository.run(session -> session.delete(post));
    }

    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "select distinct p from Post p join fetch p.priceHistories "
                        + "join p.participates"
                        + " where p.id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    @Override
    public void close() throws Exception {

    }
}