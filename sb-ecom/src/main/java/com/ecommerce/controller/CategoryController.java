package com.ecommerce.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.config.AppConstants;
import com.ecommerce.payload.CategoryDTO;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@RequestMapping("/api")
@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	/*@GetMapping("/echo")
	public ResponseEntity<String> echoMessage(@RequestParam(name="message",defaultValue = "Hello World",required = false) String message){
		return new ResponseEntity<>("Echoed message : "+message,HttpStatus.OK);
	}*/
	@Tag(name="Category APIs",description = "APIs for managing categories")
	@GetMapping("/public/categories")
	//@RequestMapping(value="/public/categories",method =RequestMethod.GET)
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(name="sortBy",defaultValue = AppConstants.SORT_CATEGORIES_BY,required=false) String sortBy,
			@RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_DIR,required=false)String sortOrder){
		CategoryResponse categoryResponse=categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
		return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
	}
	@Tag(name="Category APIs",description = "APIs for managing categories")
	@Operation(summary = "Create category",description = "API to create a new category")
	@ApiResponses({
		@ApiResponse(responseCode = "201",description = "Category is created successfully"),
		@ApiResponse(responseCode = "400",description = "Invalid Input",content = @Content),
		@ApiResponse(responseCode = "500",description = "Internal server error",content = @Content)
	})
	@PostMapping("/admin/categories")
	public ResponseEntity<CategoryDTO> createCategory( @Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO savedCategoryDTO=categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED);
	}
	@DeleteMapping("/admin/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> deleteCategory(@Parameter(description = "ID of the category that you wish to delete") @PathVariable Long categoryId) {
		
			CategoryDTO categoryDTO= categoryService.deleteCategory(categoryId);
			return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
			//return ResponseEntity.ok(status);
			//return ResponseEntity.status(HttpStatus.OK).body(status);
		
	}
	@PutMapping("/admin/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
													@PathVariable Long categoryId){
			
			CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
			return new ResponseEntity<>(savedCategoryDTO ,HttpStatus.OK);
			
		
	}
}
