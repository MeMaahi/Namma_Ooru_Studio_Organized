import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class BookingPanel extends JFrame {

    // =====================================================
    // COLORS
    // =====================================================

    private final Color MAROON = new Color(90, 24, 53);
    private final Color GOLD = new Color(198, 161, 91);
    private final Color CREAM = new Color(251, 248, 244);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(55, 45, 50);

    // =====================================================
    // FORM COMPONENTS
    // =====================================================

    private JTextField bookingIdField;
    private JComboBox<CustomerItem> customerComboBox;
    private JComboBox<PhotographerItem> photographerComboBox;
    private JComboBox<PackageItem> packageComboBox;
    private JTextField weddingDateField;
    private JTextField venueField;

    // =====================================================
    // TABLE
    // =====================================================

    private JTable bookingTable;
    private DefaultTableModel tableModel;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public BookingPanel() {

        setTitle("Namma Ooru Studio - Booking Management");

        setSize(1100, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // =================================================
        // MAIN PANEL
        // =================================================

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBackground(CREAM);

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        // =================================================
        // HEADER
        // =================================================

        JPanel headerPanel = new JPanel();

        headerPanel.setBackground(MAROON);

        headerPanel.setPreferredSize(
                new Dimension(0, 60)
        );

        JLabel titleLabel =
                new JLabel("BOOKING MANAGEMENT");

        titleLabel.setForeground(WHITE);

        titleLabel.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        26
                )
        );

        headerPanel.add(titleLabel);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =================================================
        // CENTER PANEL
        // =================================================

        JPanel centerPanel =
                new JPanel(new BorderLayout(15, 15));

        centerPanel.setBackground(CREAM);

        // =================================================
        // FORM PANEL
        // =================================================

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
                new Insets(7, 10, 7, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // =================================================
        // BOOKING ID
        // =================================================

        bookingIdField =
                new JTextField();

        bookingIdField.setEditable(false);

        bookingIdField.setBackground(
                new Color(235, 235, 235)
        );

        addFormRow(
                formPanel,
                gbc,
                createLabel("Booking ID"),
                bookingIdField,
                0
        );

        // =================================================
        // CUSTOMER
        // =================================================

        customerComboBox =
                new JComboBox<>();

        customerComboBox.setPreferredSize(
                new Dimension(350, 35)
        );

        addFormRow(
                formPanel,
                gbc,
                createLabel("Customer"),
                customerComboBox,
                1
        );

        // =================================================
        // PHOTOGRAPHER
        // =================================================

        photographerComboBox =
                new JComboBox<>();

        photographerComboBox.setPreferredSize(
                new Dimension(350, 35)
        );

        addFormRow(
                formPanel,
                gbc,
                createLabel("Photographer"),
                photographerComboBox,
                2
        );

        // =================================================
        // PACKAGE
        // =================================================

        packageComboBox =
                new JComboBox<>();

        packageComboBox.setPreferredSize(
                new Dimension(350, 35)
        );

        addFormRow(
                formPanel,
                gbc,
                createLabel("Package"),
                packageComboBox,
                3
        );

        // =================================================
        // WEDDING DATE
        // =================================================

        weddingDateField =
                new JTextField();

        weddingDateField.setToolTipText(
                "Enter date as YYYY-MM-DD"
        );

        addFormRow(
                formPanel,
                gbc,
                createLabel("Wedding Date"),
                weddingDateField,
                4
        );

        // =================================================
        // VENUE
        // =================================================

        venueField =
                new JTextField();

        addFormRow(
                formPanel,
                gbc,
                createLabel("Venue"),
                venueField,
                5
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

        // =================================================
        // FORM + BUTTONS
        // =================================================

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

        // =================================================
        // TABLE
        // =================================================

        String[] columns = {
                "Booking ID",
                "Customer",
                "Photographer",
                "Package",
                "Wedding Date",
                "Venue"
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

        bookingTable =
                new JTable(tableModel);

        bookingTable.setRowHeight(30);

        bookingTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        bookingTable.setSelectionBackground(GOLD);

        bookingTable.setSelectionForeground(TEXT);

        bookingTable.setGridColor(
                new Color(220, 220, 220)
        );

        bookingTable.getTableHeader()
                .setBackground(MAROON);

        bookingTable.getTableHeader()
                .setForeground(WHITE);

        bookingTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                13
                        )
                );

        bookingTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(0, 35)
                );

        JScrollPane scrollPane =
                new JScrollPane(bookingTable);

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

        // =================================================
        // BUTTON ACTIONS
        // =================================================

        addButton.addActionListener(
                e -> addBooking()
        );

        updateButton.addActionListener(
                e -> updateBooking()
        );

        deleteButton.addActionListener(
                e -> deleteBooking()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        refreshButton.addActionListener(
                e -> refreshAll()
        );

        // =================================================
        // TABLE ROW SELECTION
        // =================================================

        bookingTable
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row =
                                bookingTable
                                        .getSelectedRow();

                        if (row != -1) {

                            loadSelectedBooking(row);
                        }
                    }
                });

        // =================================================
        // SET CONTENT
        // =================================================

        setContentPane(mainPanel);

        // =================================================
        // LOAD DATA
        // =================================================

        loadCustomers();

        loadPhotographers();

        loadPackages();

        loadBookings();
    }

    // =====================================================
    // LABEL CREATION
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
    // ADD FORM ROW
    // =====================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            JLabel label,
            JComponent component,
            int row
    ) {

        gbc.gridx = 0;

        gbc.gridy = row;

        gbc.weightx = 0.3;

        panel.add(label, gbc);

        gbc.gridx = 1;

        gbc.weightx = 0.7;

        panel.add(component, gbc);
    }

    // =====================================================
    // CREATE BUTTON
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
                new Dimension(
                        110,
                        38
                )
        );

        return button;
    }

    // =====================================================
    // LOAD CUSTOMERS
    // =====================================================

    private void loadCustomers() {

        customerComboBox.removeAllItems();

        String sql =
                "SELECT Customer_ID, Customer_Name " +
                "FROM CUSTOMER " +
                "ORDER BY Customer_Name";

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
                                "Customer_ID"
                        );

                String name =
                        resultSet.getString(
                                "Customer_Name"
                        );

                customerComboBox.addItem(
                        new CustomerItem(
                                id,
                                name
                        )
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(
                    "loading customers",
                    ex
            );
        }
    }

    // =====================================================
    // LOAD PHOTOGRAPHERS
    // =====================================================

    private void loadPhotographers() {

        photographerComboBox.removeAllItems();

        String sql =
                "SELECT Photographer_ID, Photographer_Name " +
                "FROM PHOTOGRAPHER " +
                "ORDER BY Photographer_Name";

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
                                "Photographer_ID"
                        );

                String name =
                        resultSet.getString(
                                "Photographer_Name"
                        );

                photographerComboBox.addItem(
                        new PhotographerItem(
                                id,
                                name
                        )
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(
                    "loading photographers",
                    ex
            );
        }
    }

    // =====================================================
    // LOAD PACKAGES
    // =====================================================

    private void loadPackages() {

        packageComboBox.removeAllItems();

        String sql =
                "SELECT Package_ID, Package_Name " +
                "FROM PACKAGE " +
                "ORDER BY Package_Name";

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

                packageComboBox.addItem(
                        new PackageItem(
                                id,
                                name
                        )
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(
                    "loading packages",
                    ex
            );
        }
    }

    // =====================================================
    // LOAD BOOKINGS
    // =====================================================

    private void loadBookings() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT " +
                "b.Booking_ID, " +
                "c.Customer_Name, " +
                "p.Photographer_Name, " +
                "pk.Package_Name, " +
                "b.Wedding_Date, " +
                "b.Venue " +
                "FROM BOOKING b " +
                "JOIN CUSTOMER c " +
                "ON b.Customer_ID = c.Customer_ID " +
                "JOIN PHOTOGRAPHER p " +
                "ON b.Photographer_ID = p.Photographer_ID " +
                "JOIN PACKAGE pk " +
                "ON b.Package_ID = pk.Package_ID " +
                "ORDER BY b.Booking_ID";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                tableModel.addRow(
                        new Object[]{
                                resultSet.getInt(
                                        "Booking_ID"
                                ),

                                resultSet.getString(
                                        "Customer_Name"
                                ),

                                resultSet.getString(
                                        "Photographer_Name"
                                ),

                                resultSet.getString(
                                        "Package_Name"
                                ),

                                resultSet.getDate(
                                        "Wedding_Date"
                                ),

                                resultSet.getString(
                                        "Venue"
                                )
                        }
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(
                    "loading bookings",
                    ex
            );
        }
    }

    // =====================================================
    // ADD BOOKING
    // =====================================================

    private void addBooking() {

        CustomerItem customer =
                (CustomerItem)
                        customerComboBox
                                .getSelectedItem();

        PhotographerItem photographer =
                (PhotographerItem)
                        photographerComboBox
                                .getSelectedItem();

        PackageItem packageItem =
                (PackageItem)
                        packageComboBox
                                .getSelectedItem();

        String dateText =
                weddingDateField
                        .getText()
                        .trim();

        String venue =
                venueField
                        .getText()
                        .trim();

        // -------------------------------------------------
        // VALIDATION
        // -------------------------------------------------

        if (customer == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a Customer."
            );

            return;
        }

        if (photographer == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a Photographer."
            );

            return;
        }

        if (packageItem == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a Package."
            );

            return;
        }

        if (!isValidDate(dateText)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Wedding Date in YYYY-MM-DD format."
            );

            return;
        }

        if (venue.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Venue."
            );

            return;
        }

        // -------------------------------------------------
        // INSERT
        // -------------------------------------------------

        String sql =
                "INSERT INTO BOOKING " +
                "(Customer_ID, Photographer_ID, Package_ID, " +
                "Wedding_Date, Venue) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    customer.id
            );

            statement.setInt(
                    2,
                    photographer.id
            );

            statement.setInt(
                    3,
                    packageItem.id
            );

            statement.setDate(
                    4,
                    Date.valueOf(dateText)
            );

            statement.setString(
                    5,
                    venue
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Booking added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadBookings();

        } catch (SQLException ex) {

            showDatabaseError(
                    "adding booking",
                    ex
            );
        }
    }

    // =====================================================
    // UPDATE BOOKING
    // =====================================================

    private void updateBooking() {

        String idText =
                bookingIdField
                        .getText()
                        .trim();

        if (idText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a booking first."
            );

            return;
        }

        CustomerItem customer =
                (CustomerItem)
                        customerComboBox
                                .getSelectedItem();

        PhotographerItem photographer =
                (PhotographerItem)
                        photographerComboBox
                                .getSelectedItem();

        PackageItem packageItem =
                (PackageItem)
                        packageComboBox
                                .getSelectedItem();

        String dateText =
                weddingDateField
                        .getText()
                        .trim();

        String venue =
                venueField
                        .getText()
                        .trim();

        if (customer == null ||
                photographer == null ||
                packageItem == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select Customer, Photographer and Package."
            );

            return;
        }

        if (!isValidDate(dateText)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Wedding Date in YYYY-MM-DD format."
            );

            return;
        }

        if (venue.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Venue."
            );

            return;
        }

        int bookingId =
                Integer.parseInt(idText);

        String sql =
                "UPDATE BOOKING SET " +
                "Customer_ID = ?, " +
                "Photographer_ID = ?, " +
                "Package_ID = ?, " +
                "Wedding_Date = ?, " +
                "Venue = ? " +
                "WHERE Booking_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    customer.id
            );

            statement.setInt(
                    2,
                    photographer.id
            );

            statement.setInt(
                    3,
                    packageItem.id
            );

            statement.setDate(
                    4,
                    Date.valueOf(dateText)
            );

            statement.setString(
                    5,
                    venue
            );

            statement.setInt(
                    6,
                    bookingId
            );

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                loadBookings();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking not found."
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(
                    "updating booking",
                    ex
            );
        }
    }

    // =====================================================
    // DELETE BOOKING
    // =====================================================

    private void deleteBooking() {

        String idText =
                bookingIdField
                        .getText()
                        .trim();

        if (idText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a booking first."
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this booking?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        int bookingId =
                Integer.parseInt(idText);

        String sql =
                "DELETE FROM BOOKING " +
                "WHERE Booking_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    bookingId
            );

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                loadBookings();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking not found."
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(
                    "deleting booking",
                    ex
            );
        }
    }

    // =====================================================
    // LOAD SELECTED BOOKING
    // =====================================================

    private void loadSelectedBooking(int row) {

        String bookingId =
                tableModel
                        .getValueAt(row, 0)
                        .toString();

        String customerName =
                tableModel
                        .getValueAt(row, 1)
                        .toString();

        String photographerName =
                tableModel
                        .getValueAt(row, 2)
                        .toString();

        String packageName =
                tableModel
                        .getValueAt(row, 3)
                        .toString();

        String weddingDate =
                tableModel
                        .getValueAt(row, 4)
                        .toString();

        String venue =
                tableModel
                        .getValueAt(row, 5)
                        .toString();

        bookingIdField.setText(
                bookingId
        );

        weddingDateField.setText(
                weddingDate
        );

        venueField.setText(
                venue
        );

        // Select customer
        selectCustomer(customerName);

        // Select photographer
        selectPhotographer(photographerName);

        // Select package
        selectPackage(packageName);
    }

    // =====================================================
    // SELECT CUSTOMER
    // =====================================================

    private void selectCustomer(String name) {

        for (int i = 0;
             i < customerComboBox.getItemCount();
             i++) {

            CustomerItem item =
                    customerComboBox
                            .getItemAt(i);

            if (item.name.equals(name)) {

                customerComboBox
                        .setSelectedIndex(i);

                break;
            }
        }
    }

    // =====================================================
    // SELECT PHOTOGRAPHER
    // =====================================================

    private void selectPhotographer(String name) {

        for (int i = 0;
             i < photographerComboBox.getItemCount();
             i++) {

            PhotographerItem item =
                    photographerComboBox
                            .getItemAt(i);

            if (item.name.equals(name)) {

                photographerComboBox
                        .setSelectedIndex(i);

                break;
            }
        }
    }

    // =====================================================
    // SELECT PACKAGE
    // =====================================================

    private void selectPackage(String name) {

        for (int i = 0;
             i < packageComboBox.getItemCount();
             i++) {

            PackageItem item =
                    packageComboBox
                            .getItemAt(i);

            if (item.name.equals(name)) {

                packageComboBox
                        .setSelectedIndex(i);

                break;
            }
        }
    }

    // =====================================================
    // DATE VALIDATION
    // =====================================================

    private boolean isValidDate(String dateText) {

        if (dateText.isEmpty()) {

            return false;
        }

        try {

            SimpleDateFormat format =
                    new SimpleDateFormat(
                            "yyyy-MM-dd"
                    );

            format.setLenient(false);

            format.parse(dateText);

            return true;

        } catch (Exception ex) {

            return false;
        }
    }

    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        bookingIdField.setText("");

        weddingDateField.setText("");

        venueField.setText("");

        if (customerComboBox.getItemCount() > 0) {

            customerComboBox.setSelectedIndex(0);
        }

        if (photographerComboBox.getItemCount() > 0) {

            photographerComboBox.setSelectedIndex(0);
        }

        if (packageComboBox.getItemCount() > 0) {

            packageComboBox.setSelectedIndex(0);
        }

        bookingTable.clearSelection();

        weddingDateField.requestFocus();
    }

    // =====================================================
    // REFRESH ALL
    // =====================================================

    private void refreshAll() {

        loadCustomers();

        loadPhotographers();

        loadPackages();

        loadBookings();

        JOptionPane.showMessageDialog(
                this,
                "Booking data refreshed."
        );
    }

    // =====================================================
    // DATABASE ERROR
    // =====================================================

    private void showDatabaseError(
            String operation,
            SQLException ex
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Error " +
                operation +
                ":\n" +
                ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // =====================================================
    // CUSTOMER CLASS
    // =====================================================

    private static class CustomerItem {

        int id;

        String name;

        CustomerItem(
                int id,
                String name
        ) {

            this.id = id;

            this.name = name;
        }

        @Override
        public String toString() {

            return id + " - " + name;
        }
    }

    // =====================================================
    // PHOTOGRAPHER CLASS
    // =====================================================

    private static class PhotographerItem {

        int id;

        String name;

        PhotographerItem(
                int id,
                String name
        ) {

            this.id = id;

            this.name = name;
        }

        @Override
        public String toString() {

            return id + " - " + name;
        }
    }

    // =====================================================
    // PACKAGE CLASS
    // =====================================================

    private static class PackageItem {

        int id;

        String name;

        PackageItem(
                int id,
                String name
        ) {

            this.id = id;

            this.name = name;
        }

        @Override
        public String toString() {

            return id + " - " + name;
        }
    }

    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            BookingPanel panel =
                    new BookingPanel();

            panel.setVisible(true);
        });
    }
}
