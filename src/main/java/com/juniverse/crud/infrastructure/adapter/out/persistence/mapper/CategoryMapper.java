package com.juniverse.crud.infrastructure.adapter.out.persistence.mapper;

import com.juniverse.crud.domain.model.Category;
import com.juniverse.crud.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

/** Convierte entre {@code Category} y {@code CategoryEntity}. */
@Component
public class CategoryMapper {

    /** De entidad a dominio. */
    public Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    /** De dominio a entidad. */
    public CategoryEntity toEntity(Category domain) {
        if (domain == null) {
            return null;
        }
        return CategoryEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .build();
    }
}
