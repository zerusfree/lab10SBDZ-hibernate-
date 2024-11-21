package org.example;

import org.example.entity.*;
import org.example.ui.UserManagementUI;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Users.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(BookCategory.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Loan.class)
                .buildSessionFactory();

        // Ініціалізація інтерфейсу з SessionFactory
        UserManagementUI ui = new UserManagementUI(factory);
    }
}