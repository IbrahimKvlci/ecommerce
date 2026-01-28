package com.ibrahimkvlci.ecommerce.auth.repositories.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibrahimkvlci.ecommerce.auth.models.redis.CustomerCode;

@Repository
public interface CustomerCodeRepository extends CrudRepository<CustomerCode, String> {

}
