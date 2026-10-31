package com.juniverse.crud.application.service;

import com.juniverse.crud.domain.exception.BusinessRuleException;
import com.juniverse.crud.domain.exception.EntityNotFoundException;
import com.juniverse.crud.domain.model.Category;
import com.juniverse.crud.domain.port.in.CategoryUseCase;
import com.juniverse.crud.domain.port.out.CategoryRepositoryPort;
import com.juniverse.crud.domain.port.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Casos de uso de categorías. */
@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryRepositoryPort categoryRepository;
    private final ProductRepositoryPort productRepository;

    /** Todas las categorías. */
    @Override
    public Flux<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Busca una categoría.
     * @param id identificador
     * @return la categoría; error 404 si no existe
     */
    @Override
    public Mono<Category> findById(Long id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Category", id)));
    }

    /**
     * Categorías cuyo nombre contiene el texto (sin distinguir mayúsculas).
     * @param name texto a buscar
     */
    @Override
    public Flux<Category> findByName(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    /**
     * Crea una categoría.
     * @param category datos (se ignora el id)
     * @return la categoría creada
     */
    @Override
    public Mono<Category> create(Category category) {
        validate(category);
        category.setId(null); // el id lo asigna la base de datos; si llegara uno, save() actualizaría esa fila
        return categoryRepository.save(category);
    }

    /**
     * Actualiza nombre y descripción.
     * @param id identificador
     * @param category nuevos datos
     * @return la categoría actualizada; 404 si no existe
     */
    @Override
    public Mono<Category> update(Long id, Category category) {
        validate(category);
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Category", id)))
                .flatMap(existing -> {
                    existing.setName(category.getName());
                    existing.setDescription(category.getDescription());
                    return categoryRepository.save(existing);
                });
    }

    /**
     * Elimina una categoría.
     * @param id identificador
     * @return vacío; 404 si no existe y 400 si tiene productos asociados
     */
    @Override
    public Mono<Void> delete(Long id) {
        return findById(id) // 404 si no existe
                .then(productRepository.existsByCategoryId(id))
                .flatMap(hasProducts -> {
                    if (Boolean.TRUE.equals(hasProducts)) {
                        return Mono.error(new BusinessRuleException(
                                "Cannot delete category with id " + id + ": it has associated products"));
                    }
                    return categoryRepository.deleteById(id);
                });
    }

    /**
     * Valida los datos de una categoría.
     *
     * @param category categoría a validar
     * @throws BusinessRuleException si falta el nombre o excede la longitud de la columna
     */
    private void validate(Category category) {
        if (category == null || category.getName() == null || category.getName().isBlank()) {
            throw new BusinessRuleException("The category name is required");
        }
        if (category.getName().length() > 100) {
            throw new BusinessRuleException("The category name cannot exceed 100 characters");
        }
        if (category.getDescription() != null && category.getDescription().length() > 255) {
            throw new BusinessRuleException("The description cannot exceed 255 characters");
        }
    }
}
