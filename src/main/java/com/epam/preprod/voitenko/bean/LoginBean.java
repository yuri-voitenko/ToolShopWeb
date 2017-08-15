package com.epam.preprod.voitenko.bean;

public class LoginBean {
    private String email;
    private String password;

    public LoginBean() {
        this.email = "";
        this.password = "";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginBean loginBean = (LoginBean) o;

        if (email != null ? !email.equals(loginBean.email) : loginBean.email != null) {
            return false;
        }
        return password != null ? password.equals(loginBean.password) : loginBean.password == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
