package com.epam.preprod.voitenko.bean;

public class RegisterBean {
    private String email;
    private String password;
    private String repeatedPassword;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String avatar;

    public RegisterBean() {
        this.email = "";
        this.password = "";
        this.repeatedPassword = "";
        this.fullName = "";
        this.phoneNumber = "";
        this.address = "";
        this.avatar = "default.png";
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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
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

        RegisterBean that = (RegisterBean) o;

        if (email != null ? !email.equals(that.email) : that.email != null) {
            return false;
        }
        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }
        if (repeatedPassword != null ? !repeatedPassword.equals(that.repeatedPassword) : that.repeatedPassword != null) {
            return false;
        }
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) {
            return false;
        }
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) {
            return false;
        }
        if (address != null ? !address.equals(that.address) : that.address != null) {
            return false;
        }
        return avatar != null ? avatar.equals(that.avatar) : that.avatar == null;
    }
}