package bank.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Class to implement database connection
public class DAOUtilities {

	private static final String URL = System.getenv("DB_URL"); // Env Variable for DB URL
	private static final String CONNECTION_USERNAME = System.getenv("DB_USERNAME"); // Env Variable for DB username
	private static final String CONNECTION_PASSWORD = System.getenv("DB_PASSWORD"); // Env Variable for DB password
	private static Connection connection;

	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}

		// If connection was closed then retrieve a new connection
		if (connection.isClosed()) {
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}

}