package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = null;
        try {
            connection = Util.createConnection();
            if (!tableExistsSQL(connection, "Users")) {
                StringBuilder queryCreate = new StringBuilder("create table Users");
                queryCreate.append(" ( id int auto_increment not null, ");
                queryCreate.append(" name varchar(20), ");
                queryCreate.append(" lastName VARCHAR(25), ");
                queryCreate.append(" age int, ");
                queryCreate.append(" PRIMARY KEY ( id ))");
                Statement statement = connection.createStatement();
                statement.execute(queryCreate.toString());
            } else {
                System.out.println("The Table Users already exists");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void dropUsersTable() {

        Connection connection = null;
        try {
            connection = Util.createConnection();
            if (tableExistsSQL(connection, "Users")) {
                String queryDelete = "Drop table Users";
                Statement statement = connection.createStatement();
                statement.execute(queryDelete);
            } else {
                System.out.println("The table is not available");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        Connection connection = null;
        try {
            connection = Util.createConnection();
            if (tableExistsSQL(connection, "USERS")) {
                String queryAdd = "insert into Users (name, lastName, age) "
                        + "values (\"" + name + "\"," + " \"" + lastName + "\"," + age + ")";
                Statement statement = connection.createStatement();
                statement.executeUpdate(queryAdd);
            } else {
                System.out.println("The table is not available");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        try {
            connection = Util.createConnection();
            if (!tableExistsSQL(connection, "Users")) {
                String queryDelete = "DELETE FROM Users WHERE id = " + id + ";";
                Statement statement = connection.createStatement();
                statement.executeUpdate(queryDelete);
            } else {
                System.out.println("The table is not available");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public List<User> getAllUsers() {
        Connection connection = null;
        try {
            connection = Util.createConnection();
            if (tableExistsSQL(connection, "Users")) {
                String queryAll = "select * from Users";
                List<User> users = new ArrayList<>();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(queryAll);
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String lastname = resultSet.getString("lastName");
                    byte age = resultSet.getByte("age");
                    users.add(new User(name, lastname, age));
                }
                return users;
            } else {
                System.out.println("The table is not available allUsers");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public void cleanUsersTable() {
        Connection connection = null;

        try {
            connection = Util.createConnection();
            if (tableExistsSQL(connection, "Users")) {
                String queryClean = "TRUNCATE TABLE Users";
                Statement statement = connection.createStatement();
                statement.execute(queryClean);
            } else {
                System.out.println("The table is not available");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    boolean tableExistsSQL(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
        return resultSet.next();
    }
}
