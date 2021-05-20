package com.backend.classes;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String tipoCuenta;

    private Integer saldo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public Account() {}

    public Account(String tipoCuenta, Integer saldo, User user) {
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.user = user;
    }

    public Map<String, Object> ActoDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("User", user.getDni());
        dto.put("tipoDeCuenta", tipoCuenta);
        dto.put("saldo", saldo);
        return dto;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) { this.saldo = saldo; }

/*
    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }
*/


}
