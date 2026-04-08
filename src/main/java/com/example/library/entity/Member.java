package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Formula;
import java.time.LocalDate;

/**
 * Сущность, представляющая читателя библиотеки.
 */
@Entity
@Table(name = "members", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Formula("first_name || ' ' || last_name")
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String address;

    @Column(nullable = false)
    private LocalDate registrationDate = LocalDate.now();

    private boolean isActive = true;

    private int maxAllowedLoans = 5;
}