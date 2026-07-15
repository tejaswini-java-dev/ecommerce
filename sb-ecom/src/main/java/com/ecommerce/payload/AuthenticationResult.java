package com.ecommerce.payload;

import org.springframework.http.ResponseCookie;

import com.ecommerce.security.response.UserInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AuthenticationResult {
	private final UserInfoResponse response;
	private final ResponseCookie jwCookie;
}
