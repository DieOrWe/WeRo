package com.example.demo.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userName;

    @Column(nullable = false, length = 128)
    @NotNull @Length(min = 5, max = 128)
    private String password;

    private float price;
}
