import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Dashboard window for ADMIN, CUSTOMER and PHOTOGRAPHER users.
 */
public class DashboardPanel extends JFrame {

    // Application colours
    private final Color MAROON = new Color(90, 24, 53);
    private final Color DARK_MAROON = new Color(67, 17, 39);
    private final Color LIGHT_MAROON = new Color(125, 48, 77);
    private final Color GOLD = new Color(198, 161, 91);
    private final Color LIGHT_GOLD = new Color(231, 213, 173);
    private final Color CREAM = new Color(251, 248, 244);
    private final Color WHITE = Color.WHITE;
    private final Color DARK_TEXT = new Color(55, 55, 55);
    private final Color GREY_TEXT = new Color(110, 110, 110);
    private final Color BORDER = new Color(224, 215, 205);
    private final Color SOFT_CREAM = new Color(247, 242, 235);

    // Logged-in user information
    private String username;
    private String role;
    private Integer userId;
    private Integer customerId;
    private Integer photographerId;

    // Main content
    private JPanel contentPanel;
    private JPanel dashboardContent;

    // Table
    private DefaultTableModel bookingTableModel;
    private JTable bookingTable;

    // Admin statistics
    private JLabel customerCountLabel;
    private JLabel photographerCountLabel;
    private JLabel packageCountLabel;
    private JLabel bookingCountLabel;
    private JLabel paymentLabel;

    // Customer statistics
    private JLabel myBookingCountLabel;
    private JLabel upcomingWeddingLabel;
    private JLabel customerPaymentLabel;
    private JLabel packageUsedLabel;

    // Photographer statistics
    private JLabel photographerBookingLabel;
    private JLabel upcomingShootLabel;
    private JLabel completedShootLabel;
    private JLabel photographerCustomerLabel;


    // ---------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------

    public DashboardPanel(String username, String role) {
        this(username, role, null, null, null);
    }

    public DashboardPanel(
            String username,
            String role,
            Integer userId,
            Integer customerId,
            Integer photographerId
    ) {

        this.username = username;
        this.role = role;
        this.userId = userId;
        this.customerId = customerId;
        this.photographerId = photographerId;

        setTitle("Namma Ooru Studio - Dashboard");
        setSize(1250, 750);
        setMinimumSize(new Dimension(1100, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildInterface();
        loadDashboardData();
    }


    // ---------------------------------------------------------
    // MAIN INTERFACE
    // ---------------------------------------------------------

    private void buildInterface() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CREAM);

        mainPanel.add(createSidebar(), BorderLayout.WEST);
        mainPanel.add(createMainContent(), BorderLayout.CENTER);

        add(mainPanel);
    }


