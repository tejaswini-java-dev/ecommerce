package com.ecommerce.service;

import java.util.List;

import com.ecommerce.payload.CartDTO;
import com.ecommerce.payload.CartItemDTO;

import jakarta.transaction.Transactional;

public interface CartService {

	CartDTO addProductToCart(Long productId, Integer quantity);

	List<CartDTO> getAllCarts();

	CartDTO getCart(String emailId, Long cartId);
	
	@Transactional
	CartDTO updateProductQuantityInCart(Long productId, int quantity);

	String deleteProductFromCart(Long cartId, Long productId);

	void updateProductInCarts(Long cartId, Long productId);

	String createOrUpdateCartWithItems(List<CartItemDTO> cartItems);

	

	
		
		
}
