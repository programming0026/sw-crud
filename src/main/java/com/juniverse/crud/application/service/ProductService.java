package com.juniverse.crud.application.service;

import com.juniverse.crud.domain.exception.BusinessRuleException;
import com.juniverse.crud.domain.exception.EntityNotFoundException;
import com.juniverse.crud.domain.model.Product;
import com.juniverse.crud.domain.port.in.ProductUseCase;
import com.juniverse.crud.domain.port.out.CategoryRepositoryPort;
import com.juniverse.crud.domain.port.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Casos de uso de productos. */
@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;

    /** Todos los productos, con su categoría. */
    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll()
                .flatMap(this::loadCategory);
    }

    /**
     * Busca un producto con su categoría.
     * @param id identificador
     * @return el producto; error 404 si no existe
     */
    @Override
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product", id)))
                .flatMap(this::loadCategory);
    }

    /**
     * Productos de una categoría.
     * @param categoryId identificador de la categoría
     */
    @Override
    public Flux<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .flatMap(this::loadCategory);
    }

    /**
     * Productos cuyo nombre contiene el texto (sin distinguir mayúsculas).
     * @param name texto a buscar
     */
    @Override
    public Flux<Product> findByName(String name) {
        return productRepository.findByNameContaining(name)
                .flatMap(this::loadCategory);
    }

    /**
     * Crea un producto; la categoría debe existir.
     * @param product datos (se ignora el id)
     * @return el producto creado
     */
    @Override
    public Mono<Product> create(Product product) {
        validate(product);
        product.setId(null); // el id lo asigna la base de datos; si llegara uno, save() actualizaría esa fila
        return categoryRepository.findById(product.getCategoryId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Category", product.getCategoryId())))
                .flatMap(category -> productRepository.save(product))
                .flatMap(this::loadCategory);
    }

    /**
     * Actualiza un producto; la categoría debe existir.
     * @param id identificador
     * @param product nuevos datos
     * @return el producto actualizado
     */
    @Override
    public Mono<Product> update(Long id, Product product) {
        validate(product);
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product", id)))
                .flatMap(existing -> categoryRepository.findById(product.getCategoryId())
                        .switchIfEmpty(Mono.error(new EntityNotFoundException("Category", product.getCategoryId())))
                        .flatMap(category -> {
                            existing.setName(product.getName());
                            existing.setDescription(product.getDescription());
                            existing.setPrice(product.getPrice());
                            existing.setCategoryId(product.getCategoryId());
                            return productRepository.save(existing);
                        }))
                .flatMap(this::loadCategory);
    }

    /**
     * Elimina un producto.
     * @param id identificador
     * @return vacío; 404 si no existe
     */
    @Override
    public Mono<Void> delete(Long id) {
        return findById(id) // 404 si no existe
                .then(productRepository.deleteById(id));
    }

    /**
     * Valida los datos de un producto.
     *
     * @param product producto a validar
     * @throws BusinessRuleException si falta el nombre, el precio es negativo o no se indica la categoría
     */
    private void validate(Product product) {
        if (product == null || product.getName() == null || product.getName().isBlank()) {
            throw new BusinessRuleException("The product name is required");
        }
        if (product.getName().length() > 100) {
            throw new BusinessRuleException("The product name cannot exceed 100 characters");
        }
        if (product.getPrice() == null || product.getPrice().signum() < 0) {
            throw new BusinessRuleException("The price is required and cannot be negative");
        }
        if (product.getCategoryId() == null) {
            throw new BusinessRuleException("The categoryId is required");
        }
    }

    /** Carga la categoría del producto. */
    private Mono<Product> loadCategory(Product product) {
        if (product.getCategoryId() == null) {
            return Mono.just(product);
        }
        return categoryRepository.findById(product.getCategoryId())
                .map(category -> {
                    product.setCategory(category);
                    return product;
                })
                .defaultIfEmpty(product);
    }
}
