package org.uv.dapp01practica05.Employee.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Employees")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(generator = "employees_key_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employees_key_seq", sequenceName = "employees_key_seq", initialValue = 1, allocationSize = 1)
    private long key;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}