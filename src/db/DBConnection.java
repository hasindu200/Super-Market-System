package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

        private static db.DBConnection dbConnection;
        private final Connection connection;

        private DBConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SuperMarket", "root", "Hasindu1234");
        }

        public static db.DBConnection getDbConnection() throws SQLException, ClassNotFoundException {
            return dbConnection == null ? dbConnection = new db.DBConnection() : dbConnection;
        }

        public Connection getConnection() {
            return connection;
        }
    }


