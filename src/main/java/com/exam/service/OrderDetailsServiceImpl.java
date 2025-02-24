package com.exam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.config.OrderDetailsMapper;
import com.exam.dto.OrderDetailsDTO;
import com.exam.dto.OrdersAccountDTO;
import com.exam.dto.OrdersDTO;
import com.exam.entity.OrderDetails;
import com.exam.entity.Orders;
import com.exam.entity.Sales;
import com.exam.repository.OrderDetailsRepository;
import com.exam.repository.OrdersRepository;
import com.exam.repository.SalesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
//@RequiredArgsConstructor
//@NoArgsConstructor
@Slf4j
public class OrderDetailsServiceImpl implements OrderDetailsService {

	OrderDetailsRepository orderDetailsRepository;
	OrdersRepository ordersRepository;
	OrderDetailsMapper orderDetailsMapper;
	
	SalesRepository salesRepository;

	public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository, OrdersRepository ordersRepository,
			OrderDetailsMapper orderDetailsMapper, SalesRepository salesRepository) {
		this.orderDetailsRepository = orderDetailsRepository;
		this.ordersRepository = ordersRepository;
		this.orderDetailsMapper = orderDetailsMapper;
		this.salesRepository = salesRepository;
	}

	
	//details 1번째
	@Override
	public Orders findOrder(int cartId) {

		ModelMapper mapper = new ModelMapper();
		OrdersDTO dto = orderDetailsMapper.findOrder(cartId);
		dto.setOrderDate(LocalDateTime.now());
		log.info("orders?:{}", dto);
		// orderId : auto increment로 자동생성됨.
		Orders orders = mapper.map(dto, Orders.class);
		return ordersRepository.save(orders);
	}

	
	//details 2번째
	@Override
	public List<OrderDetailsDTO> findOrderDetails(int cartId) {
		return orderDetailsMapper.findOrderDetails(cartId);
	}

	
	//details 3번째
	@Override
	public List<OrderDetails> saveAllOrderDetails(Orders savedOrder, List<OrderDetailsDTO> list) {
		int orderId = savedOrder.getOrderId();
		
		log.info("savedOrder 정상호출?: {}", savedOrder);

		ModelMapper mapper = new ModelMapper();

		for (OrderDetailsDTO dto : list) {
			dto.setTotalPrice(dto.getAmount() * dto.getTotalPrice());
			dto.setOrderId(orderId);
			dto.setShipping(dto.isShipping());
		}

		log.info("Order details DTO list: {}", list);

		List<OrderDetails> orderDetailsList = list.stream().map(dto -> mapper.map(dto, OrderDetails.class))
				.collect(Collectors.toList());

		log.info("Orderdetailslist: {}", orderDetailsList);
		return orderDetailsRepository.saveAll(orderDetailsList);

	}

		
	//details 4번째
	@Override
	public List<OrderDetailsDTO> findOrderDetailsProducts(int orderId) {
		 List<OrderDetailsDTO> result = orderDetailsMapper.findOrderDetailsProducts(orderId);
		    for (OrderDetailsDTO dto : result) {
		        log.info("OrderDetailsProductsDTO shipping value: {}", dto.isShipping());
		    }
		    return result;
	}


	@Override
	public List<OrdersAccountDTO> FindOrdersAndDetails(int userId) {
		return orderDetailsMapper.FindOrdersAndDetails(userId);
	}
	
	
	

	@Override
	public OrderDetails findByOrderDetailId(int orderDetailId) {
		return orderDetailsRepository.findByOrderDetailId(orderDetailId);
	}
	
	@Override
	public Orders getOrdersByOrderDetailId(int orderDetailId) {
		ModelMapper mapper = new ModelMapper();
		
        OrdersDTO ordersDTO = orderDetailsMapper.findOrderByOrderDetailId(orderDetailId);
        log.info("ordersdto?{}", ordersDTO);
        
        Orders orders = mapper.map(ordersDTO, Orders.class);
        log.info("orders?{}", orders);
        return orders;
    }


	@Override
	public Sales saveSales(Sales sales) {
		
		return salesRepository.save(sales);
	}


}