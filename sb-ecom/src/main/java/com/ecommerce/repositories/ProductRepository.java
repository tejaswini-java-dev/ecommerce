package com.ecommerce.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{

	Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

	Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

	Page<Product> findByUser(User user, Pageable pageDetails);

}
