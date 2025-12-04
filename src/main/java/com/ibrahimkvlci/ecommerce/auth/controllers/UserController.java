package com.ibrahimkvlci.ecommerce.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibrahimkvlci.ecommerce.auth.services.UserInfoService;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {

    private final UserInfoService userInfoService;

    @GetMapping("/get-user-id")
    public ResponseEntity<DataResult<Long>> getUserId() {
        log.info("Getting user id");
        return ResponseEntity.ok(userInfoService.getUserIdFromJWT());
    }
}
