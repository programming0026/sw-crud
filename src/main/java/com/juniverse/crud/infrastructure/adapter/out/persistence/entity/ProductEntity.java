package com.juniverse.crud.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/** Fila de la tabla PRODUCTS. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("PRODUCTS")
public class ProductEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    @Column("CATEGORY_ID")
    private Long categoryId;
}
