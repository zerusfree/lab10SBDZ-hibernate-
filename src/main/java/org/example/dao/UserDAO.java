package org.example.dao;

import org.example.entity.Users; // Переконайтеся, що імпорт правильний
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDAO {
    private SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Users> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Users> query = session.createQuery("FROM Users", Users.class); // Перевірте, що "User" співпадає з ім'ям класу
            return query.list();
        }
    }

    public void saveUser(Users user) {
        Session session = org.example.util.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteUser(int userId) {
        Session session = org.example.util.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Users user = session.get(Users.class, userId);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Users> filterUsersByRole(String role) {
        Session session = org.example.util.HibernateUtil.getSessionFactory().openSession();
        Query<Users> query = session.createQuery("FROM Users WHERE role = :role", Users.class);
        query.setParameter("role", role);
        return query.getResultList();
    }
}
