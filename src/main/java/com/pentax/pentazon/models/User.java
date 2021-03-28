package com.pentax.pentazon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private List<Address> addresses = new ArrayList<>();
    private List<String>  phoneNumbers = new ArrayList<>();
    private String password;
    @DBRef
    private Set<Role> roles = new HashSet<>();
    String cartId;
    @DBRef
    private List<Order> orderList = new ArrayList<>();
}
