package org.simpel.pumpingUnits.controller.AuthRegisterModel;

public class RegisterRequest {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String patronymic;
    private String phoneNumber;
    private String company;
    private String jobTitle;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public RegisterRequest(String email, String password, String name, String surname, String patronymic, String phoneNumber, String company, String jobTitle) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.jobTitle = jobTitle;
    }

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterRequest() {}
}
