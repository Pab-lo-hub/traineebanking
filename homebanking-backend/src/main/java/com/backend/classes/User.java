package com.backend.classes;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String dni;

    private String tipoDni;

    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    public User() {}

    public User(String dni) {
        this.dni = dni ;
    }

    public User(String dni, String password) {
        this.dni = dni ;
        this.password = password;
    }

    public User(Long id, String dni, String tipoDni, String password) {
        this.id = id ;
        this.dni = dni ;
        this.tipoDni = tipoDni;
        this.password = password;
    }

    public Map<String, Object> UserDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("dni", getDni());
        return dto;
    }

    public String getTipoDni() {
        return tipoDni;
    }

    public void setTipoDni(String tipoDni) {
        this.tipoDni = tipoDni;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

/*
    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }
*/

}
