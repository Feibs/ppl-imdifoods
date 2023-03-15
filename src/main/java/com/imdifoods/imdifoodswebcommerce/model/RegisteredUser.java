package com.imdifoods.imdifoodswebcommerce.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Generated
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "registered_user")
public class RegisteredUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String username;
    private String nama;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var registeredUser = (RegisteredUser) o;
        return id != null && Objects.equals(id, registeredUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
