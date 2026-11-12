import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;

public class PackagePanel extends JFrame {

    // =========================
    // COLORS
    // =========================
    private final Color MAROON = new Color(90, 24, 53);
    private final Color GOLD = new Color(198, 161, 91);
    private final Color CREAM = new Color(251, 248, 244);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(55, 45, 50);

    // =========================
    // COMPONENTS
    // =========================
    private JTextField packageIdField;
    private JTextField packageNameField;
    private JTextField priceField;

    private JTable packageTable;
    private DefaultTableModel tableModel;

    // =========================
    // CONSTRUCTOR
    // =========================
    public PackagePanel() {

        setTitle("Namma Ooru Studio - Package Management");

        setSize(950, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        mainPanel.setBackground(CREAM);

        mainPanel.setBorder(
                new EmptyBorder(20, 25, 20, 25)
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel = new JPanel();

        headerPanel.setBackground(MAROON);

        headerPanel.setPreferredSize(
                new Dimension(0, 60)
        );

        JLabel titleLabel =
                new JLabel("PACKAGE MANAGEMENT");

        titleLabel.setForeground(WHITE);

        titleLabel.setFont(
                new Font("Serif", Font.BOLD, 26)
        );

        headerPanel.add(titleLabel);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER PANEL
        // =====================================================

        JPanel centerPanel =
                new JPanel(new BorderLayout(15, 15));

        centerPanel.setBackground(CREAM);

        // =====================================================
        // FORM PANEL
        // =====================================================

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBackground(WHITE);

        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD,
                                1
                        ),
                        new EmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 10, 8, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // Package ID
        JLabel idLabel =
                createLabel("Package ID");

        packageIdField =
                new JTextField();

        packageIdField.setEditable(false);

        packageIdField.setBackground(
                new Color(235, 235, 235)
        );

        addFormRow(
                formPanel,
                gbc,
                idLabel,
                packageIdField,
                0
        );

        // Package Name
        JLabel nameLabel =
                createLabel("Package Name");

        packageNameField =
                new JTextField();

        addFormRow(
                formPanel,
                gbc,
                nameLabel,
                packageNameField,
                1
        );

        // Price
        JLabel priceLabel =
                createLabel("Price");

        priceField =
                new JTextField();

        addFormRow(
                formPanel,
                gbc,
                priceLabel,
                priceField,
                2
        );

        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                10
                        )
                );

        buttonPanel.setBackground(WHITE);

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

        // Put form and buttons together
        JPanel formAndButtons =
                new JPanel(new BorderLayout());

        formAndButtons.setBackground(WHITE);

        formAndButtons.add(
                formPanel,
                BorderLayout.CENTER
        );

