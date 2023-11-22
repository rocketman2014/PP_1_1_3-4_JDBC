package jm.task.core.jdbc.dao;

import jdk.jshell.spi.SPIResolutionException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
                statement.execute("""
                        CREATE TABLE IF NOT EXISTS user (
                               id  INT NOT NULL AUTO_INCREMENT,
                               name        VARCHAR(50)     null,
                               lastName    VARCHAR(50)     null,
                               age         INT             null,
                               PRIMARY KEY (id));""");
        } catch (SQLException e) {
            System.out.println("Таблица не создана");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            System.out.println("Таблицы не сущеcтвует.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement("INSERT INTO user (name, lastName, age) VALUES (?,?,?);");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException | NullPointerException exception) {
            System.out.println("Пользователь не добавлен");
        }

    }

    public void removeUserById(long id) {
        try{
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement("DELETE FROM user where id = ?;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.printf("Пользователь с ID %d удалён.\n", id);
        } catch (SQLException e) {
            System.out.println("Пользователя с таким ID не существует.");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {Statement statement = Util.getConnection().createStatement();
           ResultSet resultSet =statement.executeQuery("SELECT * FROM  user");
           while (resultSet.next()) {
               User user = new User();
               user.setId(resultSet.getLong("id"));
               user.setName(resultSet.getString("name"));
               user.setLastName(resultSet.getString("lastName"));
               user.setAge(resultSet.getByte("age"));
               userList.add(user);
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    public void cleanUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException e) {
            System.out.println("Таблицы не существует.");
        }
    }
}
