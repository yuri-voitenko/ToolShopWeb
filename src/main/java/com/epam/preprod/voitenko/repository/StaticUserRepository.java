package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.bean.UserBean;

import java.util.HashSet;
import java.util.Set;

public class StaticUserRepository {
    private static Set<UserBean> users;

    static {
        users = new HashSet<>();
        users.add(new UserBean("voit@gmail.com", "Voitenko!335", "Yuri Voitenko", "+380505730182", "Kharkiv, Puskinska st., 79"));
        users.add(new UserBean("vlad@gmail.com", "Bullsh1t007$", "Vlad Bykov", "+38-050-56-30-333", "Lughansk, Kirova st., 12"));
        users.add(new UserBean("pupok@yandex.ru", "BigBoss999*", "Vasya Pupkin", "+38050-789-0182", "Donetsk, Lenina st., 2"));
        users.add(new UserBean("small_johnny@ukr.net", "AmericanBoy#1970", "John Lo", "780955730292", "Kiev, Belyaeva st., 45"));
        users.add(new UserBean("rusich@mail.ru", "NotRaci%st789", "Niha Petrov", "+380505730182", "Poltava, Puskinska st., 1/A"));
    }

    private StaticUserRepository() {
    }

    public static boolean addUser(UserBean user) {
        return !containsUser(user) && users.add(user);
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