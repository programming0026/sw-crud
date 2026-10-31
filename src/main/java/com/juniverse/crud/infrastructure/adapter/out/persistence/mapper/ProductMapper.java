package com.juniverse.crud.infrastructure.adapter.out.persistence.mapper;

import com.juniverse.crud.domain.model.Product;
import com.juniverse.crud.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

/** Convierte entre {@code Product} y {@code ProductEntity}. */
@Component
public class ProductMapper {

    /** De entidad a dominio. */
    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .categoryId(entity.getCategoryId())
                .build();
    }

    /** De dominio a entidad. */
    public ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }
        return ProductEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .categoryId(domain.getCategoryId())
                .build();
    }
}
