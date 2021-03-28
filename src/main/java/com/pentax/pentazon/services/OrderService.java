package com.pentax.pentazon.services;

import com.pentax.pentazon.dtos.OrderDTO;
import com.pentax.pentazon.exceptions.OrderException;
import com.pentax.pentazon.models.Address;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderDTO findOrderById(String orderId) throws OrderException;
    OrderDTO findOrderByDeliveryAddress(Address deliveryAddress) throws OrderException;
    OrderDTO updateOrderDetails(String orderId,OrderDTO updatedInformation) throws OrderException;
    void cancelOrderByOrderId(String orderId) throws OrderException;
}
