package com.pentax.pentazon.services;

import com.pentax.pentazon.dtos.OrderDTO;
import com.pentax.pentazon.exceptions.OrderException;
import com.pentax.pentazon.models.Address;
import com.pentax.pentazon.models.Order;
import com.pentax.pentazon.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public OrderDTO findOrderById(String orderId) throws OrderException {
        Order order = findAnOrderById(orderId);
        return OrderDTO.packOrderDTO(order);
    }
    private Order findAnOrderById(String orderId) throws OrderException {
        Optional<Order> orderOptional = orderRepository.findOrderByOrderID(orderId);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new OrderException("No Order found with that Id");
        }
    }

    @Override
    public OrderDTO findOrderByDeliveryAddress(Address deliveryAddress) throws OrderException {
        Order order;
        order = findAnOrderByDeliveryAddress(deliveryAddress);
        return OrderDTO.packOrderDTO(order);
    }

    private Order findAnOrderByDeliveryAddress(Address deliveryAddress) throws OrderException {
        Optional<Order> orderOptional = orderRepository.findOrderByDeliveryAddress(deliveryAddress);
        if (orderOptional.isPresent()){
            return orderOptional.get();
        }else{
            throw new OrderException("No Order found with that address");
        }
    }

    @Override
    public OrderDTO updateOrderDetails( String orderId, OrderDTO updatedInformation) throws OrderException {
        Order orderToUpdate = findAnOrderById(orderId);
        if(!orderToUpdate.getDeliveryAddress().equals(updatedInformation.getDeliveryAddress())){
            orderToUpdate.setDeliveryAddress(updatedInformation.getDeliveryAddress());
            Order savedOrder = saveOrder(orderToUpdate);
            return OrderDTO.packOrderDTO(savedOrder);
        } else{
            throw new OrderException("Delivery address is the same");
        }

    }
    private Order saveOrder(Order order){
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrderByOrderId(String orderId) throws OrderException{
        Order orderToCancel = findAnOrderById(orderId);
        deleteOrder(orderToCancel);
    }

    private void deleteOrder(Order orderToDelete){
        orderRepository.delete(orderToDelete);
    }
}
