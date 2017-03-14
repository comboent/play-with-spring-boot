package org.combo;

import org.combo.dao.UserDao;
import org.combo.entity.User;
import org.combo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class Application implements CommandLineRunner{

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        User u = new User();
        u.setUsername("你好");
        u.setPwd("123");
        u.setMaxLevel("100");
        userDao.insert(u);
    }
}
