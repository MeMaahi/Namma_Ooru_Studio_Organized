import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * PhotographerPanel
 * ------------------------------------------------
 * This class manages photographers in Namma Ooru Studio.
 *
 * Operations:
 * 1. ADD       - Insert photographer into MySQL
 * 2. UPDATE    - Update photographer information
 * 3. DELETE    - Delete photographer
 * 4. REFRESH   - Load photographers from MySQL
 * 5. CLEAR     - Clear the input fields
 */
public class PhotographerPanel extends JFrame {

    // =====================================================
    // DESIGN COLORS
    // =====================================================

    private final Color MAROON = new Color(90, 24, 53);
    private final Color GOLD = new Color(198, 161, 91);
    private final Color CREAM = new Color(251, 248, 244);
    private final Color TEXT = new Color(55, 45, 50);

    // =====================================================
    // INPUT FIELDS
    // =====================================================

    private JTextField photographerIdField;
    private JTextField photographerNameField;
    private JTextField phoneField;

    // =====================================================
    // TABLE
    // =====================================================

    private JTable photographerTable;
    private DefaultTableModel tableModel;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public PhotographerPanel() {

        setTitle(
                "Namma Ooru Studio - Photographer Management"
        );

        setSize(850, 550);

        // Closing this window does not close the entire application
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        // Center the window
        setLocationRelativeTo(null);

        // Create the interface
        createUI();

        // Load existing photographers from MySQL
        loadPhotographers();
    }

    // =====================================================
    // CREATE USER INTERFACE
    // =====================================================

    private void createUI() {

        // Main panel
        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(CREAM);

        // =================================================
        // HEADER
        // =================================================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(MAROON);

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        // Main heading
        JLabel titleLabel =
                new JLabel("PHOTOGRAPHER MANAGEMENT");

        titleLabel.setForeground(Color.WHITE);

        titleLabel.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        25
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        // Studio name
        JLabel studioLabel =
                new JLabel("NAMMA OORU STUDIO");

        studioLabel.setForeground(GOLD);

        studioLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        headerPanel.add(
                studioLabel,
                BorderLayout.EAST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =================================================
        // CENTER CONTAINER
        // =================================================

        JPanel centerPanel =
                new JPanel(new BorderLayout());

        centerPanel.setBackground(CREAM);

        // =================================================
        // FORM PANEL
        // =================================================

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBackground(CREAM);

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        15,
                        30
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // -------------------------------------------------
        // Photographer ID
        // -------------------------------------------------

        addFormLabel(
                formPanel,
                "Photographer ID:",
                gbc,
                0,
                0
        );

        photographerIdField =
                new JTextField(15);

        // ID is generated automatically by MySQL
        photographerIdField.setEditable(false);

        gbc.gridx = 1;
        gbc.gridy = 0;

        formPanel.add(
                photographerIdField,
                gbc
        );

        // -------------------------------------------------
        // Photographer Name
        // -------------------------------------------------

        addFormLabel(
                formPanel,
                "Photographer Name:",
                gbc,
                0,
                1
        );

        photographerNameField =
                new JTextField(15);

        gbc.gridx = 1;
        gbc.gridy = 1;

        formPanel.add(
                photographerNameField,
                gbc
        );

        // -------------------------------------------------
        // Phone
        // -------------------------------------------------

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

        centerPanel.add(
                formPanel,
                BorderLayout.NORTH
        );

        // =================================================
        // TABLE
        // =================================================

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Photographer ID",
                                "Photographer Name",
                                "Phone"
                        },
                        0
                ) {

                    // Prevent direct editing inside JTable
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        photographerTable =
                new JTable(tableModel);

        photographerTable.setRowHeight(28);

        photographerTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        // Table header
        photographerTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );

        photographerTable
                .getTableHeader()
                .setBackground(MAROON);

        photographerTable
                .getTableHeader()
                .setForeground(Color.WHITE);

