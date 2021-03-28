package com.pentax.pentazon.repository;

import com.pentax.pentazon.models.Address;
import com.pentax.pentazon.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findOrderByOrderID(String orderID);
    void deleteOrderByOrderID(String orderId);
    List<Order> findOrdersByUserId(String userid);
    Optional<Order> findOrderByDeliveryAddress (Address deliveryAddress);
}