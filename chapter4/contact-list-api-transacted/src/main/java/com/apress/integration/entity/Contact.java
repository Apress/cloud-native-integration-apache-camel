package com.apress.integration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Contact {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String name;
    public String email;
    public String phone;
    public String company;

    public Contact() { }

}
