package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Cart;
import com.ecommerce.payload.CartDTO;
import com.ecommerce.payload.CartItemDTO;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.util.AuthUtil;

@RestController
@RequestMapping("/api")
public class CartController {
	@Autowired
	CartService cartService;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	AuthUtil authUtil;
	@PostMapping("/carts/create")
	public ResponseEntity<String> createOrUpdateCart(@RequestBody List<CartItemDTO> cartItems){
		String response = cartService.createOrUpdateCartWithItems(cartItems);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	@PostMapping("/carts/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
													@PathVariable Integer quantity){
		CartDTO cartDTO = cartService.addProductToCart(productId,quantity);
		return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.CREATED);
	}
	@GetMapping("/carts")
	public ResponseEntity<List<CartDTO>> getCarts(){
		List<CartDTO> cartDTOs = cartService.getAllCarts();
		return new ResponseEntity<List<CartDTO>>(cartDTOs,HttpStatus.FOUND);
	}
	@GetMapping("/carts/users/cart")
	public ResponseEntity<?> getCartById(){
		String emailId = authUtil.loggedInEmail();
		Cart cart = cartRepository.findCartByEmail(emailId);
		if(cart == null){
	        return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
	    }

		Long cartId = cart.getCartId();
		CartDTO cartDTO = cartService.getCart(emailId,cartId);
		return new ResponseEntity<>(cartDTO,HttpStatus.OK);
	}
	@PutMapping("/cart/products/{productId}/quantity/{operation}")
	public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
												@PathVariable String operation){
		CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
				operation.equalsIgnoreCase("delete")?-1:1);
		return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
	}
	@DeleteMapping("/carts/{cartId}/product/{productId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
														@PathVariable Long productId){
		String status = cartService.deleteProductFromCart(cartId,productId);
		return new ResponseEntity<String>(status,HttpStatus.OK);
	}
}
