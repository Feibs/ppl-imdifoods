package com.imdifoods.imdifoodswebcommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Generated
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Email should not be blank")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Message should not be blank")
    @Column(name = "message")
    private String message;

    public static MessageBuilder builder() {
        return new CustomBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var message = (Message) o;
        return id != null && Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private static class CustomBuilder extends MessageBuilder {
        public Message build() {
            if (super.email == null || super.email.trim().length() == 0) {
                throw new IllegalArgumentException("Email should not be blank!");
            }

            if (super.message == null || super.message.trim().length() == 0) {
                throw new IllegalArgumentException("Message should not be blank!");
            }

            return super.build();
        }
    }
}
