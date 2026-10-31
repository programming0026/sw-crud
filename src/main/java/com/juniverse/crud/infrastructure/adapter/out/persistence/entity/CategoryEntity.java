package com.juniverse.crud.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/** Fila de la tabla CATEGORIES. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("CATEGORIES")
public class CategoryEntity {

    @Id
    private Long id;
    private String name;
    private String description;
}
