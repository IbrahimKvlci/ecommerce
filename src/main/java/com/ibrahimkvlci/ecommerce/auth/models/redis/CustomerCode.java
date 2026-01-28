package com.ibrahimkvlci.ecommerce.auth.models.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("customer_code")
public class CustomerCode {
    @Id
    @Indexed
    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String code;
    @TimeToLive
    private Long expirationTime = 180L;
}
