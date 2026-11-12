import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

/**
 * Namma Ooru Studio
 * Customer Registration Panel
 */
public class RegisterPanel extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color MAROON = new Color(90, 24, 53);
    private static final Color GOLD = new Color(198, 161, 91);
    private static final Color CREAM = new Color(251, 248, 244);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(55, 45, 50);

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public RegisterPanel() {

        setTitle(
                "Namma Ooru Studio | Customer Registration"
        );

        setSize(1000, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(false);

        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(CREAM);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(MAROON);

        headerPanel.setBorder(
                new EmptyBorder(
                        18,
                        30,
                        18,
                        30
                )
        );

        JLabel brandLabel =
                new JLabel(
                        "NAMMA OORU STUDIO"
                );

        brandLabel.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        24
                )
        );

        brandLabel.setForeground(WHITE);

        JLabel sectionLabel =
                new JLabel(
                        "CUSTOMER REGISTRATION"
                );

        sectionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        sectionLabel.setForeground(GOLD);

        headerPanel.add(
                brandLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                sectionLabel,
                BorderLayout.EAST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER PANEL
        // =====================================================

        JPanel centerPanel =
                new JPanel(new GridBagLayout());

        centerPanel.setBackground(CREAM);

        // =====================================================
        // REGISTRATION CARD
        // =====================================================

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD,
                                1
                        ),
                        new EmptyBorder(
                                30,
                                50,
                                30,
                                50
                        )
                )
        );

        card.setPreferredSize(
                new Dimension(
                        650,
                        560
                )
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel titleLabel =
                new JLabel(
                        "Create Your Account",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        30
                )
        );

        titleLabel.setForeground(MAROON);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        titleLabel.setMaximumSize(
                new Dimension(
                        550,
                        45
                )
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(5)
        );

        // =====================================================
        // SUBTITLE
        // =====================================================

        JLabel subtitleLabel =
                new JLabel(
                        "Join Namma Ooru Studio as a customer",
                        SwingConstants.CENTER
                );

        subtitleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        subtitleLabel.setForeground(TEXT);

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitleLabel.setMaximumSize(
                new Dimension(
                        550,
                        25
                )
        );

        card.add(subtitleLabel);

        card.add(
                Box.createVerticalStrut(25)
        );

        // =====================================================
        // FORM AREA
        // =====================================================

        JPanel formPanel =
                new JPanel();

        formPanel.setLayout(
                new BoxLayout(
                        formPanel,
                        BoxLayout.Y_AXIS
                )
        );

        formPanel.setBackground(WHITE);

        formPanel.setPreferredSize(
                new Dimension(
                        400,
                        400
                )
        );

        formPanel.setMaximumSize(
                new Dimension(
                        400,
                        400
                )
        );

        formPanel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =====================================================
        // FULL NAME
        // =====================================================

        formPanel.add(
                createFormLabel(
                        "FULL NAME"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(6)
        );

        nameField =
                createTextField();

        formPanel.add(nameField);

        formPanel.add(
                Box.createVerticalStrut(13)
        );

        // =====================================================
        // PHONE
        // =====================================================

        formPanel.add(
                createFormLabel(
                        "PHONE NUMBER"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(6)
        );

        phoneField =
                createTextField();

        formPanel.add(phoneField);

        formPanel.add(
                Box.createVerticalStrut(13)
        );

        // =====================================================
        // EMAIL
        // =====================================================

        formPanel.add(
                createFormLabel(
                        "EMAIL"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(6)
        );

        emailField =
                createTextField();

        formPanel.add(emailField);

        formPanel.add(
                Box.createVerticalStrut(13)
        );

        // =====================================================
        // PASSWORD
        // =====================================================

        formPanel.add(
                createFormLabel(
                        "PASSWORD"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(6)
        );

        passwordField =
                new JPasswordField();

        stylePasswordField(
                passwordField
        );

        formPanel.add(passwordField);

        formPanel.add(
                Box.createVerticalStrut(13)
        );

        // =====================================================
        // CONFIRM PASSWORD
        // =====================================================

        formPanel.add(
                createFormLabel(
                        "CONFIRM PASSWORD"
                )
        );

        formPanel.add(
                Box.createVerticalStrut(6)
        );

        confirmPasswordField =
                new JPasswordField();

        stylePasswordField(
                confirmPasswordField
        );

        formPanel.add(confirmPasswordField);

        card.add(formPanel);

        card.add(
                Box.createVerticalStrut(22)
        );

        // =====================================================
        // CREATE ACCOUNT BUTTON
        // =====================================================

        JButton registerButton =
                new JButton(
                        "CREATE ACCOUNT"
                );

        registerButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        registerButton.setForeground(WHITE);

        registerButton.setBackground(MAROON);

        registerButton.setFocusPainted(false);

        registerButton.setPreferredSize(
                new Dimension(
                        400,
                        45
                )
        );

        registerButton.setMaximumSize(
                new Dimension(
                        400,
                        45
                )
        );

        registerButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        registerButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        registerButton.addActionListener(
                e -> registerCustomer()
        );

        card.add(registerButton);

        card.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JButton backButton =
                new JButton(
                        "← Back to Login"
                );

        backButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        backButton.setForeground(MAROON);

        backButton.setBackground(WHITE);

        backButton.setBorderPainted(false);

        backButton.setFocusPainted(false);

        backButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.addActionListener(
                e -> dispose()
        );

        card.add(backButton);

        // =====================================================
        // ADD CARD TO CENTER
        // =====================================================

        centerPanel.add(card);

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);

        getRootPane().setDefaultButton(
                registerButton
        );
    }

    // =========================================================
    // FORM LABEL
    // =========================================================

    private JLabel createFormLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        label.setForeground(MAROON);

        label.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        label.setPreferredSize(
                new Dimension(
                        400,
                        20
                )
        );

        label.setMaximumSize(
                new Dimension(
                        400,
                        20
                )
        );

        return label;
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        field.setPreferredSize(
                new Dimension(
                        400,
                        38
                )
        );

        field.setMaximumSize(
                new Dimension(
                        400,
                        38
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD
                        ),
                        new EmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );

        return field;
    }

    // =========================================================
    // PASSWORD FIELD
    // =========================================================

    private void stylePasswordField(
            JPasswordField field
    ) {

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        field.setPreferredSize(
                new Dimension(
                        400,
                        38
                )
        );

        field.setMaximumSize(
                new Dimension(
                        400,
                        38
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD
                        ),
                        new EmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );
    }

    // =========================================================
    // REGISTER CUSTOMER
    // =========================================================

    private void registerCustomer() {

        String name =
                nameField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        // =====================================================
        // VALIDATION
        // =====================================================

        if (name.isEmpty()) {

            showWarning(
                    "Please enter your full name."
            );

            return;
        }

        if (phone.isEmpty()) {

            showWarning(
                    "Please enter your phone number."
            );

            return;
        }

        if (!phone.matches("\\d{10}")) {

            showWarning(
                    "Phone number must contain exactly 10 digits."
            );

            return;
        }

        if (email.isEmpty()) {

            showWarning(
                    "Please enter your email."
            );

            return;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )) {

            showWarning(
                    "Please enter a valid email address."
            );

            return;
        }

        if (password.length() < 6) {

            showWarning(
                    "Password must contain at least 6 characters."
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            showWarning(
                    "Passwords do not match."
            );

            return;
        }

        // =====================================================
        // DATABASE TRANSACTION
        // =====================================================

        Connection connection = null;

        try {

            connection =
                    DBConnection.getConnection();

            connection.setAutoCommit(false);

            // =================================================
            // INSERT CUSTOMER
            // =================================================

            String customerSql =
                    "INSERT INTO CUSTOMER "
                            + "(Customer_Name, Phone, Email) "
                            + "VALUES (?, ?, ?)";

            int customerId;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    customerSql,
                                    Statement.RETURN_GENERATED_KEYS
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

                statement.setString(
                        3,
                        email
                );

                statement.executeUpdate();

                try (
                        ResultSet keys =
                                statement.getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        throw new SQLException(
                                "Customer ID was not generated."
                        );
                    }

                    customerId =
                            keys.getInt(1);
                }
            }

            // =================================================
            // INSERT LOGIN ACCOUNT
            // =================================================

            String loginSql =
                    "INSERT INTO LOGIN_USER "
                            + "(Username, Password, Role, Customer_ID) "
                            + "VALUES (?, ?, ?, ?)";

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    loginSql
                            )
            ) {

                statement.setString(
                        1,
                        name
                );

                statement.setString(
                        2,
                        password
                );

                statement.setString(
                        3,
                        "CUSTOMER"
                );

                statement.setInt(
                        4,
                        customerId
                );

                statement.executeUpdate();
            }

            // =================================================
            // COMMIT
            // =================================================

            connection.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer account created successfully!\n\n"
                            + "Username: " + name
                            + "\n\n"
                            + "You can now login as CUSTOMER.",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (SQLException ex) {

            // =================================================
            // ROLLBACK
            // =================================================

            if (connection != null) {

                try {

                    connection.rollback();

                } catch (SQLException rollbackEx) {

                    rollbackEx.printStackTrace();
                }
            }

            String message =
                    ex.getMessage();

            if (message != null &&
                    message.toLowerCase()
                            .contains("duplicate")) {

                JOptionPane.showMessageDialog(
                        this,
                        "This customer information or username "
                                + "already exists.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed:\n"
                                + message,
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } finally {

            if (connection != null) {

                try {

                    connection.setAutoCommit(true);

                    connection.close();

                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }
        }
    }

    // =========================================================
    // WARNING
    // =========================================================

    private void showWarning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Registration",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            RegisterPanel panel =
                    new RegisterPanel();

            panel.setVisible(true);
        });
    }
}