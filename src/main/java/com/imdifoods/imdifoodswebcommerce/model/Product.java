package com.imdifoods.imdifoodswebcommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Generated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotBlank(message = "Name should not be blank")
    private String name;
    @NotBlank(message = "Description should not be blank")
    private String description;
    @NotBlank(message = "Composition should not be blank")
    private String composition;
    @NotNull
    @Min(0)
    private Integer stock;
    @NotNull
    @Min(0)
    private Double price;
    @NotBlank(message = "ImageId should not be blank")
    private String imageId;

    public static ProductBuilder builder() {
        return new CustomBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private static class CustomBuilder extends ProductBuilder {
        public Product build() {
            if (super.name == null || super.name.trim().length() == 0) {
                throw new IllegalArgumentException("Name should not be blank!");
            }

            if (super.description == null || super.description.trim().length() == 0) {
                throw new IllegalArgumentException("Description should not be blank!");
            }

            if (super.stock == null || super.stock < 0) {
                throw new IllegalArgumentException("Stock should not be negative!");
            }

            if (super.price == null || super.price < 0) {
                throw new IllegalArgumentException("Price should not be negative!");
            }

            if (super.imageId == null || super.imageId.trim().length() == 0) {
                throw new IllegalArgumentException("ImageId should not be blank!");
            }

            return super.build();
        }
    }
}
