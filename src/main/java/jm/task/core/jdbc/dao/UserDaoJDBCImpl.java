package jm.task.core.jdbc.dao;

import antlr.CppCodeGenerator;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.createConnection()) {
            StringBuilder queryCreate = new StringBuilder("create table if not exists Users");
            queryCreate.append(" ( id int auto_increment not null, ");
            queryCreate.append(" name varchar(20), ");
            queryCreate.append(" lastName VARCHAR(25), ");
            queryCreate.append(" age int, ");
            queryCreate.append(" PRIMARY KEY ( id ))");
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryCreate.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.createConnection()) {
                String queryDelete = "Drop table if exists Users";
                Statement statement = connection.createStatement();
                statement.executeUpdate(queryDelete);
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.createConnection()) {
            String queryAdd = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(queryAdd);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.createConnection()) {
            String queryDelete = "DELETE FROM Users WHERE id = " + id + ";";
            PreparedStatement statement = connection.prepareStatement(queryDelete);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.createConnection()) {
            String queryAll = "select * from Users";
            List<User> users = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(queryAll);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                users.add(new User(name, lastname, age));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.createConnection()) {
                String queryClean = "TRUNCATE TABLE Users";
                Statement statement = connection.createStatement();
                statement.execute(queryClean);
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
