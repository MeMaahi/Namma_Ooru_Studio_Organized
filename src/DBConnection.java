
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/NAMMA_OORU_STUDIO?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";

    // Enter your actual MySQL password if you have one.
    private static final String PASSWORD = "";

    public static Connection getConnection()
            throws SQLException {

        try {
            // Load the MySQL JDBC driver.
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "MySQL JDBC Driver not found. Check Connector/J.",
                e
            );
        }

        // Establish the database connection.
        return DriverManager.getConnection(
            URL, USER, PASSWORD
        );
    }

    public static void main(String[] args) {

        try {
            Connection connection = getConnection();

            System.out.println(
                "Database connected successfully!"
            );

            connection.close();

        } catch (SQLException e) {
            System.out.println(
                "Database connection failed!"
            );

            e.printStackTrace();
        }
    }
}
