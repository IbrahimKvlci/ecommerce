package com.ibrahimkvlci.ecommerce.auth.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibrahimkvlci.ecommerce.auth.models.Role;
import com.ibrahimkvlci.ecommerce.auth.models.Role.RoleEnum;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByName() {
        //Role role = new Role();
        //role.setName("ADMIN");
        //roleRepository.save(role);
        Optional<Role> foundRole = roleRepository.findByName(RoleEnum.CUSTOMER.name());
        System.out.println(foundRole.get().getName());
        assertEquals("CUSTOMER", foundRole.get().getName());
    }

}
