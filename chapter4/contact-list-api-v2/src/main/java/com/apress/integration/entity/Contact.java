package com.apress.integration.entity;

import javax.persistence.*;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(unique = true)
    public String name;
    public String email;
    public String phone;
    public String company;

    public Contact() { }

}
