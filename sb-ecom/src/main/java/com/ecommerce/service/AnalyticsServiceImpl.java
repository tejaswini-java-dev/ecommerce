package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.payload.AnalyticsResponse;
import com.ecommerce.repositories.OrderRepository;
import com.ecommerce.repositories.ProductRepository;
@Service
public class AnalyticsServiceImpl implements AnalyticsService{
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Override
	public AnalyticsResponse getAnalyticsData() {
		AnalyticsResponse response = new AnalyticsResponse();
		long productCount = productRepository.count();
		long totalOrders=orderRepository.count();
		Double totalRevenue=orderRepository.getTotalRevenue();
		response.setProductCount(String.valueOf(productCount));
		response.setTotalOrders(String.valueOf(totalOrders));
		response.setTotalRevenue(String.valueOf(totalRevenue != null ? totalRevenue:0));
		
		return response;
	}

}
