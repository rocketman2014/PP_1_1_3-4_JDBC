package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

  userService.createUsersTable();
//
       userService.saveUser("Алексей", "Алдокимов", (byte) 22);
//        userService.saveUser("Мария", "Прохустина", (byte) 21);
//        userService.saveUser("Владимир", "Дальновиников", (byte) 19);
//        userService.saveUser("Игнатий", "Лесорубов", (byte) 45);

//        userService.removeUserById(1);
//       userService.getAllUsers();
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}