        JScrollPane scrollPane =
                new JScrollPane(
                        photographerTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        30,
                        10,
                        30
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

        // =================================================
        // BUTTON PANEL
        // =================================================

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

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =================================================
        // BUTTON ACTIONS
        // =================================================

        addButton.addActionListener(
                e -> addPhotographer()
        );

        updateButton.addActionListener(
                e -> updatePhotographer()
        );

        deleteButton.addActionListener(
                e -> deletePhotographer()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        refreshButton.addActionListener(
                e -> loadPhotographers()
        );

        // =================================================
        // TABLE ROW SELECTION
        // =================================================

        photographerTable
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    // Avoid reacting while selection is changing
                    if (!e.getValueIsAdjusting()) {

                        int row =
                                photographerTable
                                .getSelectedRow();

                        if (row >= 0) {

                            // Get Photographer ID
                            photographerIdField.setText(
                                    tableModel
                                    .getValueAt(
                                            row,
                                            0
                                    )
                                    .toString()
                            );

                            // Get Photographer Name
                            photographerNameField.setText(
                                    tableModel
                                    .getValueAt(
                                            row,
                                            1
                                    )
                                    .toString()
                            );

                            // Get Phone
                            phoneField.setText(
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

        // Add everything to JFrame
        add(mainPanel);
    }

    // =====================================================
    // ADD PHOTOGRAPHER
    // =====================================================

    private void addPhotographer() {

        // Read input values
        String name =
                photographerNameField
                .getText()
                .trim();

        String phone =
                phoneField
                .getText()
                .trim();

        // -----------------------------------------------
        // VALIDATION
        // -----------------------------------------------

        if (name.isEmpty() ||
                phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Photographer Name and Phone are required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // -----------------------------------------------
        // SQL INSERT
        // -----------------------------------------------

        String sql =
                "INSERT INTO PHOTOGRAPHER " +
                "(Photographer_Name, Phone) " +
                "VALUES (?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            // Set photographer name
            statement.setString(
                    1,
                    name
            );

            // Set phone number
            statement.setString(
                    2,
                    phone
            );

            // Execute INSERT
            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Photographer added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Clear fields
            clearFields();

            // Reload table
            loadPhotographers();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add photographer.\n\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // UPDATE PHOTOGRAPHER
    // =====================================================

    private void updatePhotographer() {

        String id =
                photographerIdField
                .getText()
                .trim();

        String name =
                photographerNameField
                .getText()
                .trim();

        String phone =
                phoneField
                .getText()
                .trim();

        // Check whether a photographer is selected
        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a photographer from the table first.",
                    "Update Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Check required fields
        if (name.isEmpty() ||
                phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Photographer Name and Phone are required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // SQL UPDATE
        String sql =
                "UPDATE PHOTOGRAPHER " +
                "SET Photographer_Name = ?, " +
                "Phone = ? " +
                "WHERE Photographer_ID = ?";

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

            statement.setString(
                    2,
                    phone
            );

            statement.setInt(
                    3,
                    Integer.parseInt(id)
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Photographer updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadPhotographers();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update photographer.\n\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // DELETE PHOTOGRAPHER
    // =====================================================

    private void deletePhotographer() {

        String id =
                photographerIdField
                .getText()
                .trim();

        // Check selection
        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a photographer from the table first.",
                    "Delete Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Ask for confirmation
        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this photographer?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation !=
                JOptionPane.YES_OPTION) {

            return;
        }

        // SQL DELETE
        String sql =
                "DELETE FROM PHOTOGRAPHER " +
                "WHERE Photographer_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    Integer.parseInt(id)
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Photographer deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadPhotographers();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete photographer.\n\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // LOAD PHOTOGRAPHERS
    // =====================================================

    private void loadPhotographers() {

        // Make sure table model exists
        if (tableModel == null) {
            return;
        }

        // Remove old table rows
        tableModel.setRowCount(0);

        // SQL SELECT
        String sql =
                "SELECT Photographer_ID, " +
                "Photographer_Name, Phone " +
                "FROM PHOTOGRAPHER " +
                "ORDER BY Photographer_ID";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            // Read every photographer
            while (result.next()) {

                tableModel.addRow(
                        new Object[]{

                                result.getInt(
                                        "Photographer_ID"
                                ),

                                result.getString(
                                        "Photographer_Name"
                                ),

                                result.getString(
                                        "Phone"
                                )
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load photographers.\n\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        photographerIdField.setText("");

        photographerNameField.setText("");

        phoneField.setText("");

        photographerTable.clearSelection();

        photographerNameField.requestFocus();
    }

    // =====================================================
    // CREATE FORM LABEL
    // =====================================================

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

        panel.add(
                label,
                gbc
        );
    }

    // =====================================================
    // CREATE BUTTON
    // =====================================================

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

    // =====================================================
    // MAIN METHOD
    // =====================================================

    /*
     * This main method is only for testing
     * PhotographerPanel independently.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            PhotographerPanel panel =
                    new PhotographerPanel();

            panel.setVisible(true);
        });
    }
}
