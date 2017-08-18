package com.epam.preprod.voitenko.entity;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;

public class LoginEntity {
    private String email;
    private String password;

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
