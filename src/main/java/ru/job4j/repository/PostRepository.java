package ru.job4j.repository;

import lombok.AllArgsConstructor;
import ru.job4j.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public List<Post> findAll() {
        return crudRepository.query(
                "from Post",
                Post.class
        );
    }

    public List<Post> findForLastDay() {
        return crudRepository.query(
                "FROM Post p WHERE TIMESTAMPDIFF(HOUR, NOW(), p.created) < 24;",
                Post.class
        );
    }

    public List<Post> findWithPhoto() {
        return crudRepository.query(
                "FROM Post p WHERE p.photo != null;",
                Post.class
        );
    }

    public List<Post> findTheMark(String brand) {
        return crudRepository.query(
                "FROM Post p WHERE p.text LIKE %:fBrand% ",
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
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", postId)
        );
    }

    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }
}