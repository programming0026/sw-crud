package com.juniverse.crud.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Categoría de productos (modelo de dominio). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long id;
    private String name;
    private String description;
}
