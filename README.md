# SW-CRUD - Spring WebFlux CRUD with Hexagonal Architecture

CRUD reactivo con Spring WebFlux, R2DBC y H2, implementado con arquitectura hexagonal.

## Tecnologías

- Java 21
- Spring Boot 4.0.1
- Spring WebFlux
- Spring Data R2DBC
- H2 Database
- Lombok

## Arquitectura Hexagonal

```
src/main/java/com/juniverse/crud/

├── domain/                              # NÚCLEO DE DOMINIO
│   ├── model/                           # Entidades de dominio
│   │   ├── Category.java
│   │   └── Product.java
│   ├── port/
│   │   ├── in/                          # Puertos de entrada (casos de uso)
│   │   │   ├── CategoryUseCase.java
│   │   │   └── ProductUseCase.java
│   │   └── out/                         # Puertos de salida (repositorios)
│   │       ├── CategoryRepositoryPort.java
│   │       └── ProductRepositoryPort.java
│   └── exception/                       # Excepciones de dominio
│       ├── DomainException.java
│       ├── EntityNotFoundException.java
│       └── BusinessRuleException.java
│
├── application/                         # CAPA DE APLICACIÓN
│   └── service/                         # Implementación de casos de uso
│       ├── CategoryService.java
│       └── ProductService.java
│
└── infrastructure/                      # CAPA DE INFRAESTRUCTURA
    ├── adapter/
    │   ├── in/
    │   │   └── rest/                    # Adaptadores de entrada (API REST)
    │   │       ├── CategoryController.java
    │   │       ├── ProductController.java
    │   │       └── GlobalExceptionHandler.java
    │   └── out/
    │       └── persistence/             # Adaptadores de salida (persistencia)
    │           ├── entity/
    │           │   ├── CategoryEntity.java
    │           │   └── ProductEntity.java
    │           ├── repository/
    │           │   ├── CategoryR2dbcRepository.java
    │           │   └── ProductR2dbcRepository.java
    │           ├── mapper/
    │           │   ├── CategoryMapper.java
    │           │   └── ProductMapper.java
    │           ├── CategoryPersistenceAdapter.java
    │           └── ProductPersistenceAdapter.java
    └── config/
        └── DatabaseConfig.java
```

## Modelo de Datos

```
┌─────────────────┐       ┌─────────────────┐
│   CATEGORIES    │       │    PRODUCTS     │
├─────────────────┤       ├─────────────────┤
│ ID (PK)         │◄──────│ CATEGORY_ID (FK)│
│ NAME            │       │ ID (PK)         │
│ DESCRIPTION     │       │ NAME            │
└─────────────────┘       │ DESCRIPTION     │
                          │ PRICE           │
                          └─────────────────┘
```

## Ejecutar

```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## API Endpoints

### Categories

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/categories` | Listar todas las categorías |
| GET | `/api/categories/{id}` | Obtener categoría por ID |
| GET | `/api/categories/search?name={name}` | Buscar categorías por nombre |
| POST | `/api/categories` | Crear categoría |
| PUT | `/api/categories/{id}` | Actualizar categoría |
| DELETE | `/api/categories/{id}` | Eliminar categoría |

### Products

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/products` | Listar todos los productos |
| GET | `/api/products/{id}` | Obtener producto por ID |
| GET | `/api/products/category/{categoryId}` | Listar productos por categoría |
| GET | `/api/products/search?name={name}` | Buscar productos por nombre |
| POST | `/api/products` | Crear producto |
| PUT | `/api/products/{id}` | Actualizar producto |
| DELETE | `/api/products/{id}` | Eliminar producto |

## Ejemplos de Uso

### Crear categoría

```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name":"Electronics","description":"Electronic devices"}'
```

### Crear producto

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","description":"High performance laptop","price":999.99,"categoryId":1}'
```

### Obtener producto (incluye categoría)

```bash
curl http://localhost:8080/api/products/1
```

Respuesta:
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High performance laptop",
  "price": 999.99,
  "categoryId": 1,
  "category": {
    "id": 1,
    "name": "Electronics",
    "description": "Electronic devices"
  }
}
```
