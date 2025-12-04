package com.ibrahimkvlci.ecommerce.auth.services;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.auth.dto.UserMyDetails;
import com.ibrahimkvlci.ecommerce.auth.models.UserInfo;
import com.ibrahimkvlci.ecommerce.auth.repositories.UserInfoRepository;

import com.ibrahimkvlci.ecommerce.auth.exceptions.AuthException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserDetailsService, UserInfoService {

    private final UserInfoRepository userRepository;

    // Method to load user details by username (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database by email (username)
        Optional<UserInfo> userInfo = userRepository.findByEmail(username);

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Convert UserInfo to UserDetails (UserInfoDetails)
        UserInfo user = userInfo.get();
        return new UserMyDetails(user);
    }

    @Override
    public DataResult<Long> getUserIdFromJWT() throws AuthException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            UserInfo user = userRepository.findByEmail(principal.getUsername()).get();
            return new SuccessDataResult<>(user.getId());
        }
        throw new AuthException("User not authenticated!");
    }

}
