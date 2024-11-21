package org.example.ui;

import org.example.dao.UserDAO;
import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserManagementUI {
    private JFrame frame;
    private JTable table;
    private UserDAO userDAO;

    public UserManagementUI(SessionFactory sessionFactory) {
        this.userDAO = new UserDAO(sessionFactory);
        frame = new JFrame("User Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Створення таблиці для відображення користувачів
        String[] columns = {"User ID", "First Name", "Last Name", "Email", "Phone"};
        Object[][] data = fetchData();
        table = new JTable(data, columns);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Кнопки для додавання і видалення користувачів
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add User");
        JButton deleteButton = new JButton("Delete User");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        buttonsPanel.add(addButton);
        buttonsPanel.add(deleteButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private Object[][] fetchData() {
        List<Users> users = userDAO.getAllUsers();
        Object[][] data = new Object[users.size()][5];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i).getUserId();
            data[i][1] = users.get(i).getFirstName();
            data[i][2] = users.get(i).getLastName();
            data[i][3] = users.get(i).getEmail();
            data[i][4] = users.get(i).getPhone();
        }
        return data;
    }

    private void addUser() {
        // Створюємо діалогове вікно для введення даних користувача
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField roleField = new JTextField();
        JTextField photoUrlField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleField);
        panel.add(new JLabel("Photo URL:"));
        panel.add(photoUrlField);

        int option = JOptionPane.showConfirmDialog(frame, panel, "Enter User Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Створюємо нового користувача і додаємо до бази
            Users newUser = new Users(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    usernameField.getText(),
                    new String(passwordField.getPassword()),
                    roleField.getText(),
                    photoUrlField.getText()
            );
            userDAO.saveUser(newUser);
            refreshTable();
        }
    }

    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int user_id = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userDAO.deleteUser(user_id);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a user to delete.", "No User Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshTable() {
        Object[][] data = fetchData();
        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"User ID", "First Name", "Last Name", "Email", "Phone"}));
    }
}
