package ru.job4j.model.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
      /**
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "INSERT INTO auto_user (login, password) VALUES  (:fLogin, :fPassword);")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
       */
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
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

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
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

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
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

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Optional<User> result = Optional.empty();
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                "from User as i where i.id = :fId", User.class)
                .setParameter("fId", userId);
        User user = session.get(User.class, userId);
        result = Optional.of(user);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        List<User> result = new ArrayList<>();
        Session session = sf.openSession();
        session.beginTransaction();
        List list = session.createQuery("from User").list();
        List<User> userList = (List<User>) list;
        for (User user : userList) {
            if (user.getLogin().contains(key)) {
                result.add(user);
            }
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
    /**
        Optional<User> result = Optional.empty();
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "from User as i where i.login = :fLogin", User.class)
                .setParameter("fLogin", login);
        User user =session.get(User.class, id);
        result = Optional.of(user);
        session.getTransaction().commit();
        session.close();
        return result; */
        Optional<User> result = Optional.empty();
        Session session = sf.openSession();
        Criteria userCriteria = session.createCriteria(User.class);
        userCriteria.add(Restrictions.eq("login", login));
        User user = (User) userCriteria.uniqueResult();
        result = Optional.of(user);
        session.close();
        return result;
    }
}