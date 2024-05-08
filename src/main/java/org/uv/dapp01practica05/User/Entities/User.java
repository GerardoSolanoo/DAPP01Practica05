package org.uv.dapp01practica05.User.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "users_key_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_key_seq", sequenceName = "users_key_seq", initialValue = 1, allocationSize = 1)
    private long key;


    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String roles;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}