        formAndButtons.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        centerPanel.add(
                formAndButtons,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "Package ID",
                "Package Name",
                "Price"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
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

        packageTable =
                new JTable(tableModel);

        packageTable.setRowHeight(30);

        packageTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        packageTable.setSelectionBackground(GOLD);

        packageTable.setSelectionForeground(TEXT);

        packageTable.setGridColor(
                new Color(220, 220, 220)
        );

        packageTable.getTableHeader()
                .setBackground(MAROON);

        packageTable.getTableHeader()
                .setForeground(WHITE);

        packageTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                14
                        )
                );

        packageTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(0, 35)
                );

        JScrollPane scrollPane =
                new JScrollPane(packageTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        GOLD,
                        1
                )
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        addButton.addActionListener(
                e -> addPackage()
        );

        updateButton.addActionListener(
                e -> updatePackage()
        );

        deleteButton.addActionListener(
                e -> deletePackage()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        refreshButton.addActionListener(
                e -> loadPackages()
        );

        // =====================================================
        // TABLE ROW SELECTION
        // =====================================================

        packageTable
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row =
                                packageTable
                                        .getSelectedRow();

                        if (row != -1) {

                            packageIdField.setText(
                                    tableModel
                                            .getValueAt(
                                                    row,
                                                    0
                                            )
                                            .toString()
                            );

                            packageNameField.setText(
                                    tableModel
                                            .getValueAt(
                                                    row,
                                                    1
                                            )
                                            .toString()
                            );

                            priceField.setText(
                                    tableModel
                                            .getValueAt(
                                                    row,
                                                    2
                                            )
                                            .toString()
                            );
                        }
                    }
                });

        // Set content
        setContentPane(mainPanel);

        // Load database records
        loadPackages();
    }

    // =====================================================
    // LABEL
    // =====================================================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    // =====================================================
    // FORM ROW
    // =====================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            JLabel label,
            JTextField field,
            int row
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        field.setPreferredSize(
                new Dimension(350, 35)
        );

        panel.add(field, gbc);
    }

    // =====================================================
    // BUTTON
    // =====================================================

    private JButton createButton(String text) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        button.setBackground(MAROON);

        button.setForeground(WHITE);

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(110, 38)
        );

        return button;
    }

    // =====================================================
    // ADD PACKAGE
    // =====================================================

    private void addPackage() {

        String name =
                packageNameField
                        .getText()
                        .trim();

        String priceText =
                priceField
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Package Name."
            );

            return;
        }

        if (priceText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Price."
            );

            return;
        }

        BigDecimal price;

        try {

            price =
                    new BigDecimal(priceText);

            if (price.compareTo(
                    BigDecimal.ZERO
            ) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price must be greater than 0."
                );

                return;
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid price."
            );

            return;
        }

        String sql =
                "INSERT INTO PACKAGE " +
                "(Package_Name, Price) " +
                "VALUES (?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    name
            );

            statement.setBigDecimal(
                    2,
                    price
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Package added successfully!"
            );

            clearFields();

            loadPackages();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error adding package:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // UPDATE PACKAGE
    // =====================================================

    private void updatePackage() {

        if (packageIdField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a package first."
            );

            return;
        }

        String name =
                packageNameField
                        .getText()
                        .trim();

        String priceText =
                priceField
                        .getText()
                        .trim();

        if (name.isEmpty() ||
                priceText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        BigDecimal price;

        try {

            price =
                    new BigDecimal(priceText);

            if (price.compareTo(
                    BigDecimal.ZERO
            ) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price must be greater than 0."
                );

                return;
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid price."
            );

            return;
        }

        int id =
                Integer.parseInt(
                        packageIdField
                                .getText()
                                .trim()
                );

        String sql =
                "UPDATE PACKAGE " +
                "SET Package_Name = ?, Price = ? " +
                "WHERE Package_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);

            statement.setBigDecimal(2, price);

            statement.setInt(3, id);

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Package updated successfully!"
                );

                clearFields();

                loadPackages();

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating package:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // DELETE PACKAGE
    // =====================================================

    private void deletePackage() {

        if (packageIdField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a package first."
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this package?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice !=
                JOptionPane.YES_OPTION) {

            return;
        }

        int id =
                Integer.parseInt(
                        packageIdField
                                .getText()
                                .trim()
                );

        String sql =
                "DELETE FROM PACKAGE " +
                "WHERE Package_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Package deleted successfully!"
            );

            clearFields();

            loadPackages();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error deleting package:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // LOAD PACKAGES
    // =====================================================

    private void loadPackages() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT Package_ID, " +
                "Package_Name, Price " +
                "FROM PACKAGE " +
                "ORDER BY Package_ID";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                int id =
                        resultSet.getInt(
                                "Package_ID"
                        );

                String name =
                        resultSet.getString(
                                "Package_Name"
                        );

                BigDecimal price =
                        resultSet.getBigDecimal(
                                "Price"
                        );

                tableModel.addRow(
                        new Object[]{
                                id,
                                name,
                                price
                        }
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading packages:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // CLEAR
    // =====================================================

    private void clearFields() {

        packageIdField.setText("");

        packageNameField.setText("");

        priceField.setText("");

        packageTable.clearSelection();

        packageNameField.requestFocus();
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            PackagePanel panel =
                    new PackagePanel();

            panel.setVisible(true);
        });
    }
}