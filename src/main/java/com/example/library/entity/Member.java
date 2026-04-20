package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "members", uniqueConstraints = {
        @UniqueConstraint(name = "uk_member_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_member_phone", columnNames = "phone")
})
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String address;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDate registrationDate = LocalDate.now();

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(name = "max_allowed_loans", nullable = false)
    private int maxAllowedLoans = 5;

    @Version
    private Long version;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member other)) return false;
        return memberId != null && memberId.equals(other.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
