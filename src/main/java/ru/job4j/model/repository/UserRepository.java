package ru.job4j.model.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    public User create(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE auto_user SET login = :fLogin, password = :fPassword WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE auto_user WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        session.beginTransaction();
        Comparator<User> comparator = Comparator.comparingInt(User::getId);
        List result = session.createQuery("from User").list();
        result.sort(comparator);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<User> result = session.createQuery(
                "from User as i where i.id = :fId", User.class)
                .setParameter("fId", userId)
                .uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from User as i where i.login like :fkey")
                .setParameter("fkey", key)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<User> result = session.createQuery("from User as i where i.login = :fLogin", User.class)
                .setParameter("fLogin", login)
                .uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}