package com.imdifoods.imdifoodswebcommerce.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "pesanan")
public class Pesanan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Date tanggal;

    private String alamatTujuan;

    private String invoice;

    private Double biaya;

    private Integer idBukti;

    private String status;

    private String urlBuktiPembayaran;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pesanan pesanan = (Pesanan) o;
        return id != null && Objects.equals(id, pesanan.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
