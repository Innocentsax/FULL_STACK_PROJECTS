package com.example.hive.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EscrowWallet extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String escrowWallet_id;

    private BigDecimal escrowAmount;

    @OneToOne(cascade = CascadeType.ALL)
    private Task task;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EscrowWallet that = (EscrowWallet) o;
        return getEscrowWallet_id() != null && Objects.equals(getEscrowWallet_id(), that.getEscrowWallet_id());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
