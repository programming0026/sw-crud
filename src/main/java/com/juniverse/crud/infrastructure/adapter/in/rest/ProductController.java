package com.juniverse.crud.infrastructure.adapter.in.rest;

import com.juniverse.crud.domain.model.Product;
import com.juniverse.crud.domain.port.in.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** API REST de productos ({@code /api/products}). */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    /** Lista los productos. */
    @GetMapping
    public Flux<Product> getAll() {
        return productUseCase.findAll();
    }

    /** Obtiene un producto por id. */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getById(@PathVariable Long id) {
        return productUseCase.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /** Productos de una categoría. */
    @GetMapping("/category/{categoryId}")
    public Flux<Product> getByCategory(@PathVariable Long categoryId) {
        return productUseCase.findByCategoryId(categoryId);
    }

    /** Busca por nombre. */
    @GetMapping("/search")
    public Flux<Product> searchByName(@RequestParam String name) {
        return productUseCase.findByName(name);
    }

    /** Crea un producto (201). */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> create(@RequestBody Product product) {
        return productUseCase.create(product);
    }

    /** Actualiza un producto. */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> update(@PathVariable Long id, @RequestBody Product product) {
        return productUseCase.update(id, product)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /** Elimina un producto (204). */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return productUseCase.delete(id);
    }
}
