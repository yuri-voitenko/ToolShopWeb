package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.bean.UserBean;

import java.util.HashSet;
import java.util.Set;

public class UserRepository {
    private static Set<UserBean> users;

    static {
        users = new HashSet<>();
        users.add(new UserBean("Yuri Voitenko", "Kharkiv, Puskinska st., 79", "+380505730182", "voit@gmail.com", "Voitenko!335"));
        users.add(new UserBean("Vlad Bykov", "Lughansk, Kirova st., 12", "+38-050-56-30-333", "vlad@gmail.com", "Bullsh1t007$"));
        users.add(new UserBean("Vasya Pupkin", "Donetsk, Lenina st., 2", "+38050-789-0182", "pupok@yandex.ru", "BigBoss999*"));
        users.add(new UserBean("John Lo", "Kiev, Belyaeva st., 45", "780955730292", "small_johnny@ukr.net", "AmericanBoy#1970"));
        users.add(new UserBean("Niha Petrov", "Poltava, Puskinska st., 1/A", "+380505730182", "rusich@mail.ru", "NotRaci%st789"));
    }

    private UserRepository() {
    }

    public static boolean addUser(UserBean user) {
        return containsUser(user) && users.add(user);
    }

    public static boolean updateUser(UserBean user) {
        return deleteUser(user) && users.add(user);
    }

    public static boolean containsUser(UserBean user) {
        for (UserBean userBean : users) {
            if (userBean.getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public static boolean deleteUser(UserBean user) {
        for (UserBean userBean : users) {
            if (userBean.getEmail().equals(user.getEmail())) {
                users.remove(userBean);
                return true;
            }
        }
        return false;
    }
}