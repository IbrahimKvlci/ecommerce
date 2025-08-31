# Catalog Product Services

This package provides comprehensive product management functionality for the e-commerce catalog system.

## Package Structure

- **models**: Domain entities (Product)
- **repositories**: Data access layer (ProductRepository)
- **services**: Business logic (ProductService, ProductServiceImpl)
- **controllers**: REST API endpoints (ProductController)
- **dto**: Data transfer objects (ProductDTO)
- **exceptions**: Custom exceptions (ProductNotFoundException, ProductValidationException)

## API Endpoints

### Base URL: `/api/catalog/products`

#### Product CRUD Operations
- `POST /` - Create a new product
- `GET /` - Get all products
- `GET /{id}` - Get product by ID
- `PUT /{id}` - Update product by ID
- `DELETE /{id}` - Delete product by ID

#### Product Search Operations
- `GET /search/title?title={keyword}` - Search products by title
- `GET /search/description?keyword={keyword}` - Search products by description
- `GET /search/price-range?minPrice={min}&maxPrice={max}` - Get products by price range
- `GET /search/max-price?maxPrice={price}` - Get products with price ≤ maxPrice
- `GET /search/min-price?minPrice={price}` - Get products with price ≥ minPrice

## Features

- **Input Validation**: Comprehensive validation using Bean Validation annotations
- **Error Handling**: Custom exceptions for better error management
- **Transaction Management**: All service methods are transactional
- **Search Capabilities**: Multiple search options including title, description, and price filtering
- **Data Transfer Objects**: Separate DTOs for API requests/responses
- **Repository Pattern**: Spring Data JPA repository with custom query methods

## Dependencies Added

- `spring-boot-starter-validation` - For input validation
- Existing dependencies: `spring-boot-starter-data-jpa`, `spring-boot-starter-web`, `lombok`

## Usage Example

```java
// Create a product
ProductDTO productDTO = new ProductDTO();
productDTO.setTitle("Sample Product");
productDTO.setDescription("A sample product description");
productDTO.setPrice(29.99);

Product createdProduct = productService.createProduct(productDTO.toEntity());

// Search products by title
List<Product> products = productService.searchProductsByTitle("Sample");

// Get products by price range
List<Product> affordableProducts = productService.getProductsByMaxPrice(50.0);
```

## Validation Rules

- **Title**: Required, 1-255 characters
- **Description**: Optional, max 1000 characters
- **Price**: Required, must be positive
- **ID**: Must be positive for updates/deletes
