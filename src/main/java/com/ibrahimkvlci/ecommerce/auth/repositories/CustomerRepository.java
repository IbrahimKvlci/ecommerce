package com.ibrahimkvlci.ecommerce.auth.repositories;

import com.ibrahimkvlci.ecommerce.auth.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}


