package com.ibrahimkvlci.ecommerce.auth.repositories;

import com.ibrahimkvlci.ecommerce.auth.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmailIgnoreCase(String email);
    Optional<UserInfo> findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);

    @Query("select u from UserInfo u left join fetch u.roles where upper(u.email) = upper(:email)")
    Optional<UserInfo> findByEmailIgnoreCaseWithRoles(@Param("email") String email);
}


