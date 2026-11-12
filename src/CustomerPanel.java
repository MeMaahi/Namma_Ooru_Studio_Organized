import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CustomerPanel extends JFrame {

    private final Color MAROON = new Color(90, 24, 53);
    private final Color GOLD = new Color(198, 161, 91);
    private final Color CREAM = new Color(251, 248, 244);
    private final Color TEXT = new Color(55, 45, 50);

    private JTextField customerIdField;
    private JTextField customerNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JTable customerTable;
    private DefaultTableModel tableModel;

    public CustomerPanel() {

        setTitle("Namma Ooru Studio - Customer Management");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadCustomers();
    }

    // Creates the customer management interface
    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CREAM);

        // Creates the page header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(MAROON);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        JLabel titleLabel =
                new JLabel("CUSTOMER MANAGEMENT");

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(
                new Font("Serif", Font.BOLD, 26)
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        JLabel studioLabel =
                new JLabel("NAMMA OORU STUDIO");

        studioLabel.setForeground(GOLD);
        studioLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        headerPanel.add(
                studioLabel,
                BorderLayout.EAST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // Creates the customer input form
        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBackground(CREAM);
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 30, 12, 30
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(7, 8, 7, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        addFormLabel(
                formPanel,
                "Customer ID:",
                gbc,
                0,
                0
        );

        customerIdField =
                new JTextField(15);

        customerIdField.setEditable(false);

        gbc.gridx = 1;
        gbc.gridy = 0;

        formPanel.add(
                customerIdField,
                gbc
        );

        addFormLabel(
                formPanel,
                "Customer Name:",
                gbc,
                0,
                1
        );

        customerNameField =
                new JTextField(15);

        gbc.gridx = 1;
        gbc.gridy = 1;

        formPanel.add(
                customerNameField,
                gbc
        );

        addFormLabel(
                formPanel,
                "Phone:",
                gbc,
                2,
                0
        );

        phoneField =
                new JTextField(15);

        gbc.gridx = 3;
        gbc.gridy = 0;

        formPanel.add(
                phoneField,
                gbc
        );

        addFormLabel(
                formPanel,
                "Email:",
                gbc,
                2,
                1
        );

        emailField =
                new JTextField(15);

        gbc.gridx = 3;
        gbc.gridy = 1;

        formPanel.add(
                emailField,
                gbc
        );

        addFormLabel(
                formPanel,
                "Username:",
                gbc,
                0,
                2
        );

        usernameField =
                new JTextField(15);

        gbc.gridx = 1;
        gbc.gridy = 2;

        formPanel.add(
                usernameField,
                gbc
        );

        addFormLabel(
                formPanel,
                "Password:",
                gbc,
                2,
                2
        );

        passwordField =
                new JPasswordField(15);

        gbc.gridx = 3;
        gbc.gridy = 2;

        formPanel.add(
                passwordField,
                gbc
        );

        // Creates the CRUD buttons
        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                10
                        )
                );

        buttonPanel.setBackground(CREAM);

        JButton addButton =
                createButton("ADD");

        JButton updateButton =
                createButton("UPDATE");

        JButton deleteButton =
                createButton("DELETE");

        JButton clearButton =
                createButton("CLEAR");

        JButton refreshButton =
                createButton("REFRESH");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        JPanel topContainer =
                new JPanel(new BorderLayout());

        topContainer.setBackground(CREAM);

        topContainer.add(
                formPanel,
                BorderLayout.CENTER
        );

        topContainer.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                topContainer,
                BorderLayout.NORTH
        );

        // Creates the customer table
        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Customer ID",
                                "Customer Name",
                                "Phone",
                                "Email"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        customerTable =
                new JTable(tableModel);

        customerTable.setRowHeight(28);

        customerTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        customerTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );

        customerTable.getTableHeader()
                .setBackground(MAROON);

        customerTable.getTableHeader()
                .setForeground(Color.WHITE);

        JScrollPane scrollPane =
                new JScrollPane(customerTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        30,
                        10,
                        30
                )
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // Connects buttons with database operations
        addButton.addActionListener(
                e -> addCustomer()
        );

        updateButton.addActionListener(
                e -> updateCustomer()
        );

        deleteButton.addActionListener(
                e -> deleteCustomer()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        refreshButton.addActionListener(
                e -> loadCustomers()
        );

        // Loads selected table data into the form
        customerTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row =
                                customerTable
                                        .getSelectedRow();

                        if (row >= 0) {

                            customerIdField.setText(
                                    tableModel
                                            .getValueAt(
                                                    row,
                                                    0
                                            )
                                            .toString()
                            );

                            customerNameField.setText(
                                    tableModel
                                            .getValueAt(
                                                    row,
                                                    1
                                            )
                                            .toString()
                            );

                            phoneField.setText(
                                    tableModel
                                            .getValueAt(
                                                    row,
                                                    2
                                            )
                                            .toString()
                            );

                            Object email =
                                    tableModel
                                            .getValueAt(
                                                    row,
                                                    3
                                            );

                            emailField.setText(
                                    email == null
                                            ? ""
                                            : email.toString()
                            );

                            loadLoginCredentials(
                                    Integer.parseInt(
                                            customerIdField
                                                    .getText()
                                    )
                            );
                        }
                    }
                });

        add(mainPanel);
    }

    // Inserts customer details and creates the related login record
    private void addCustomer() {

        String name =
                customerNameField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                ).trim();

        if (name.isEmpty()
                || phone.isEmpty()
                || username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer Name, Phone, Username and Password are required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String customerSql =
                "INSERT INTO CUSTOMER " +
                "(Customer_Name, Phone, Email) " +
                "VALUES (?, ?, ?)";

        String loginSql =
                "INSERT INTO LOGIN_USER " +
                "(Username, Password, Role, Customer_ID) " +
                "VALUES (?, ?, 'CUSTOMER', ?)";

        Connection connection = null;

        try {

            // Opens a database connection
            connection =
                    DBConnection.getConnection();

            // Starts a transaction
            connection.setAutoCommit(false);

            int generatedCustomerId;

            // Inserts the customer record
            try (
                    PreparedStatement customerStatement =
                            connection.prepareStatement(
                                    customerSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                customerStatement.setString(
                        1,
                        name
                );

                customerStatement.setString(
                        2,
                        phone
                );

                if (email.isEmpty()) {

                    customerStatement.setNull(
                            3,
                            Types.VARCHAR
                    );

                } else {

                    customerStatement.setString(
                            3,
                            email
                    );
                }

                customerStatement.executeUpdate();

                // Gets the generated customer ID
                try (
                        ResultSet keys =
                                customerStatement
                                        .getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        throw new SQLException(
                                "Unable to get generated Customer ID."
                        );
                    }

                    generatedCustomerId =
                            keys.getInt(1);
                }
            }

            // Inserts the customer login record
            try (
                    PreparedStatement loginStatement =
                            connection.prepareStatement(
                                    loginSql
                            )
            ) {

                loginStatement.setString(
                        1,
                        username
                );

                loginStatement.setString(
                        2,
                        password
                );

                loginStatement.setInt(
                        3,
                        generatedCustomerId
                );

                loginStatement.executeUpdate();
            }

            // Saves both database operations
            connection.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();
            loadCustomers();

        } catch (Exception e) {

            // Cancels the transaction if an error occurs
            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add customer.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();

        } finally {

            // Closes the database connection
            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeError) {
                    closeError.printStackTrace();
                }
            }
        }
    }

    // Updates customer and login information
    private void updateCustomer() {

        String id =
                customerIdField
                        .getText()
                        .trim();

        String name =
                customerNameField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                ).trim();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a customer from the table first.",
                    "Update Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (name.isEmpty()
                || phone.isEmpty()
                || username.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer Name, Phone and Username are required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int customerId =
                Integer.parseInt(id);

        Connection connection = null;

        try {

            // Opens a database connection
            connection =
                    DBConnection.getConnection();

            // Starts a transaction
            connection.setAutoCommit(false);

            String customerSql =
                    "UPDATE CUSTOMER SET " +
                    "Customer_Name = ?, " +
                    "Phone = ?, " +
                    "Email = ? " +
                    "WHERE Customer_ID = ?";

            // Updates customer details
            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    customerSql
                            )
            ) {

                statement.setString(
                        1,
                        name
                );

                statement.setString(
                        2,
                        phone
                );

                if (email.isEmpty()) {

                    statement.setNull(
                            3,
                            Types.VARCHAR
                    );

                } else {

                    statement.setString(
                            3,
                            email
                    );
                }

                statement.setInt(
                        4,
                        customerId
                );

                statement.executeUpdate();
            }

            // Updates username and password
            if (password.isEmpty()) {

                String sql =
                        "UPDATE LOGIN_USER SET " +
                        "Username = ? " +
                        "WHERE Customer_ID = ? " +
                        "AND Role = 'CUSTOMER'";

                try (
                        PreparedStatement statement =
                                connection.prepareStatement(sql)
                ) {

                    statement.setString(
                            1,
                            username
                    );

                    statement.setInt(
                            2,
                            customerId
                    );

                    statement.executeUpdate();
                }

            } else {

                String sql =
                        "UPDATE LOGIN_USER SET " +
                        "Username = ?, Password = ? " +
                        "WHERE Customer_ID = ? " +
                        "AND Role = 'CUSTOMER'";

                try (
                        PreparedStatement statement =
                                connection.prepareStatement(sql)
                ) {

                    statement.setString(
                            1,
                            username
                    );

                    statement.setString(
                            2,
                            password
                    );

                    statement.setInt(
                            3,
                            customerId
                    );

                    statement.executeUpdate();
                }
            }

            connection.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();
            loadCustomers();

        } catch (Exception e) {

            // Cancels the transaction if an error occurs
            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update customer.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();

        } finally {

            // Closes the database connection
            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeError) {
                    closeError.printStackTrace();
                }
            }
        }
    }

    // Deletes the selected customer
    private void deleteCustomer() {

        String id =
                customerIdField
                        .getText()
                        .trim();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a customer from the table first.",
                    "Delete Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this customer?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        int customerId =
                Integer.parseInt(id);

        Connection connection = null;

        try {

            // Opens a database connection
            connection =
                    DBConnection.getConnection();

            // Starts a transaction
            connection.setAutoCommit(false);

            String deleteLoginSql =
                    "DELETE FROM LOGIN_USER " +
                    "WHERE Customer_ID = ? " +
                    "AND Role = 'CUSTOMER'";

            // Deletes the related login record
            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    deleteLoginSql
                            )
            ) {

                statement.setInt(
                        1,
                        customerId
                );

                statement.executeUpdate();
            }

            String deleteCustomerSql =
                    "DELETE FROM CUSTOMER " +
                    "WHERE Customer_ID = ?";

            // Deletes the customer record
            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    deleteCustomerSql
                            )
            ) {

                statement.setInt(
                        1,
                        customerId
                );

                statement.executeUpdate();
            }

            connection.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();
            loadCustomers();

        } catch (Exception e) {

            // Cancels the transaction if an error occurs
            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete customer.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();

        } finally {

            // Closes the database connection
            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeError) {
                    closeError.printStackTrace();
                }
            }
        }
    }

    // Retrieves customer records from MySQL
    private void loadCustomers() {

        if (tableModel == null) {
            return;
        }

        tableModel.setRowCount(0);

        String sql =
                "SELECT Customer_ID, " +
                "Customer_Name, Phone, Email " +
                "FROM CUSTOMER " +
                "ORDER BY Customer_ID";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            while (result.next()) {

                tableModel.addRow(
                        new Object[]{
                                result.getInt(
                                        "Customer_ID"
                                ),

                                result.getString(
                                        "Customer_Name"
                                ),

                                result.getString(
                                        "Phone"
                                ),

                                result.getString(
                                        "Email"
                                )
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load customers.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // Retrieves the username for the selected customer
    private void loadLoginCredentials(
            int customerId
    ) {

        String sql =
                "SELECT Username " +
                "FROM LOGIN_USER " +
                "WHERE Customer_ID = ? " +
                "AND Role = 'CUSTOMER'";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    customerId
            );

            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {

                    usernameField.setText(
                            result.getString(
                                    "Username"
                            )
                    );

                } else {

                    usernameField.setText("");
                }

                passwordField.setText("");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load customer information.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Clears the customer form
    private void clearFields() {

        customerIdField.setText("");
        customerNameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        usernameField.setText("");
        passwordField.setText("");

        if (customerTable != null) {
            customerTable.clearSelection();
        }

        customerNameField.requestFocus();
    }

    // Creates labels for the input form
    private void addFormLabel(
            JPanel panel,
            String text,
            GridBagConstraints gbc,
            int x,
            int y
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(TEXT);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;

        panel.add(
                label,
                gbc
        );
    }

    // Creates styled action buttons
    private JButton createButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(MAROON);
        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setPreferredSize(
                new Dimension(
                        110,
                        38
                )
        );

        return button;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            CustomerPanel panel =
                    new CustomerPanel();

            panel.setVisible(true);
        });
    }
}