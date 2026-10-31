package com.juniverse.crud.infrastructure.adapter.out.persistence;

import com.juniverse.crud.domain.model.Category;
import com.juniverse.crud.domain.port.out.CategoryRepositoryPort;
import com.juniverse.crud.infrastructure.adapter.out.persistence.mapper.CategoryMapper;
import com.juniverse.crud.infrastructure.adapter.out.persistence.repository.CategoryR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Adaptador de salida: guarda categorías con R2DBC. */
@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryRepositoryPort {

    private final CategoryR2dbcRepository repository;
    private final CategoryMapper mapper;

    /** Todas las categorías. */
    @Override
    public Flux<Category> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    /** Busca por id. */
    @Override
    public Mono<Category> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    /** Busca por nombre sin distinguir mayúsculas. */
    @Override
    public Flux<Category> findByNameContaining(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .map(mapper::toDomain);
    }

    /** Inserta o actualiza. */
    @Override
    public Mono<Category> save(Category category) {
        return repository.save(mapper.toEntity(category))
                .map(mapper::toDomain);
    }

    /** Elimina por id. */
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}
