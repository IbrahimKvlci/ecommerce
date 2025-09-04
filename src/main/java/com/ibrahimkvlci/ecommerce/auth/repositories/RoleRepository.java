package com.ibrahimkvlci.ecommerce.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibrahimkvlci.ecommerce.auth.models.Role;
import com.ibrahimkvlci.ecommerce.auth.models.Role.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}