    // ---------------------------------------------------------
    // SIDEBAR
    // ---------------------------------------------------------

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(285, 0));
        sidebar.setBackground(MAROON);

        // ---------------- BRAND ----------------

        JPanel brandPanel = new JPanel();
        brandPanel.setBackground(MAROON);
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));

        brandPanel.setBorder(
                new EmptyBorder(30, 10, 20, 10)
        );

        JLabel logo = new JLabel(
                "NAMMA OORU STUDIO ♥",
                SwingConstants.CENTER
        );

        logo.setFont(
                new Font("Serif", Font.BOLD, 19)
        );

        logo.setForeground(GOLD);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Wedding Photography",
                SwingConstants.CENTER
        );

        subtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        subtitle.setForeground(WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel management = new JLabel(
                "Management System",
                SwingConstants.CENTER
        );

        management.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        management.setForeground(LIGHT_GOLD);
        management.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator separator = new JSeparator();

        separator.setForeground(GOLD);
        separator.setBackground(GOLD);

        separator.setMaximumSize(
                new Dimension(220, 1)
        );

        brandPanel.add(logo);
        brandPanel.add(Box.createVerticalStrut(8));
        brandPanel.add(subtitle);
        brandPanel.add(management);
        brandPanel.add(Box.createVerticalStrut(18));
        brandPanel.add(separator);

        sidebar.add(
                brandPanel,
                BorderLayout.NORTH
        );


        // ---------------- MENU ----------------

        JPanel menuPanel = new JPanel();

        menuPanel.setBackground(MAROON);
        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setBorder(
                new EmptyBorder(8, 14, 8, 14)
        );

        JButton dashboardButton =
                createMenuButton("Dashboard");

        dashboardButton.addActionListener(
                e -> refreshDashboard()
        );

        menuPanel.add(dashboardButton);
        menuPanel.add(Box.createVerticalStrut(8));


        // ADMIN MENU
        if ("ADMIN".equalsIgnoreCase(role)) {

            JButton customerButton =
                    createMenuButton("Customers");

            JButton photographerButton =
                    createMenuButton("Photographers");

            JButton packageButton =
                    createMenuButton("Packages");

            JButton bookingButton =
                    createMenuButton("Bookings");

            JButton paymentButton =
                    createMenuButton("Payments");

            JButton summaryButton =
                    createMenuButton("Booking Summary");


            customerButton.addActionListener(e -> {
                CustomerPanel panel = new CustomerPanel();
                panel.setVisible(true);
            });


            photographerButton.addActionListener(e -> {
                PhotographerPanel panel =
                        new PhotographerPanel();

                panel.setVisible(true);
            });


            packageButton.addActionListener(e -> {
                PackagePanel panel =
                        new PackagePanel();

                panel.setVisible(true);
            });


            bookingButton.addActionListener(e -> {
                BookingPanel panel =
                        new BookingPanel();

                panel.setVisible(true);
            });


            paymentButton.addActionListener(
                    e -> openPaymentPanel()
            );


            // Open the real booking summary from the database.
            summaryButton.addActionListener(e -> showBookingSummary());


            addMenuItem(menuPanel, customerButton);
            addMenuItem(menuPanel, photographerButton);
            addMenuItem(menuPanel, packageButton);
            addMenuItem(menuPanel, bookingButton);
            addMenuItem(menuPanel, paymentButton);
            addMenuItem(menuPanel, summaryButton);
        }


        // CUSTOMER MENU
        else if ("CUSTOMER".equalsIgnoreCase(role)) {

            JButton myBookingsButton =
                    createMenuButton("My Bookings");

            JButton myPaymentsButton =
                    createMenuButton("My Payments");

            JButton myProfileButton =
                    createMenuButton("My Profile");


            // CHANGED:
            // My Bookings now opens a proper booking display.
            myBookingsButton.addActionListener(
                    e -> showCustomerBookings()
            );


            myPaymentsButton.addActionListener(
                    e -> showCustomerPayments()
            );


            myProfileButton.addActionListener(
                    e -> showCustomerProfile()
            );


            addMenuItem(menuPanel, myBookingsButton);
            addMenuItem(menuPanel, myPaymentsButton);
            addMenuItem(menuPanel, myProfileButton);
        }


        // PHOTOGRAPHER MENU
        else if ("PHOTOGRAPHER".equalsIgnoreCase(role)) {

            JButton myBookingsButton =
                    createMenuButton("My Bookings");

            JButton myProfileButton =
                    createMenuButton("My Profile");


            // CHANGED:
            // My Bookings now opens a proper booking display.
            myBookingsButton.addActionListener(
                    e -> showPhotographerBookings()
            );


            myProfileButton.addActionListener(
                    e -> showPhotographerProfile()
            );


            addMenuItem(menuPanel, myBookingsButton);
            addMenuItem(menuPanel, myProfileButton);
        }


        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );


        // ---------------- LOGOUT ----------------

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        bottomPanel.setBackground(MAROON);

        bottomPanel.setBorder(
                new EmptyBorder(10, 14, 20, 14)
        );

        JButton logoutButton =
                createMenuButton("Logout");


        logoutButton.addActionListener(e -> {

            dispose();

            new LoginPanel().setVisible(true);
        });


        bottomPanel.add(
                logoutButton,
                BorderLayout.CENTER
        );

        sidebar.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        return sidebar;
    }


    private void addMenuItem(
            JPanel menuPanel,
            JButton button
    ) {

        menuPanel.add(button);
        menuPanel.add(Box.createVerticalStrut(8));
    }


    private JButton createMenuButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(WHITE);
        button.setBackground(MAROON);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD,
                                1
                        ),
                        new EmptyBorder(
                                9,
                                12,
                                9,
                                12
                        )
                )
        );

        button.setFocusPainted(false);
        button.setFocusable(false);

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(235, 43)
        );

        button.setPreferredSize(
                new Dimension(235, 43)
        );

        button.setMinimumSize(
                new Dimension(235, 43)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );


        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                LIGHT_MAROON
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

        return button;
    }


    // ---------------------------------------------------------
    // MAIN CONTENT
    // ---------------------------------------------------------

    private JPanel createMainContent() {

        contentPanel =
                new JPanel(new BorderLayout());

        contentPanel.setBackground(CREAM);

        contentPanel.setBorder(
                new EmptyBorder(
                        24,
                        28,
                        24,
                        28
                )
        );


        contentPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );


        dashboardContent =
                new JPanel();

        dashboardContent.setBackground(CREAM);

        dashboardContent.setLayout(
                new BoxLayout(
                        dashboardContent,
                        BoxLayout.Y_AXIS
                )
        );


        dashboardContent.add(
                Box.createVerticalStrut(18)
        );


        if ("ADMIN".equalsIgnoreCase(role)) {

            buildAdminDashboard();

        } else if ("CUSTOMER".equalsIgnoreCase(role)) {

            buildCustomerDashboard();

        } else if ("PHOTOGRAPHER".equalsIgnoreCase(role)) {

            buildPhotographerDashboard();

        } else {

            buildAdminDashboard();
        }


        JScrollPane scrollPane =
                new JScrollPane(
                        dashboardContent
                );

        scrollPane.setBorder(null);

        scrollPane.setBackground(CREAM);

        scrollPane.getViewport()
                .setBackground(CREAM);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);


        contentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        return contentPanel;
    }


    // ---------------------------------------------------------
    // HEADER
    // ---------------------------------------------------------

    private JPanel createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(CREAM);

        header.setPreferredSize(
                new Dimension(0, 50)
        );


        String titleText = "Dashboard";

        if ("ADMIN".equalsIgnoreCase(role)) {

            titleText = "Admin Dashboard";

        } else if ("CUSTOMER".equalsIgnoreCase(role)) {

            titleText = "Customer Dashboard";

        } else if ("PHOTOGRAPHER".equalsIgnoreCase(role)) {

            titleText = "Photographer Dashboard";
        }


        JLabel title =
                new JLabel(titleText);

        title.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(MAROON);


        JPanel userBadge =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                6
                        )
                );

        userBadge.setBackground(WHITE);

        userBadge.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                LIGHT_GOLD
                        ),
                        new EmptyBorder(
                                2,
                                12,
                                2,
                                12
                        )
                )
        );


        JLabel userIcon =
                new JLabel("●");

        userIcon.setForeground(GOLD);


        JLabel userLabel =
                new JLabel(
                        username + "  |  " + role
                );

        userLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        userLabel.setForeground(MAROON);


        userBadge.add(userIcon);
        userBadge.add(userLabel);


        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                userBadge,
                BorderLayout.EAST
        );


        return header;
    }


    // ---------------------------------------------------------
    // ADMIN DASHBOARD
    // ---------------------------------------------------------

    private void buildAdminDashboard() {

        dashboardContent.add(
                createWelcomePanel(
                        "Good day, " + username + "!",
                        "Manage your complete wedding photography studio."
                )
        );

        addGap(20);


        dashboardContent.add(
                createAdminStatistics()
        );

        addGap(20);


        dashboardContent.add(
                createBookingSection(
                        "Upcoming Bookings",
                        "All scheduled wedding photography bookings"
                )
        );

        addGap(20);


        dashboardContent.add(
                createAdminQuickActions()
        );
    }


    private JPanel createAdminStatistics() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                5,
                                14,
                                0
                        )
                );

        panel.setBackground(CREAM);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );


        customerCountLabel =
                createValueLabel("0");

        photographerCountLabel =
                createValueLabel("0");

        packageCountLabel =
                createValueLabel("0");

        bookingCountLabel =
                createValueLabel("0");

        paymentLabel =
                createValueLabel("₹0");


        panel.add(
                createStatisticCard(
                        "Customers",
                        customerCountLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Photographers",
                        photographerCountLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Packages",
                        packageCountLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Bookings",
                        bookingCountLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Total Payment",
                        paymentLabel
                )
        );


        return panel;
    }


    // ---------------------------------------------------------
    // CUSTOMER DASHBOARD
    // ---------------------------------------------------------

    private void buildCustomerDashboard() {

        dashboardContent.add(
                createWelcomePanel(
                        "Welcome, " + username + "!",
                        "Here is everything related to your wedding booking."
                )
        );

        addGap(20);


        dashboardContent.add(
                createCustomerStatistics()
        );

        addGap(20);


        dashboardContent.add(
                createBookingSection(
                        "My Wedding Bookings",
                        "Your booked photography services"
                )
        );

        addGap(20);


        dashboardContent.add(
                createCustomerActions()
        );
    }


    private JPanel createCustomerStatistics() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                14,
                                0
                        )
                );

        panel.setBackground(CREAM);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );


        myBookingCountLabel =
                createValueLabel("0");

        upcomingWeddingLabel =
                createValueLabel("-");

        customerPaymentLabel =
                createValueLabel("₹0");

        packageUsedLabel =
                createValueLabel("0");


        panel.add(
                createStatisticCard(
                        "My Bookings",
                        myBookingCountLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Upcoming Wedding",
                        upcomingWeddingLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Total Paid",
                        customerPaymentLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Packages Used",
                        packageUsedLabel
                )
        );


        return panel;
    }


    // ---------------------------------------------------------
    // PHOTOGRAPHER DASHBOARD
    // ---------------------------------------------------------

    private void buildPhotographerDashboard() {

        dashboardContent.add(
                createWelcomePanel(
                        "Welcome, " + username + "!",
                        "Manage your assigned wedding photography bookings."
                )
        );

        addGap(20);


        dashboardContent.add(
                createPhotographerStatistics()
        );

        addGap(20);


        dashboardContent.add(
                createBookingSection(
                        "My Upcoming Shoots",
                        "Wedding photography assignments assigned to you"
                )
        );

        addGap(20);


        dashboardContent.add(
                createPhotographerActions()
        );
    }


    private JPanel createPhotographerStatistics() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                14,
                                0
                        )
                );

        panel.setBackground(CREAM);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );


        photographerBookingLabel =
                createValueLabel("0");

        upcomingShootLabel =
                createValueLabel("0");

        completedShootLabel =
                createValueLabel("0");

        photographerCustomerLabel =
                createValueLabel("0");


        panel.add(
                createStatisticCard(
                        "My Bookings",
                        photographerBookingLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Upcoming Shoots",
                        upcomingShootLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "Completed Shoots",
                        completedShootLabel
                )
        );

        panel.add(
                createStatisticCard(
                        "My Customers",
                        photographerCustomerLabel
                )
        );


        return panel;
    }


    // ---------------------------------------------------------
    // WELCOME PANEL
    // ---------------------------------------------------------

    private JPanel createWelcomePanel(
            String greetingText,
            String descriptionText
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(MAROON);

        panel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD,
                                1
                        ),
                        new EmptyBorder(
                                20,
                                25,
                                20,
                                25
                        )
                )
        );


        JPanel textPanel =
                new JPanel();

        textPanel.setBackground(MAROON);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel greeting =
                new JLabel(greetingText);

        greeting.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        27
                )
        );

        greeting.setForeground(WHITE);

        greeting.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel description =
                new JLabel(descriptionText);

        description.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        description.setForeground(
                LIGHT_GOLD
        );

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        textPanel.add(greeting);
        textPanel.add(Box.createVerticalStrut(7));
        textPanel.add(description);


        panel.add(
                textPanel,
                BorderLayout.WEST
        );


        panel.setPreferredSize(
                new Dimension(0, 100)
        );

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        100
                )
        );


        return panel;
    }


    // ---------------------------------------------------------
    // STATISTIC CARD
    // ---------------------------------------------------------

    private JPanel createStatisticCard(
            String title,
            JLabel valueLabel
    ) {

        JPanel card =
                new JPanel();

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                12,
                                10,
                                12,
                                10
                        )
                )
        );


        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        titleLabel.setForeground(
                GREY_TEXT
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JSeparator separator =
                new JSeparator();

        separator.setForeground(
                LIGHT_GOLD
        );

        separator.setBackground(
                LIGHT_GOLD
        );

        separator.setMaximumSize(
                new Dimension(70, 1)
        );

        separator.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        valueLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        card.add(titleLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(separator);
        card.add(Box.createVerticalStrut(8));
        card.add(valueLabel);


        return card;
    }


    private JLabel createValueLabel(String value) {

        JLabel label =
                new JLabel(
                        value,
                        SwingConstants.CENTER
                );

        label.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        23
                )
        );

        label.setForeground(MAROON);

        return label;
    }


    // ---------------------------------------------------------
    // BOOKING SECTION
    // ---------------------------------------------------------

    private JPanel createBookingSection(
            String titleText,
            String subtitleText
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(WHITE);

        panel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                14,
                                14,
                                14,
                                14
                        )
                )
        );


        JPanel titlePanel =
                new JPanel(
                        new BorderLayout()
                );

        titlePanel.setBackground(WHITE);

        titlePanel.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        10,
                        0
                )
        );


        JLabel title =
                new JLabel(titleText);

        title.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(MAROON);


        JLabel subtitle =
                new JLabel(subtitleText);

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        subtitle.setForeground(
                GREY_TEXT
        );


        titlePanel.add(
                title,
                BorderLayout.WEST
        );

        titlePanel.add(
                subtitle,
                BorderLayout.EAST
        );


        panel.add(
                titlePanel,
                BorderLayout.NORTH
        );


        String[] columns;

        if ("CUSTOMER".equalsIgnoreCase(role)) {

            columns = new String[]{
                    "Booking ID",
                    "Photographer",
                    "Package",
                    "Wedding Date",
                    "Venue"
            };

        } else if ("PHOTOGRAPHER".equalsIgnoreCase(role)) {

            columns = new String[]{
                    "Booking ID",
                    "Customer",
                    "Package",
                    "Wedding Date",
                    "Venue"
            };

        } else {

            columns = new String[]{
                    "Booking ID",
                    "Customer",
                    "Photographer",
                    "Package",
                    "Wedding Date",
                    "Venue"
            };
        }


        bookingTableModel =
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
                new JTable(
                        bookingTableModel
                );


        configureTable();


        JScrollPane tableScrollPane =
                new JScrollPane(
                        bookingTable
                );

        tableScrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        tableScrollPane.getViewport()
                .setBackground(WHITE);


        panel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );


        panel.setPreferredSize(
                new Dimension(0, 280)
        );

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        280
                )
        );


        return panel;
    }


    private void configureTable() {

        bookingTable.setRowHeight(30);

        bookingTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        bookingTable.setForeground(
                DARK_TEXT
        );

        bookingTable.setBackground(
                WHITE
        );

        bookingTable.setGridColor(
                BORDER
        );

        bookingTable.setShowGrid(true);

        bookingTable.setSelectionBackground(
                new Color(
                        244,
                        235,
                        221
                )
        );

        bookingTable.setSelectionForeground(
                DARK_TEXT
        );

        bookingTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        bookingTable.setFillsViewportHeight(true);


        bookingTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                12
                        )
                );

        bookingTable.getTableHeader()
                .setBackground(MAROON);

        bookingTable.getTableHeader()
                .setForeground(WHITE);

        bookingTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(0, 32)
                );

        bookingTable.getTableHeader()
                .setReorderingAllowed(false);


        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        bookingTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );


        if (bookingTable.getColumnCount() >= 4) {

            bookingTable
                    .getColumnModel()
                    .getColumn(
                            bookingTable.getColumnCount() - 2
                    )
                    .setCellRenderer(
                            centerRenderer
                    );
        }
    }


    // ---------------------------------------------------------
    // ADMIN QUICK ACTIONS
    // ---------------------------------------------------------

    private JPanel createAdminQuickActions() {

        JPanel panel =
                createActionContainer(
                        "Quick Management Actions"
                );


        JButton addCustomer =
                createQuickButton("Add Customer");

        JButton addPhotographer =
                createQuickButton("Add Photographer");

        JButton addPackage =
                createQuickButton("Add Package");

        JButton newBooking =
                createQuickButton("New Booking");

        JButton addPayment =
                createQuickButton("Add Payment");


        addCustomer.addActionListener(e -> {

            CustomerPanel frame =
                    new CustomerPanel();

            frame.setVisible(true);
        });


        addPhotographer.addActionListener(e -> {

            PhotographerPanel frame =
                    new PhotographerPanel();

            frame.setVisible(true);
        });


        addPackage.addActionListener(e -> {

            PackagePanel frame =
                    new PackagePanel();

            frame.setVisible(true);
        });


        newBooking.addActionListener(e -> {

            BookingPanel frame =
                    new BookingPanel();

            frame.setVisible(true);
        });


        addPayment.addActionListener(
                e -> openPaymentPanel()
        );


        JPanel buttons =
                createButtonPanel();

        buttons.add(addCustomer);
        buttons.add(addPhotographer);
        buttons.add(addPackage);
        buttons.add(newBooking);
        buttons.add(addPayment);


        panel.add(
                buttons,
                BorderLayout.CENTER
        );


        return panel;
    }


    // ---------------------------------------------------------
    // CUSTOMER ACTIONS
    // ---------------------------------------------------------

    private JPanel createCustomerActions() {

        JPanel panel =
                createActionContainer(
                        "My Account"
                );


        JButton viewBookings =
                createQuickButton(
                        "Refresh Bookings"
                );

        JButton viewPayments =
                createQuickButton(
                        "View Payments"
                );

        JButton viewProfile =
                createQuickButton(
                        "My Profile"
                );


        viewBookings.addActionListener(
                e -> refreshDashboard()
        );

        viewPayments.addActionListener(
                e -> showCustomerPayments()
        );

        viewProfile.addActionListener(
                e -> showCustomerProfile()
        );


        JPanel buttons =
                createButtonPanel();

        buttons.add(viewBookings);
        buttons.add(viewPayments);
        buttons.add(viewProfile);


        panel.add(
                buttons,
                BorderLayout.CENTER
        );


        return panel;
    }


    // ---------------------------------------------------------
    // PHOTOGRAPHER ACTIONS
    // ---------------------------------------------------------

    private JPanel createPhotographerActions() {

        JPanel panel =
                createActionContainer(
                        "My Photography Work"
                );


        JButton refresh =
                createQuickButton(
                        "Refresh Bookings"
                );

        JButton profile =
                createQuickButton(
                        "My Profile"
                );


        refresh.addActionListener(
                e -> refreshDashboard()
        );

        profile.addActionListener(
                e -> showPhotographerProfile()
        );


        JPanel buttons =
                createButtonPanel();

        buttons.add(refresh);
        buttons.add(profile);


        panel.add(
                buttons,
                BorderLayout.CENTER
        );


        return panel;
    }


    private JPanel createActionContainer(
            String titleText
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(CREAM);

        panel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        75
                )
        );


        JLabel title =
                new JLabel(titleText);

        title.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        18
                )
        );

        title.setForeground(MAROON);


        panel.add(
                title,
                BorderLayout.WEST
        );


        return panel;
    }


    private JPanel createButtonPanel() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        panel.setBackground(CREAM);

        return panel;
    }


    private JButton createQuickButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(WHITE);

        button.setBackground(MAROON);

        button.setFocusPainted(false);

        button.setFocusable(false);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD,
                                1
                        ),
                        new EmptyBorder(
                                8,
                                15,
                                8,
                                15
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                LIGHT_MAROON
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


        return button;
    }


    // ---------------------------------------------------------
    // DATABASE LOADING
    // ---------------------------------------------------------

    private void loadDashboardData() {

        loadRoleStatistics();
        loadUpcomingBookings();
    }


    private void loadRoleStatistics() {

        if ("ADMIN".equalsIgnoreCase(role)) {

            loadAdminStatistics();

        } else if ("CUSTOMER".equalsIgnoreCase(role)) {

            loadCustomerStatistics();

        } else if ("PHOTOGRAPHER".equalsIgnoreCase(role)) {

            loadPhotographerStatistics();
        }
    }


    // ---------------------------------------------------------
    // ADMIN DATABASE DATA
    // ---------------------------------------------------------

    private void loadAdminStatistics() {

        String customerQuery =
                "SELECT COUNT(*) FROM CUSTOMER";

        String photographerQuery =
                "SELECT COUNT(*) FROM PHOTOGRAPHER";

        String packageQuery =
                "SELECT COUNT(*) FROM PACKAGE";

        String bookingQuery =
                "SELECT COUNT(*) FROM BOOKING";

        String paymentQuery =
                "SELECT COALESCE(SUM(Amount), 0) FROM PAYMENT";


        try (
                Connection connection =
                        DBConnection.getConnection()
        ) {

            customerCountLabel.setText(
                    String.valueOf(
                            getSingleInteger(
                                    connection,
                                    customerQuery
                            )
                    )
            );


            photographerCountLabel.setText(
                    String.valueOf(
                            getSingleInteger(
                                    connection,
                                    photographerQuery
                            )
                    )
            );


            packageCountLabel.setText(
                    String.valueOf(
                            getSingleInteger(
                                    connection,
                                    packageQuery
                            )
                    )
            );


            bookingCountLabel.setText(
                    String.valueOf(
                            getSingleInteger(
                                    connection,
                                    bookingQuery
                            )
                    )
            );


            double totalPayment =
                    getSingleDouble(
                            connection,
                            paymentQuery
                    );


            paymentLabel.setText(
                    formatCurrency(totalPayment)
            );


        } catch (SQLException e) {

            showDatabaseError(
                    "Unable to load admin dashboard statistics.",
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // CUSTOMER DATABASE DATA
    // ---------------------------------------------------------

    private void loadCustomerStatistics() {

        if (customerId == null) {
            return;
        }


        String bookingQuery =
                "SELECT COUNT(*) " +
                "FROM BOOKING " +
                "WHERE Customer_ID = ?";


        String upcomingQuery =
                "SELECT MIN(Wedding_Date) " +
                "FROM BOOKING " +
                "WHERE Customer_ID = ? " +
                "AND Wedding_Date >= CURDATE()";


        String paymentQuery =
                "SELECT COALESCE(SUM(pay.Amount), 0) " +
                "FROM PAYMENT pay " +
                "JOIN BOOKING b " +
                "ON pay.Booking_ID = b.Booking_ID " +
                "WHERE b.Customer_ID = ?";


        String packageQuery =
                "SELECT COUNT(DISTINCT Package_ID) " +
                "FROM BOOKING " +
                "WHERE Customer_ID = ?";


        try (
                Connection connection =
                        DBConnection.getConnection()
        ) {


            myBookingCountLabel.setText(
                    String.valueOf(
                            getIntegerWithParameter(
                                    connection,
                                    bookingQuery,
                                    customerId
                            )
                    )
            );


            Date upcomingDate =
                    getDateWithParameter(
                            connection,
                            upcomingQuery,
                            customerId
                    );


            if (upcomingDate == null) {

                upcomingWeddingLabel.setText("-");

            } else {

                upcomingWeddingLabel.setText(
                        new SimpleDateFormat(
                                "dd MMM"
                        ).format(upcomingDate)
                );
            }


            double totalPaid =
                    getDoubleWithParameter(
                            connection,
                            paymentQuery,
                            customerId
                    );


            customerPaymentLabel.setText(
                    formatCurrency(totalPaid)
            );


            packageUsedLabel.setText(
                    String.valueOf(
                            getIntegerWithParameter(
                                    connection,
                                    packageQuery,
                                    customerId
                            )
                    )
            );


        } catch (SQLException e) {

            showDatabaseError(
                    "Unable to load customer dashboard.",
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // PHOTOGRAPHER DATABASE DATA
    // ---------------------------------------------------------

    private void loadPhotographerStatistics() {

        if (photographerId == null) {
            return;
        }


        String bookingQuery =
                "SELECT COUNT(*) " +
                "FROM BOOKING " +
                "WHERE Photographer_ID = ?";


        String upcomingQuery =
                "SELECT COUNT(*) " +
                "FROM BOOKING " +
                "WHERE Photographer_ID = ? " +
                "AND Wedding_Date >= CURDATE()";


        String completedQuery =
                "SELECT COUNT(*) " +
                "FROM BOOKING " +
                "WHERE Photographer_ID = ? " +
                "AND Wedding_Date < CURDATE()";


        String customerQuery =
                "SELECT COUNT(DISTINCT Customer_ID) " +
                "FROM BOOKING " +
                "WHERE Photographer_ID = ?";


        try (
                Connection connection =
                        DBConnection.getConnection()
        ) {


            photographerBookingLabel.setText(
                    String.valueOf(
                            getIntegerWithParameter(
                                    connection,
                                    bookingQuery,
                                    photographerId
                            )
                    )
            );


            upcomingShootLabel.setText(
                    String.valueOf(
                            getIntegerWithParameter(
                                    connection,
                                    upcomingQuery,
                                    photographerId
                            )
                    )
            );


            completedShootLabel.setText(
                    String.valueOf(
                            getIntegerWithParameter(
                                    connection,
                                    completedQuery,
                                    photographerId
                            )
                    )
            );


            photographerCustomerLabel.setText(
                    String.valueOf(
                            getIntegerWithParameter(
                                    connection,
                                    customerQuery,
                                    photographerId
                            )
                    )
            );


        } catch (SQLException e) {

            showDatabaseError(
                    "Unable to load photographer dashboard.",
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // UPCOMING BOOKINGS
    // ---------------------------------------------------------

    private void loadUpcomingBookings() {

        if (bookingTableModel == null) {
            return;
        }


        bookingTableModel.setRowCount(0);


        StringBuilder query =
                new StringBuilder();


        query.append(
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
                "ON b.Package_ID = pk.Package_ID "
        );


        boolean filtered =
                ("CUSTOMER".equalsIgnoreCase(role)
                        && customerId != null)
                ||
                ("PHOTOGRAPHER".equalsIgnoreCase(role)
                        && photographerId != null);


        if ("CUSTOMER".equalsIgnoreCase(role)
                && customerId != null) {

            query.append(
                    "WHERE b.Customer_ID = ? "
            );

        } else if (
                "PHOTOGRAPHER".equalsIgnoreCase(role)
                        && photographerId != null
        ) {

            query.append(
                    "WHERE b.Photographer_ID = ? "
            );
        }


        // Only show upcoming bookings in the dashboard.
        if (filtered) {

            query.append(
                    "AND b.Wedding_Date >= CURDATE() "
            );

        } else {

            query.append(
                    "WHERE b.Wedding_Date >= CURDATE() "
            );
        }


        query.append(
                "ORDER BY b.Wedding_Date ASC"
        );


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                query.toString()
                        )
        ) {


            if ("CUSTOMER".equalsIgnoreCase(role)
                    && customerId != null) {

                statement.setInt(
                        1,
                        customerId
                );

            } else if (
                    "PHOTOGRAPHER".equalsIgnoreCase(role)
                            && photographerId != null
            ) {

                statement.setInt(
                        1,
                        photographerId
                );
            }


            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {


                while (resultSet.next()) {

                    int bookingId =
                            resultSet.getInt(
                                    "Booking_ID"
                            );

                    String customer =
                            resultSet.getString(
                                    "Customer_Name"
                            );

                    String photographer =
                            resultSet.getString(
                                    "Photographer_Name"
                            );

                    String packageName =
                            resultSet.getString(
                                    "Package_Name"
                            );

                    Date weddingDate =
                            resultSet.getDate(
                                    "Wedding_Date"
                            );

                    String venue =
                            resultSet.getString(
                                    "Venue"
                            );


                    if ("CUSTOMER".equalsIgnoreCase(role)) {

                        bookingTableModel.addRow(
                                new Object[]{
                                        bookingId,
                                        photographer,
                                        packageName,
                                        weddingDate,
                                        venue
                                }
                        );

                    } else if (
                            "PHOTOGRAPHER"
                                    .equalsIgnoreCase(role)
                    ) {

                        bookingTableModel.addRow(
                                new Object[]{
                                        bookingId,
                                        customer,
                                        packageName,
                                        weddingDate,
                                        venue
                                }
                        );

                    } else {

                        bookingTableModel.addRow(
                                new Object[]{
                                        bookingId,
                                        customer,
                                        photographer,
                                        packageName,
                                        weddingDate,
                                        venue
                                }
                        );
                    }
                }
            }


        } catch (SQLException e) {

            showDatabaseError(
                    "Unable to load upcoming bookings.",
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // BOOKING SUMMARY (ADMIN)
    // ---------------------------------------------------------

    private void showBookingSummary() {
        JFrame summaryFrame = new JFrame("Namma Ooru Studio - Booking Summary");
        summaryFrame.setSize(1100, 520);
        summaryFrame.setMinimumSize(new Dimension(850, 400));
        summaryFrame.setLocationRelativeTo(this);
        summaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "Booking ID",
                "Customer Name",
                "Customer Phone",
                "Photographer",
                "Package",
                "Price",
                "Wedding Date",
                "Venue"
        };

        DefaultTableModel summaryModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable summaryTable = new JTable(summaryModel);
        summaryTable.setRowHeight(30);
        summaryTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        summaryTable.setForeground(DARK_TEXT);
        summaryTable.setBackground(WHITE);
        summaryTable.setGridColor(BORDER);
        summaryTable.setSelectionBackground(new Color(244, 235, 221));
        summaryTable.setSelectionForeground(DARK_TEXT);
        summaryTable.setAutoCreateRowSorter(true);
        summaryTable.setFillsViewportHeight(true);
        summaryTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        summaryTable.getTableHeader().setBackground(MAROON);
        summaryTable.getTableHeader().setForeground(WHITE);
        summaryTable.getTableHeader().setPreferredSize(new Dimension(0, 32));
        summaryTable.getTableHeader().setReorderingAllowed(false);

        JLabel heading = new JLabel("Booking Summary", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 26));
        heading.setForeground(MAROON);
        heading.setBorder(new EmptyBorder(15, 10, 15, 10));

        JButton refreshButton = createQuickButton("Refresh");
        refreshButton.addActionListener(e -> loadBookingSummary(summaryModel, summaryFrame));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(CREAM);
        topPanel.add(heading, BorderLayout.CENTER);
        topPanel.add(refreshButton, BorderLayout.EAST);
        topPanel.setBorder(new EmptyBorder(5, 12, 5, 12));

        JScrollPane scrollPane = new JScrollPane(summaryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(WHITE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(CREAM);
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        summaryFrame.setContentPane(panel);
        loadBookingSummary(summaryModel, summaryFrame);
        summaryFrame.setVisible(true);
    }

    private void loadBookingSummary(DefaultTableModel summaryModel, JFrame summaryFrame) {
        summaryModel.setRowCount(0);

        String sql =
                "SELECT b.Booking_ID, c.Customer_Name, c.Phone AS Customer_Phone, " +
                "p.Photographer_Name, pk.Package_Name, pk.Price, " +
                "b.Wedding_Date, b.Venue " +
                "FROM BOOKING b " +
                "JOIN CUSTOMER c ON b.Customer_ID = c.Customer_ID " +
                "JOIN PHOTOGRAPHER p ON b.Photographer_ID = p.Photographer_ID " +
                "JOIN PACKAGE pk ON b.Package_ID = pk.Package_ID " +
                "ORDER BY b.Wedding_Date ASC";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {
            while (result.next()) {
                summaryModel.addRow(new Object[]{
                        result.getInt("Booking_ID"),
                        result.getString("Customer_Name"),
                        result.getString("Customer_Phone"),
                        result.getString("Photographer_Name"),
                        result.getString("Package_Name"),
                        result.getBigDecimal("Price"),
                        result.getDate("Wedding_Date"),
                        result.getString("Venue")
                });
            }

            if (summaryModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                        summaryFrame,
                        "No booking records found.",
                        "Booking Summary",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (SQLException e) {
            showDatabaseError("Unable to load booking summary.", e);
        }
    }


    // ---------------------------------------------------------
    // NEW: CUSTOMER MY BOOKINGS
    // ---------------------------------------------------------

    private void showCustomerBookings() {

        if (customerId == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your customer account is not linked to a Customer ID.\n\n"
                            + "Please check the LOGIN_USER table and make sure\n"
                            + "your Customer_ID is correctly assigned.",
                    "My Bookings",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String sql =
                "SELECT " +
                "b.Booking_ID, " +
                "p.Photographer_Name, " +
                "pk.Package_Name, " +
                "b.Wedding_Date, " +
                "b.Venue " +
                "FROM BOOKING b " +
                "JOIN PHOTOGRAPHER p " +
                "ON b.Photographer_ID = p.Photographer_ID " +
                "JOIN PACKAGE pk " +
                "ON b.Package_ID = pk.Package_ID " +
                "WHERE b.Customer_ID = ? " +
                "ORDER BY b.Wedding_Date ASC";


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

                StringBuilder message =
                        new StringBuilder();

                boolean found = false;


                while (result.next()) {

                    found = true;


                    message.append(
                            "BOOKING ID: "
                    ).append(
                            result.getInt(
                                    "Booking_ID"
                            )
                    );


                    message.append(
                            "\nPhotographer: "
                    ).append(
                            result.getString(
                                    "Photographer_Name"
                            )
                    );


                    message.append(
                            "\nPackage: "
                    ).append(
                            result.getString(
                                    "Package_Name"
                            )
                    );


                    message.append(
                            "\nWedding Date: "
                    ).append(
                            result.getDate(
                                    "Wedding_Date"
                            )
                    );


                    message.append(
                            "\nVenue: "
                    ).append(
                            result.getString(
                                    "Venue"
                            )
                    );


                    message.append(
                            "\n\n"
                    );

                    message.append(
                            "----------------------------------------"
                    );

                    message.append(
                            "\n\n"
                    );
                }


                if (!found) {

                    JOptionPane.showMessageDialog(
                            this,
                            "No bookings found for your account.",
                            "My Bookings",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return;
                }


                showBookingMessage(
                        "My Wedding Bookings",
                        message.toString()
                );
            }


        } catch (SQLException e) {

            showDatabaseError(
                    "Unable to load your bookings.",
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // NEW: PHOTOGRAPHER MY BOOKINGS
    // ---------------------------------------------------------

    private void showPhotographerBookings() {

        if (photographerId == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your photographer account is not linked to a Photographer ID.\n\n"
                            + "Please check the LOGIN_USER table and make sure\n"
                            + "your Photographer_ID is correctly assigned.",
                    "My Bookings",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String sql =
                "SELECT " +
                "b.Booking_ID, " +
                "c.Customer_Name, " +
                "pk.Package_Name, " +
                "b.Wedding_Date, " +
                "b.Venue " +
                "FROM BOOKING b " +
                "JOIN CUSTOMER c " +
                "ON b.Customer_ID = c.Customer_ID " +
                "JOIN PACKAGE pk " +
                "ON b.Package_ID = pk.Package_ID " +
                "WHERE b.Photographer_ID = ? " +
                "ORDER BY b.Wedding_Date ASC";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    photographerId
            );


            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                StringBuilder message =
                        new StringBuilder();

                boolean found = false;


                while (result.next()) {

                    found = true;


                    message.append(
                            "BOOKING ID: "
                    ).append(
                            result.getInt(
                                    "Booking_ID"
                            )
                    );


                    message.append(
                            "\nCustomer: "
                    ).append(
                            result.getString(
                                    "Customer_Name"
                            )
                    );


                    message.append(
                            "\nPackage: "
                    ).append(
                            result.getString(
                                    "Package_Name"
                            )
                    );


                    message.append(
                            "\nWedding Date: "
                    ).append(
                            result.getDate(
                                    "Wedding_Date"
                            )
                    );


                    message.append(
                            "\nVenue: "
                    ).append(
                            result.getString(
                                    "Venue"
                            )
                    );


                    message.append(
                            "\n\n"
                    );

                    message.append(
                            "----------------------------------------"
                    );

                    message.append(
                            "\n\n"
                    );
                }


                if (!found) {

                    JOptionPane.showMessageDialog(
                            this,
                            "No bookings found for your account.",
                            "My Bookings",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return;
                }


                showBookingMessage(
                        "My Photography Bookings",
                        message.toString()
                );
            }


        } catch (SQLException e) {

            showDatabaseError(
                    "Unable to load your bookings.",
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // NEW: BOOKING MESSAGE WINDOW
    // ---------------------------------------------------------

    private void showBookingMessage(
            String title,
            String bookingText
    ) {

        JTextArea textArea =
                new JTextArea();

        textArea.setText(
                bookingText
        );

        textArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        textArea.setForeground(
                DARK_TEXT
        );

        textArea.setBackground(
                WHITE
        );

        textArea.setEditable(false);

        textArea.setLineWrap(false);

        textArea.setWrapStyleWord(false);

        textArea.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        textArea
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        600,
                        400
                )
        );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        GOLD,
                        1
                )
        );


        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ---------------------------------------------------------
    // CUSTOMER PAYMENTS
    // ---------------------------------------------------------

    private void showCustomerPayments() {

        if (customerId == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer information is not available.",
                    "Customer",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        String sql =
                "SELECT " +
                "b.Booking_ID, " +
                "pk.Package_Name, " +
                "pay.Amount, " +
                "pay.Payment_Date, " +
                "pay.Payment_Status " +
                "FROM BOOKING b " +
                "JOIN PACKAGE pk " +
                "ON b.Package_ID = pk.Package_ID " +
                "LEFT JOIN PAYMENT pay " +
                "ON b.Booking_ID = pay.Booking_ID " +
                "WHERE b.Customer_ID = ? " +
                "ORDER BY b.Wedding_Date ASC";


        StringBuilder message =
                new StringBuilder();


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


                boolean found = false;


                while (result.next()) {

                    found = true;


                    message.append(
                            "Booking ID: "
                    ).append(
                            result.getInt(
                                    "Booking_ID"
                            )
                    );


                    message.append(
                            "\nPackage: "
                    ).append(
                            result.getString(
                                    "Package_Name"
                            )
                    );


                    message.append(
                            "\nAmount: "
                    ).append(
                            result.getObject(
                                    "Amount"
                            ) == null
                                    ? "Not Paid"
                                    : formatCurrency(
                                            result.getDouble(
                                                    "Amount"
                                            )
                                    )
                    );


                    message.append(
                            "\nPayment Date: "
                    ).append(
                            result.getObject(
                                    "Payment_Date"
                            ) == null
                                    ? "-"
                                    : result.getDate(
                                            "Payment_Date"
                                    )
                    );


                    message.append(
                            "\nStatus: "
                    ).append(
                            result.getObject(
                                    "Payment_Status"
                            ) == null
                                    ? "Pending"
                                    : result.getString(
                                            "Payment_Status"
                                    )
                    );


                    message.append(
                            "\n\n"
                    );
                }


                if (!found) {

                    message.append(
                            "No payment records found."
                    );
                }
            }


        } catch (SQLException e) {

            message.setLength(0);

            message.append(
                    "Unable to load payment information.\n"
            );

            message.append(
                    e.getMessage()
            );
        }


        JOptionPane.showMessageDialog(
                this,
                message.toString(),
                "My Payments",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ---------------------------------------------------------
    // CUSTOMER PROFILE
    // ---------------------------------------------------------

    private void showCustomerProfile() {

        if (customerId == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer information is not available.",
                    "Customer Profile",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        String sql =
                "SELECT Customer_Name, Phone, Email " +
                "FROM CUSTOMER " +
                "WHERE Customer_ID = ?";


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

                    String email =
                            result.getString(
                                    "Email"
                            );


                    JOptionPane.showMessageDialog(
                            this,

                            "Customer ID: "
                                    + customerId
                                    + "\n\nName: "
                                    + result.getString(
                                            "Customer_Name"
                                    )
                                    + "\nPhone: "
                                    + result.getString(
                                            "Phone"
                                    )
                                    + "\nEmail: "
                                    + (
                                    email == null
                                            ? "-"
                                            : email
                            ),

                            "My Profile",

                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }


        } catch (SQLException e) {

            showDatabaseError(
                    "Unable to load customer profile.",
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // PHOTOGRAPHER PROFILE
    // ---------------------------------------------------------

    private void showPhotographerProfile() {

        if (photographerId == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Photographer information is not available.",
                    "Photographer Profile",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        String sql =
                "SELECT Photographer_Name, Phone " +
                "FROM PHOTOGRAPHER " +
                "WHERE Photographer_ID = ?";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {


            statement.setInt(
                    1,
                    photographerId
            );


            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {


                if (result.next()) {

                    JOptionPane.showMessageDialog(
                            this,

                            "Photographer ID: "
                                    + photographerId
                                    + "\n\nName: "
                                    + result.getString(
                                            "Photographer_Name"
                                    )
                                    + "\nPhone: "
                                    + result.getString(
                                            "Phone"
                                    ),

                            "My Profile",

                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }


        } catch (SQLException e) {

            showDatabaseError(
                    "Unable to load photographer profile.",
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // DATABASE HELPER METHODS
    // ---------------------------------------------------------

    private int getSingleInteger(
            Connection connection,
            String query
    ) throws SQLException {

        try (
                PreparedStatement statement =
                        connection.prepareStatement(query);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    private double getSingleDouble(
            Connection connection,
            String query
    ) throws SQLException {

        try (
                PreparedStatement statement =
                        connection.prepareStatement(query);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (resultSet.next()) {

                return resultSet.getDouble(1);
            }
        }

        return 0;
    }


    private int getIntegerWithParameter(
            Connection connection,
            String query,
            int parameter
    ) throws SQLException {

        try (
                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(
                    1,
                    parameter
            );


            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {

                    return result.getInt(1);
                }
            }
        }

        return 0;
    }


    private double getDoubleWithParameter(
            Connection connection,
            String query,
            int parameter
    ) throws SQLException {

        try (
                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(
                    1,
                    parameter
            );


            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {

                    return result.getDouble(1);
                }
            }
        }

        return 0;
    }


    private Date getDateWithParameter(
            Connection connection,
            String query,
            int parameter
    ) throws SQLException {

        try (
                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(
                    1,
                    parameter
            );


            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {

                    return result.getDate(1);
                }
            }
        }

        return null;
    }


    private String formatCurrency(
            double amount
    ) {

        return String.format(
                "₹%,.0f",
                amount
        );
    }


    // ---------------------------------------------------------
    // REFRESH
    // ---------------------------------------------------------

    private void refreshDashboard() {

        loadDashboardData();

        JOptionPane.showMessageDialog(
                this,
                "Dashboard refreshed successfully.",
                "Dashboard",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ---------------------------------------------------------
    // PAYMENT PANEL
    // ---------------------------------------------------------

    private void openPaymentPanel() {

        try {

            Class<?> paymentClass =
                    Class.forName(
                            "PaymentPanel"
                    );


            Object paymentObject =
                    paymentClass
                            .getDeclaredConstructor()
                            .newInstance();


            if (
                    paymentObject
                            instanceof JFrame
            ) {

                JFrame paymentFrame =
                        (JFrame) paymentObject;

                paymentFrame.setVisible(true);
            }


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to open Payment Panel.\n"
                            + e.getMessage(),
                    "Payment Panel",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ---------------------------------------------------------
    // UTILITY METHODS
    // ---------------------------------------------------------

    private void addGap(int height) {

        dashboardContent.add(
                Box.createVerticalStrut(height)
        );
    }


    private void showDatabaseError(
            String message,
            SQLException e
    ) {

        JOptionPane.showMessageDialog(
                this,
                message
                        + "\n\n"
                        + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }


    // ---------------------------------------------------------
    // MAIN METHOD
    // ---------------------------------------------------------

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new DashboardPanel(
                    "Admin",
                    "ADMIN",
                    1,
                    null,
                    null
            ).setVisible(true);

        });
    }
}