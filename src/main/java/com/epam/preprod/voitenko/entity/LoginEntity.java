package com.epam.preprod.voitenko.entity;

import java.io.Serializable;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;

public class LoginEntity implements Serializable {
    private String email;
    private String password;
    private int failCount;

    public LoginEntity() {
        this.email = EMPTY_STRING;
        this.password = EMPTY_STRING;
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

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public void increaseCount() {
        failCount++;
    }

    public void resetCount() {
        failCount = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginEntity loginEntity = (LoginEntity) o;

        if (email != null ? !email.equals(loginEntity.email) : loginEntity.email != null) {
            return false;
        }
        return password != null ? password.equals(loginEntity.password) : loginEntity.password == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
