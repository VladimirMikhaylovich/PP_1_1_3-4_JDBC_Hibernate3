package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    // реализуйте настройку соеденения с БД
    public static final String url = "jdbc:mysql://localhost:3306/test19";
    private static final String username = "root";
    private static final String password = "Anna2012";
    private static final String driverDB = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(driverDB);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Util() {
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
