package org.producerconsumer.repository;

import org.producerconsumer.HibernateUtil;
import org.producerconsumer.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;

public class UserRepositoryImpl implements Repository<User> {
    private final SessionFactory sessionFactory;
    public UserRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    public void createUser(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public List<User> listUsers() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return new LinkedList<>();
    }
    public void deleteAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
