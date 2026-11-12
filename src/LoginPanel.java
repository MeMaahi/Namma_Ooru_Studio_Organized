import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPanel extends JFrame {

    // =========================
    // COLORS
    // =========================

    private static final Color MAROON = new Color(91, 25, 53);
    private static final Color DARK_MAROON = new Color(60, 15, 34);
    private static final Color GOLD = new Color(198, 161, 91);
    private static final Color LIGHT_GOLD = new Color(232, 215, 181);
    private static final Color CREAM = new Color(249, 246, 241);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(55, 45, 50);
    private static final Color MUTED = new Color(125, 115, 120);

    // =========================
    // COMPONENTS
    // =========================

    private JComboBox<String> roleCombo;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton showPasswordButton;

    // =========================
    // CONSTANTS
    // =========================

    private static final int FORM_WIDTH = 435;
    private static final int FIELD_HEIGHT = 46;

    // =========================
    // CONSTRUCTOR
    // =========================

    public LoginPanel() {

        setTitle("Namma Ooru Studio | Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1350, 760);
        setMinimumSize(new Dimension(1100, 650));
        setLocationRelativeTo(null);

        createInterface();
    }

    // ============================================================
    // MAIN INTERFACE
    // ============================================================

    private void createInterface() {

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(CREAM);

        // LEFT SIDE
        mainPanel.add(createBrandPanel());

        // RIGHT SIDE
        mainPanel.add(createLoginArea());

        add(mainPanel);
    }

    // ============================================================
    // LEFT BRAND PANEL
    // ============================================================

    private JPanel createBrandPanel() {

        JPanel panel = new JPanel();
        panel.setBackground(MAROON);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Logo
        JPanel logo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                int w = getWidth();
                int h = getHeight();

                g2.setColor(GOLD);
                g2.setStroke(new BasicStroke(4));

                g2.drawOval(4, 4, w - 8, h - 8);

                g2.setColor(GOLD);
                g2.fillOval(14, 14, w - 28, h - 28);

                g2.dispose();
            }
        };

        logo.setOpaque(false);
        logo.setPreferredSize(new Dimension(65, 65));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 35, 0);
        panel.add(logo, gbc);

        // Studio name
        JLabel studioName = new JLabel(
                "NAMMA OORU STUDIO"
        );

        studioName.setFont(
                new Font("Georgia", Font.BOLD, 34)
        );

        studioName.setForeground(WHITE);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 12, 0);
        panel.add(studioName, gbc);

        // Subtitle
        JLabel subtitle = new JLabel(
                "WEDDING PHOTOGRAPHY"
        );

        subtitle.setFont(
                new Font("SansSerif", Font.BOLD, 16)
        );

        subtitle.setForeground(GOLD);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        panel.add(subtitle, gbc);

        // Gold line
        JPanel line = new JPanel();
        line.setBackground(GOLD);
        line.setPreferredSize(new Dimension(110, 3));

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 70, 0);
        panel.add(line, gbc);

        // Quote
        JLabel quote = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "<i>Capturing your<br>forever.</i>"
                        + "</div></html>"
        );

        quote.setFont(
                new Font("Georgia", Font.PLAIN, 35)
        );

        quote.setForeground(WHITE);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 28, 0);
        panel.add(quote, gbc);

        // Description
        JLabel description = new JLabel(
        "<html><div style='text-align:center;'>"
                + "We don't just capture moments,<br>"
                + "we preserve the emotions<br>"
                + "that make them unforgettable."
                + "</div></html>"
);

        description.setFont(
                new Font("SansSerif", Font.PLAIN, 16)
        );

        description.setForeground(WHITE);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 70, 0);
        panel.add(description, gbc);

        // Bottom quote
        JLabel bottomQuote = new JLabel(
                "Your moments. Your memories. Your story."
        );

        bottomQuote.setFont(
                new Font("Georgia", Font.ITALIC, 15)
        );

        bottomQuote.setForeground(GOLD);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(bottomQuote, gbc);

        // Bottom gold block
        JPanel goldBlock = new JPanel();
        goldBlock.setBackground(GOLD);
        goldBlock.setPreferredSize(new Dimension(125, 13));

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(goldBlock, gbc);

        return panel;
    }

    // ============================================================
    // RIGHT LOGIN AREA
    // ============================================================

    private JPanel createLoginArea() {

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(CREAM);

        // Main login card
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                LIGHT_GOLD,
                                1
                        ),
                        new EmptyBorder(
                                42,
                                64,
                                35,
                                64
                        )
                )
        );

        card.setPreferredSize(
                new Dimension(570, 660)
        );

        // =========================
        // CARD CONTENT
        // =========================

        JPanel content = new JPanel();
        content.setOpaque(false);

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        // TITLE
        JLabel title = new JLabel(
                "Welcome Back"
        );

        title.setFont(
                new Font("Georgia", Font.BOLD, 38)
        );

        title.setForeground(MAROON);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(title);

        content.add(Box.createVerticalStrut(8));

        // SUBTITLE
        JLabel subTitle = new JLabel(
                "Sign in to your studio account"
        );

        subTitle.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        subTitle.setForeground(TEXT);
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(subTitle);

        content.add(Box.createVerticalStrut(38));

        // ROLE LABEL
        content.add(
                createLabel(
                        "LOGIN AS"
                )
        );

        content.add(Box.createVerticalStrut(8));

        // ROLE
        roleCombo = new JComboBox<>(
                new String[]{
                        "ADMIN",
                        "CUSTOMER",
                        "PHOTOGRAPHER"
                }
        );

        styleComboBox(roleCombo);

        content.add(
                centerComponent(
                        roleCombo
                )
        );

        content.add(Box.createVerticalStrut(24));

        // USERNAME LABEL
        content.add(
                createLabel(
                        "USERNAME"
                )
        );

        content.add(Box.createVerticalStrut(8));

        // USERNAME
        usernameField = new JTextField();

        styleTextField(usernameField);

        content.add(
                centerComponent(
                        usernameField
                )
        );

        content.add(Box.createVerticalStrut(24));

        // PASSWORD LABEL
        content.add(
                createLabel(
                        "PASSWORD"
                )
        );

        content.add(Box.createVerticalStrut(8));

        // PASSWORD ROW
        JPanel passwordRow = new JPanel(
                new BorderLayout(8, 0)
        );

        passwordRow.setOpaque(false);
        passwordRow.setPreferredSize(
                new Dimension(
                        FORM_WIDTH,
                        FIELD_HEIGHT
                )
        );

        passwordRow.setMaximumSize(
                new Dimension(
                        FORM_WIDTH,
                        FIELD_HEIGHT
                )
        );

        passwordField = new JPasswordField();

        styleTextField(passwordField);

        passwordRow.add(
                passwordField,
                BorderLayout.CENTER
        );

        showPasswordButton = new JButton("SHOW");

        showPasswordButton.setPreferredSize(
                new Dimension(82, FIELD_HEIGHT)
        );

        showPasswordButton.setFocusPainted(false);
        showPasswordButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        showPasswordButton.setBackground(
                WHITE
        );

        showPasswordButton.setForeground(
                MAROON
        );

        showPasswordButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        showPasswordButton.setBorder(
                BorderFactory.createLineBorder(
                        GOLD,
                        1
                )
        );

        passwordRow.add(
                showPasswordButton,
                BorderLayout.EAST
        );

        content.add(
                passwordRow
        );

        // SHOW PASSWORD
        showPasswordButton.addActionListener(
                e -> togglePassword()
        );

        content.add(Box.createVerticalStrut(30));

        // LOGIN BUTTON
        JButton loginButton = new JButton(
                "LOGIN"
        );

        stylePrimaryButton(loginButton);

        loginButton.setPreferredSize(
                new Dimension(
                        FORM_WIDTH,
                        52
                )
        );

        loginButton.setMaximumSize(
                new Dimension(
                        FORM_WIDTH,
                        52
                )
        );

        content.add(
                centerComponent(
                        loginButton
                )
        );

        loginButton.addActionListener(
                e -> login()
        );

        // ENTER KEY
        passwordField.addActionListener(
                e -> login()
        );

        usernameField.addActionListener(
                e -> passwordField.requestFocus()
        );

        content.add(Box.createVerticalStrut(20));

        // NEW CUSTOMER
        JLabel newCustomer = new JLabel(
                "New customer?"
        );

        newCustomer.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        newCustomer.setForeground(
                MUTED
        );

        newCustomer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(newCustomer);

        content.add(Box.createVerticalStrut(6));

        JButton registerButton = new JButton(
                "CREATE CUSTOMER ACCOUNT"
        );

        registerButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        registerButton.setForeground(
                MAROON
        );

        registerButton.setBackground(
                WHITE
        );

        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);

        registerButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        registerButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(registerButton);

        registerButton.addActionListener(
                e -> openRegister()
        );

        content.add(Box.createVerticalStrut(8));

        // GOLD LINE
        JPanel goldLine = new JPanel();
        goldLine.setBackground(GOLD);

        goldLine.setPreferredSize(
                new Dimension(
                        FORM_WIDTH,
                        1
                )
        );

        goldLine.setMaximumSize(
                new Dimension(
                        FORM_WIDTH,
                        1
                )
        );

        content.add(
                goldLine
        );

        content.add(Box.createVerticalStrut(18));

        JLabel footer = new JLabel(
                "Namma Ooru Studio  •  Since Forever"
        );

        footer.setFont(
                new Font(
                        "Georgia",
                        Font.ITALIC,
                        12
                )
        );

        footer.setForeground(
                new Color(150, 120, 125)
        );

        footer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(
                footer
        );

        card.add(
                content,
                BorderLayout.CENTER
        );

        background.add(card);

        return background;
    }

    // ============================================================
    // LABEL
    // ============================================================

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(MAROON);

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return label;
    }

    // ============================================================
    // CENTER COMPONENT
    // ============================================================

    private JComponent centerComponent(
            JComponent component
    ) {

        JPanel wrapper = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        0,
                        0
                )
        );

        wrapper.setOpaque(false);

        wrapper.setPreferredSize(
                new Dimension(
                        FORM_WIDTH,
                        component.getPreferredSize().height
                )
        );

        wrapper.setMaximumSize(
                new Dimension(
                        FORM_WIDTH,
                        component.getPreferredSize().height
                )
        );

        wrapper.add(component);

        return wrapper;
    }

    // ============================================================
    // TEXT FIELD STYLE
    // ============================================================

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        field.setForeground(TEXT);
        field.setBackground(WHITE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD,
                                1
                        ),
                        new EmptyBorder(
                                0,
                                12,
                                0,
                                12
                        )
                )
        );

        field.setPreferredSize(
                new Dimension(
                        FORM_WIDTH,
                        FIELD_HEIGHT
                )
        );

        field.setMaximumSize(
                new Dimension(
                        FORM_WIDTH,
                        FIELD_HEIGHT
                )
        );
    }

    // ============================================================
    // COMBO BOX STYLE
    // ============================================================

    private void styleComboBox(
            JComboBox<String> combo
    ) {

        combo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        combo.setForeground(TEXT);
        combo.setBackground(WHITE);

        combo.setPreferredSize(
                new Dimension(
                        FORM_WIDTH,
                        FIELD_HEIGHT
                )
        );

        combo.setMaximumSize(
                new Dimension(
                        FORM_WIDTH,
                        FIELD_HEIGHT
                )
        );
    }

    // ============================================================
    // LOGIN BUTTON STYLE
    // ============================================================

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        button.setForeground(WHITE);
        button.setBackground(MAROON);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setOpaque(true);

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                DARK_MAROON
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                MAROON
                        );
                    }
                }
        );
    }

    // ============================================================
    // PASSWORD SHOW / HIDE
    // ============================================================

    private void togglePassword() {

        if (passwordField.getEchoChar() == 0) {

            passwordField.setEchoChar('•');
            showPasswordButton.setText("SHOW");

        } else {

            passwordField.setEchoChar((char) 0);
            showPasswordButton.setText("HIDE");
        }
    }

    // ============================================================
    // LOGIN
    // ============================================================

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String role =
                (String) roleCombo.getSelectedItem();

        if (username.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            usernameField.requestFocus();
            return;
        }

        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            passwordField.requestFocus();
            return;
        }

        String sql =
                "SELECT User_ID, Role, Customer_ID, Photographer_ID " +
                "FROM LOGIN_USER " +
                "WHERE Username = ? " +
                "AND Password = ? " +
                "AND Role = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {

                    int userId =
                            result.getInt("User_ID");

                    String actualRole =
                            result.getString("Role");

                    int customerId =
                            result.getInt("Customer_ID");

                    Integer customerIdValue =
                            result.wasNull()
                                    ? null
                                    : customerId;

                    int photographerId =
                            result.getInt(
                                    "Photographer_ID"
                            );

                    Integer photographerIdValue =
                            result.wasNull()
                                    ? null
                                    : photographerId;

                    JOptionPane.showMessageDialog(
                            this,
                            "Login successful!",
                            "Welcome",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    dispose();

                    new DashboardPanel(
                            username,
                            actualRole,
                            userId,
                            customerIdValue,
                            photographerIdValue
                    ).setVisible(true);

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid username, password or role.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );

                    passwordField.setText("");
                    passwordField.requestFocus();
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // ============================================================
    // REGISTER
    // ============================================================

    private void openRegister() {

        try {

            Class<?> registerClass =
                    Class.forName("RegisterPanel");

            JFrame registerFrame =
                    (JFrame) registerClass
                            .getDeclaredConstructor()
                            .newInstance();

            registerFrame.setVisible(true);

            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Register page is not available yet.",
                    "Registration",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ============================================================
    // MAIN
    // ============================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    

                    new LoginPanel().setVisible(true);
                }
        );
    }
}