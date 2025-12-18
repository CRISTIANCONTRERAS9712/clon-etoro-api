package com.clon.etoro.application.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {

    private Long id; // ahora el ID viene en el request
    private String firstname;
    private String lastname;
    private String email; // solo informativo, NO se actualiza
    private String isoCountry; // para actualizar country_id
    private String cellphone;
    private LocalDate birthdate;
    private String password;

    public UpdateUserRequest(
            String firstname,
            String lastname,
            String email,
            LocalDate birthdate,
            String cellphone,
            String password,
            String isoCountry) {
        super();
        this.email = email;
        this.isoCountry = isoCountry;
        this.firstname = firstname;
        this.lastname = lastname;
        this.cellphone = cellphone;
        this.birthdate = birthdate;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
