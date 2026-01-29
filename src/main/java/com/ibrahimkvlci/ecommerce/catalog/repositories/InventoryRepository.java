package com.ibrahimkvlci.ecommerce.catalog.repositories;

import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);

    Optional<Inventory> findByProductId(Long productId);

    List<Inventory> findAllByProductId(Long productId);

    List<Inventory> findAllByProductIdAndSellerIdNot(Long productId, Long sellerId);

    Optional<Inventory> findByProductIdAndSellerId(Long productId, Long sellerId);

    boolean existsByProductId(Long productId);
}
