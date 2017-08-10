package com.epam.preprod.voitenko.bean;

import org.apache.commons.codec.digest.DigestUtils;

public class UserBean {
    private String fullName;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;

    public UserBean() {
    }

    public UserBean(String fullName, String address, String phoneNumber, String email, String password) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = DigestUtils.md5Hex(password + fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(password + fullName);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserBean userBean = (UserBean) o;
        if (fullName != null ? !fullName.equals(userBean.fullName) : userBean.fullName != null) {
            return false;
        }
        if (address != null ? !address.equals(userBean.address) : userBean.address != null) {
            return false;
        }
        if (phoneNumber != null ? !phoneNumber.equals(userBean.phoneNumber) : userBean.phoneNumber != null) {
            return false;
        }
        if (email != null ? !email.equals(userBean.email) : userBean.email != null) {
            return false;
        }
        return password != null ? password.equals(userBean.password) : userBean.password == null;
    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}