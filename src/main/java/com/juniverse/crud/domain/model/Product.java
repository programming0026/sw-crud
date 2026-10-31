package com.juniverse.crud.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/** Producto (modelo de dominio); {@code category} se rellena al consultar. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Category category;
}
