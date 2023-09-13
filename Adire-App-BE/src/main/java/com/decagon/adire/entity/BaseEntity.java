package com.decagon.adire.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@DynamicUpdate
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;


    @PrePersist
    protected void onCreate() {
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Timestamp(System.currentTimeMillis());
    }

}
