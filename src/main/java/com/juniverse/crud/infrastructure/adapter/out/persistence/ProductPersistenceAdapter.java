package com.juniverse.crud.infrastructure.adapter.out.persistence;

import com.juniverse.crud.domain.model.Product;
import com.juniverse.crud.domain.port.out.ProductRepositoryPort;
import com.juniverse.crud.infrastructure.adapter.out.persistence.mapper.ProductMapper;
import com.juniverse.crud.infrastructure.adapter.out.persistence.repository.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Adaptador de salida: guarda productos con R2DBC. */
@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final ProductR2dbcRepository repository;
    private final ProductMapper mapper;

    /** Todos los productos. */
    @Override
    public Flux<Product> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    /** Busca por id. */
    @Override
    public Mono<Product> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    /** Productos de una categoría. */
    @Override
    public Flux<Product> findByCategoryId(Long categoryId) {
        return repository.findByCategoryId(categoryId)
                .map(mapper::toDomain);
    }

    /** Busca por nombre sin distinguir mayúsculas. */
    @Override
    public Flux<Product> findByNameContaining(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .map(mapper::toDomain);
    }

    /** Inserta o actualiza. */
    @Override
    public Mono<Product> save(Product product) {
        return repository.save(mapper.toEntity(product))
                .map(mapper::toDomain);
    }

    /** Elimina por id. */
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    /** Indica si la categoría tiene productos. */
    @Override
    public Mono<Boolean> existsByCategoryId(Long categoryId) {
        return repository.existsByCategoryId(categoryId);
    }
}
