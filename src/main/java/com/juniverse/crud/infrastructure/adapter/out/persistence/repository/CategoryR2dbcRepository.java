package com.juniverse.crud.infrastructure.adapter.out.persistence.repository;

import com.juniverse.crud.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/** Repositorio reactivo de categorías. */
public interface CategoryR2dbcRepository extends ReactiveCrudRepository<CategoryEntity, Long> {

    /** Busca por nombre sin distinguir mayúsculas. */
    Flux<CategoryEntity> findByNameContainingIgnoreCase(String name);
}
