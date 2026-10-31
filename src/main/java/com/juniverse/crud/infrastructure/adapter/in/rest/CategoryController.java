package com.juniverse.crud.infrastructure.adapter.in.rest;

import com.juniverse.crud.domain.model.Category;
import com.juniverse.crud.domain.port.in.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** API REST de categorías ({@code /api/categories}). */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    /** Lista las categorías. */
    @GetMapping
    public Flux<Category> getAll() {
        return categoryUseCase.findAll();
    }

    /** Obtiene una categoría por id. */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Category>> getById(@PathVariable Long id) {
        return categoryUseCase.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /** Busca por nombre. */
    @GetMapping("/search")
    public Flux<Category> searchByName(@RequestParam String name) {
        return categoryUseCase.findByName(name);
    }

    /** Crea una categoría (201). */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Category> create(@RequestBody Category category) {
        return categoryUseCase.create(category);
    }

    /** Actualiza una categoría. */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Category>> update(@PathVariable Long id, @RequestBody Category category) {
        return categoryUseCase.update(id, category)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /** Elimina una categoría (204). */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return categoryUseCase.delete(id);
    }
}
