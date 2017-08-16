package com.epam.preprod.voitenko.bean;

public class UserBean {
    private int id;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String avatar;

    public UserBean() {
        this.avatar = "default.png";
    }

    public UserBean(String email, String password, String fullName, String phoneNumber, String address) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatar = "default.png";
    }

    public UserBean(String email, String password, String fullName, String phoneNumber, String address, String avatar) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

        if (id != userBean.id) {
            return false;
        }
        if (email != null ? !email.equals(userBean.email) : userBean.email != null) {
            return false;
        }
        if (password != null ? !password.equals(userBean.password) : userBean.password != null) {
            return false;
        }
        if (fullName != null ? !fullName.equals(userBean.fullName) : userBean.fullName != null) {
            return false;
        }
        if (phoneNumber != null ? !phoneNumber.equals(userBean.phoneNumber) : userBean.phoneNumber != null) {
            return false;
        }
        if (address != null ? !address.equals(userBean.address) : userBean.address != null) {
            return false;
        }
        return avatar != null ? avatar.equals(userBean.avatar) : userBean.avatar == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }
}