import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Payment Management Panel
 * Project: Namma Ooru Studio
 */
public class PaymentPanel extends JFrame {

    // =========================
    // COLORS
    // =========================
    private final Color MAROON = new Color(90, 24, 53);
    private final Color GOLD = new Color(198, 161, 91);
    private final Color CREAM = new Color(251, 248, 244);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(55, 45, 50);

    // =========================
    // FORM COMPONENTS
    // =========================
    private JTextField paymentIdField;
    private JComboBox<BookingItem> bookingComboBox;
    private JTextField amountField;
    private JTextField paymentDateField;
    private JComboBox<String> statusComboBox;

    // =========================
    // TABLE
    // =========================
    private JTable paymentTable;
    private DefaultTableModel tableModel;

    // =========================
    // CONSTRUCTOR
    // =========================
    public PaymentPanel() {

        setTitle("Namma Ooru Studio - Payment Management");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(CREAM);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // =========================
        // HEADER
        // =========================

        JLabel titleLabel = new JLabel("PAYMENT MANAGEMENT");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(MAROON);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel =
                new JLabel("Manage customer payment records");

        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(CREAM);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);
        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GOLD, 1),
                        new EmptyBorder(15, 20, 15, 20)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Payment ID
        JLabel paymentIdLabel = createLabel("Payment ID");

        paymentIdField = new JTextField();
        paymentIdField.setEditable(false);

        addFormRow(
                formPanel,
                gbc,
                0,
                paymentIdLabel,
                paymentIdField
        );

        // Booking
        JLabel bookingLabel = createLabel("Booking");

        bookingComboBox = new JComboBox<>();

        addFormRow(
                formPanel,
                gbc,
                1,
                bookingLabel,
                bookingComboBox
        );

        // Amount
        JLabel amountLabel = createLabel("Amount");

        amountField = new JTextField();

        addFormRow(
                formPanel,
                gbc,
                2,
                amountLabel,
                amountField
        );

        // Payment Date
        JLabel dateLabel = createLabel("Payment Date");

        paymentDateField = new JTextField();
        paymentDateField.setText(
                new SimpleDateFormat("yyyy-MM-dd")
                        .format(new java.util.Date())
        );

        addFormRow(
                formPanel,
                gbc,
                3,
                dateLabel,
                paymentDateField
        );

        // Payment Status
        JLabel statusLabel = createLabel("Payment Status");

        statusComboBox = new JComboBox<>(
                new String[]{
                        "Pending",
                        "Paid",
                        "Partial",
                        "Cancelled"
                }
        );

        addFormRow(
                formPanel,
                gbc,
                4,
                statusLabel,
                statusComboBox
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10)
        );

        buttonPanel.setBackground(WHITE);

        JButton addButton = createButton("ADD");
        JButton updateButton = createButton("UPDATE");
        JButton deleteButton = createButton("DELETE");
        JButton clearButton = createButton("CLEAR");
        JButton refreshButton = createButton("REFRESH");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;

        formPanel.add(buttonPanel, gbc);

        // =========================
        // TABLE
        // =========================

        tableModel = new DefaultTableModel(
                new String[]{
                        "Payment ID",
                        "Booking ID",
                        "Customer",
                        "Amount",
                        "Payment Date",
                        "Status"
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

        paymentTable = new JTable(tableModel);

        paymentTable.setRowHeight(28);
        paymentTable.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        paymentTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        paymentTable.getTableHeader().setBackground(MAROON);
        paymentTable.getTableHeader().setForeground(WHITE);

        JScrollPane scrollPane =
                new JScrollPane(paymentTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(GOLD, 1)
        );

        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(CREAM);

        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // =========================
        // LOAD BOOKINGS
        // =========================

        loadBookings();

        // =========================
        // LOAD PAYMENTS
        // =========================

        loadPayments();

        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(e -> addPayment());

        updateButton.addActionListener(e -> updatePayment());

        deleteButton.addActionListener(e -> deletePayment());

        clearButton.addActionListener(e -> clearFields());

        refreshButton.addActionListener(e -> {
            loadBookings();
            loadPayments();
        });

        // =========================
        // TABLE ROW CLICK
        // =========================

        paymentTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int selectedRow =
                                paymentTable.getSelectedRow();

                        if (selectedRow != -1) {

                            loadSelectedPayment(selectedRow);
                        }
                    }
                });
    }

    // =========================================================
    // CREATE LABEL
    // =========================================================

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        label.setForeground(TEXT);

        return label;
    }

    // =========================================================
    // ADD FORM ROW
    // =========================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            JLabel label,
            JComponent component
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;

        component.setPreferredSize(
                new Dimension(300, 35)
        );

        panel.add(component, gbc);
    }

    // =========================================================
    // CREATE BUTTON
    // =========================================================

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        button.setForeground(WHITE);
        button.setBackground(MAROON);

        button.setFocusPainted(false);

        button.setPreferredSize(
                new Dimension(100, 35)
        );

        return button;
    }

    // =========================================================
    // BOOKING ITEM CLASS
    // =========================================================

    private static class BookingItem {

        int bookingId;
        String customerName;

        BookingItem(
                int bookingId,
                String customerName
        ) {

            this.bookingId = bookingId;
            this.customerName = customerName;
        }

        @Override
        public String toString() {

            return bookingId + " - " + customerName;
        }
    }

    // =========================================================
    // LOAD BOOKINGS INTO COMBOBOX
    // =========================================================

    private void loadBookings() {

        bookingComboBox.removeAllItems();

        String sql =
                "SELECT b.Booking_ID, c.Customer_Name " +
                "FROM BOOKING b " +
                "JOIN CUSTOMER c " +
                "ON b.Customer_ID = c.Customer_ID " +
                "ORDER BY b.Booking_ID";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            while (result.next()) {

                int bookingId =
                        result.getInt("Booking_ID");

                String customerName =
                        result.getString("Customer_Name");

                bookingComboBox.addItem(
                        new BookingItem(
                                bookingId,
                                customerName
                        )
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading bookings:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD PAYMENTS
    // =========================================================

    private void loadPayments() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT " +
                "pay.Payment_ID, " +
                "b.Booking_ID, " +
                "c.Customer_Name, " +
                "pay.Amount, " +
                "pay.Payment_Date, " +
                "pay.Payment_Status " +
                "FROM PAYMENT pay " +
                "JOIN BOOKING b " +
                "ON pay.Booking_ID = b.Booking_ID " +
                "JOIN CUSTOMER c " +
                "ON b.Customer_ID = c.Customer_ID " +
                "ORDER BY pay.Payment_ID";

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
                                result.getInt("Payment_ID"),
                                result.getInt("Booking_ID"),
                                result.getString("Customer_Name"),
                                result.getBigDecimal("Amount"),
                                result.getDate("Payment_Date"),
                                result.getString("Payment_Status")
                        }
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading payments:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ADD PAYMENT
    // =========================================================

    private void addPayment() {

        BookingItem booking =
                (BookingItem)
                        bookingComboBox.getSelectedItem();

        String amountText =
                amountField.getText().trim();

        String dateText =
                paymentDateField.getText().trim();

        String status =
                (String)
                        statusComboBox.getSelectedItem();

        // Validation
        if (booking == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a booking."
            );

            return;
        }

        if (amountText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the payment amount."
            );

            return;
        }

        if (dateText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the payment date."
            );

            return;
        }

        BigDecimal amount;

        try {

            amount =
                    new BigDecimal(amountText);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than 0."
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid amount."
            );

            return;
        }

        // Validate date
        Date paymentDate;

        try {

            paymentDate =
                    Date.valueOf(dateText);

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date.\nUse YYYY-MM-DD."
            );

            return;
        }

        String sql =
                "INSERT INTO PAYMENT " +
                "(Booking_ID, Amount, Payment_Date, Payment_Status) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    booking.bookingId
            );

            statement.setBigDecimal(
                    2,
                    amount
            );

            statement.setDate(
                    3,
                    paymentDate
            );

            statement.setString(
                    4,
                    status
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Payment added successfully!"
            );

            loadPayments();

            clearFields();

        } catch (SQLException e) {

            if (e.getMessage().contains("Duplicate")) {

                JOptionPane.showMessageDialog(
                        this,
                        "This booking already has a payment.\n"
                                + "Only one payment is allowed per booking.",
                        "Payment Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error adding payment:\n"
                                + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =========================================================
    // UPDATE PAYMENT
    // =========================================================

    private void updatePayment() {

        if (paymentIdField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment from the table."
            );

            return;
        }

        BookingItem booking =
                (BookingItem)
                        bookingComboBox.getSelectedItem();

        String amountText =
                amountField.getText().trim();

        String dateText =
                paymentDateField.getText().trim();

        String status =
                (String)
                        statusComboBox.getSelectedItem();

        if (booking == null ||
                amountText.isEmpty() ||
                dateText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );

            return;
        }

        BigDecimal amount;

        try {

            amount =
                    new BigDecimal(amountText);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than 0."
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid amount."
            );

            return;
        }

        Date paymentDate;

        try {

            paymentDate =
                    Date.valueOf(dateText);

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date.\nUse YYYY-MM-DD."
            );

            return;
        }

        int paymentId =
                Integer.parseInt(
                        paymentIdField.getText()
                );

        String sql =
                "UPDATE PAYMENT SET " +
                "Booking_ID = ?, " +
                "Amount = ?, " +
                "Payment_Date = ?, " +
                "Payment_Status = ? " +
                "WHERE Payment_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    booking.bookingId
            );

            statement.setBigDecimal(
                    2,
                    amount
            );

            statement.setDate(
                    3,
                    paymentDate
            );

            statement.setString(
                    4,
                    status
            );

            statement.setInt(
                    5,
                    paymentId
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Payment updated successfully!"
            );

            loadPayments();

            clearFields();

        } catch (SQLException e) {

            if (e.getMessage().contains("Duplicate")) {

                JOptionPane.showMessageDialog(
                        this,
                        "This booking already has another payment.",
                        "Payment Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error updating payment:\n"
                                + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =========================================================
    // DELETE PAYMENT
    // =========================================================

    private void deletePayment() {

        if (paymentIdField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment to delete."
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this payment?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        int paymentId =
                Integer.parseInt(
                        paymentIdField.getText()
                );

        String sql =
                "DELETE FROM PAYMENT " +
                "WHERE Payment_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    paymentId
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Payment deleted successfully!"
            );

            loadPayments();

            clearFields();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error deleting payment:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD SELECTED PAYMENT
    // =========================================================

    private void loadSelectedPayment(int row) {

        int paymentId =
                (int)
                        tableModel.getValueAt(
                                row,
                                0
                        );

        int bookingId =
                (int)
                        tableModel.getValueAt(
                                row,
                                1
                        );

        Object amount =
                tableModel.getValueAt(
                        row,
                        3
                );

        Object paymentDate =
                tableModel.getValueAt(
                        row,
                        4
                );

        String status =
                tableModel.getValueAt(
                        row,
                        5
                ).toString();

        paymentIdField.setText(
                String.valueOf(paymentId)
        );

        amountField.setText(
                String.valueOf(amount)
        );

        paymentDateField.setText(
                String.valueOf(paymentDate)
        );

        statusComboBox.setSelectedItem(status);

        // Select matching booking
        for (int i = 0;
             i < bookingComboBox.getItemCount();
             i++) {

            BookingItem item =
                    bookingComboBox.getItemAt(i);

            if (item.bookingId == bookingId) {

                bookingComboBox.setSelectedIndex(i);

                break;
            }
        }
    }

    // =========================================================
    // CLEAR FIELDS
    // =========================================================

    private void clearFields() {

        paymentIdField.setText("");

        amountField.setText("");

        paymentDateField.setText(
                new SimpleDateFormat("yyyy-MM-dd")
                        .format(new java.util.Date())
        );

        statusComboBox.setSelectedIndex(0);

        if (bookingComboBox.getItemCount() > 0) {

            bookingComboBox.setSelectedIndex(0);
        }

        paymentTable.clearSelection();
    }

    // =========================================================
    // MAIN METHOD FOR TESTING
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            PaymentPanel paymentPanel =
                    new PaymentPanel();

            paymentPanel.setVisible(true);
        });
    }
}
