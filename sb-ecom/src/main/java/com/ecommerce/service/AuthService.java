package com.ecommerce.service;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.ecommerce.payload.AuthenticationResult;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.security.request.LoginRequest;
import com.ecommerce.security.request.SignupRequest;
import com.ecommerce.security.response.MessageResponse;
import com.ecommerce.security.response.UserInfoResponse;

import jakarta.validation.Valid;

public interface AuthService {

	AuthenticationResult login(LoginRequest loginRequest);

	ResponseEntity<MessageResponse> register(@Valid SignupRequest signupRequest);

	
	UserInfoResponse getCurrentUserDetails(Authentication authentication);

	ResponseCookie logoutUser();

	
	UserResponse getAllSellers(Pageable pageDetails);

}
