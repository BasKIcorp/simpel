package org.simpel.pumpingUnits.model;

import org.simpel.pumpingUnits.model.enums.Role;

public class UsersBuilder {
    private String email;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private String patronymic;
    private String phoneNumber;
    private String jobTitle;
    private String company;

    public UsersBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UsersBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UsersBuilder role(Role role) {
        if(role != null) {
            this.role = role;
        }
        return this;
    }

    public UsersBuilder name(String name) {
        if(name != null) {
            this.name = name;
        }
        return this;
    }

    public UsersBuilder surname(String surname) {
        if(surname != null) {
            this.surname = surname;
        }
        return this;
    }

    public UsersBuilder patronymic(String patronymic) {
        if(patronymic != null) {
            this.patronymic = patronymic;
        }
        return this;
    }

    public UsersBuilder phoneNumber(String phoneNumber) {
        if(phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
        return this;
    }

    public UsersBuilder jobTitle(String jobTitle) {
        if(jobTitle != null) {
            this.jobTitle = jobTitle;
        }
        return this;
    }

    public UsersBuilder company(String company) {
        if(company != null) {
            this.company = company;
        }
        return this;
    }

    public Users build() {
        Users users = new Users();
        users.setEmail(this.email);
        users.setPassword(this.password);
        users.setRole(this.role);
        users.setName(this.name);
        users.setSurname(this.surname);
        users.setPatronymic(this.patronymic);
        users.setPhoneNumber(this.phoneNumber);
        users.setJobTitle(this.jobTitle);
        users.setCompany(this.company);
        return users;
    }
}
