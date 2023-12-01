package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getCorrennSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS user (
                           id  INT NOT NULL AUTO_INCREMENT ,
                           name        VARCHAR(50)    not null,
                           lastName    VARCHAR(50)    not null,
                           age         INT            not null,
                           PRIMARY KEY(id));""");
            System.out.println("Таблица создана.");
        } catch (SQLException | HibernateException e) {
            System.out.println("Таблица не создана");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user;");
            System.out.println("Таблица удалена.");
        } catch (SQLException e) {
            System.out.println("Таблицы не сущеcтвует.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем %s добавлен в базу данных.\n", name);
        } catch (HibernateException e) {
            System.out.println("Пользователь не добавлен.");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            transaction.commit();
            System.out.printf("Пользователь с ID %d удалён.\n", id);
        } catch (Exception e) {
            System.out.printf("Пользователь c ID %d не существует или не удалён.", id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            List<User> ses = session.createQuery("FROM User", User.class).getResultList();
            System.out.println(ses);
            return ses;
        } catch (HibernateException e) {
            System.out.println("Пользователи не могут быть выведены из-за ошибки.");
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            System.out.println("Все данные пользователей удалены.");
        } catch (HibernateException e) {
            System.out.println("Таблица не отчистилась или её не существует.");
        }
    }
}
