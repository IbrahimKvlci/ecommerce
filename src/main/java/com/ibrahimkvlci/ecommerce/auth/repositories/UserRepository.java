package com.ibrahimkvlci.ecommerce.auth.repositories;

import com.ibrahimkvlci.ecommerce.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);

    @Query("select u from User u left join fetch u.roles where upper(u.email) = upper(:email)")
    Optional<User> findByEmailIgnoreCaseWithRoles(@Param("email") String email);
}


