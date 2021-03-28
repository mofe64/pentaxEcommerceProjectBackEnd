package com.pentax.pentazon.controllers;

import com.pentax.pentazon.dtos.ApiResponse;
import com.pentax.pentazon.dtos.OrderDTO;
import com.pentax.pentazon.exceptions.OrderException;
import com.pentax.pentazon.models.Address;
import com.pentax.pentazon.models.Order;
import com.pentax.pentazon.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/{orderId}")
    public ResponseEntity<?> findOrderById(@PathVariable String orderId){
        try{
            OrderDTO orderDTO = orderService.findOrderById(orderId);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } catch (OrderException orderException) {
            return new ResponseEntity<>(new ApiResponse(false, "No order found by that orderID"), HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/{deliveryAddress}")
//    public ResponseEntity<?> findOrderByDeliveryAddress(@PathVariable Address deliveryAddress){
//        try{
//            OrderDTO orderDTO = orderService.findOrderByDeliveryAddress(deliveryAddress);
//            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
//        } catch (OrderException orderException) {
//            return new ResponseEntity<>(new ApiResponse(false, "No order found by that deliveryAddress"), HttpStatus.BAD_REQUEST);
//        }
//    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateOrderDetails(@PathVariable String orderId, @RequestBody OrderDTO updatedInformation){
        try{
            OrderDTO updatedOrder =orderService.updateOrderDetails(orderId, updatedInformation);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);

        } catch (OrderException orderException){
            return new ResponseEntity<>(new ApiResponse(false, "No order found with that ID"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId){

        try{
            orderService.cancelOrderByOrderId(orderId);
            return new ResponseEntity<>(new ApiResponse(true, "Deleted Successfully"), HttpStatus.NO_CONTENT);
        }catch (OrderException orderException){
            return new ResponseEntity<>(new ApiResponse(false, orderException.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

}