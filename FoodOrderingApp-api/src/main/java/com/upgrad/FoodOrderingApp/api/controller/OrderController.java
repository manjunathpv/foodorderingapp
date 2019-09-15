package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.SaveOrderRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveOrderResponse;
import com.upgrad.FoodOrderingApp.service.businness.CouponBusinessService;
import com.upgrad.FoodOrderingApp.service.businness.OrdersBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderController {

  @Autowired
  private CouponBusinessService couponBusinessService;

  @Autowired
  private OrdersBusinessService ordersBusinessService;

  @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{couponName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CouponDetailsResponse> getCouponByName(@PathVariable("couponName") final String couponName, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, CouponNotFoundException {
    final CouponEntity singleCoupon = couponBusinessService.getCouponByName(couponName, authorization);

    CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse()
            .couponName(singleCoupon.getCouponName())
            .id(UUID.fromString(singleCoupon.getUuid()))
            .percent(singleCoupon.getPercent());

    return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SaveOrderResponse> createAnswer (@RequestHeader("authorization") final String authorization, final SaveOrderRequest orderRequest) throws AuthorizationFailedException {

    OrdersEntity ordersEntity = new OrdersEntity();

    ordersEntity.setUuid(UUID.randomUUID().toString());
    ordersEntity.setBill(orderRequest.getBill());
    ordersEntity.setDiscount(orderRequest.getDiscount());
    ordersEntity.setDate(LocalDateTime.now());

    OrderItemEntity orderItemEntity = new OrderItemEntity();
    ItemEntity itemEntity = new ItemEntity();

    for (int i = 0; i < orderRequest.getItemQuantities().size(); i++){
      orderItemEntity.setPrice(orderRequest.getItemQuantities().get(i).getPrice());
      orderItemEntity.setQuantity(orderRequest.getItemQuantities().get(i).getQuantity());
      itemEntity.setUuid(orderRequest.getItemQuantities().get(i).getItemId().toString());
    }

    OrdersEntity ordersEntity1 = ordersBusinessService.saveOrder(ordersEntity,
            orderRequest.getCouponId().toString(),
            orderRequest.getAddressId(),
            orderItemEntity,
            itemEntity,
            orderRequest.getPaymentId().toString(),
            orderRequest.getRestaurantId().toString());

    SaveOrderResponse saveOrderResponse = new SaveOrderResponse()
            .id(ordersEntity1.getUuid())
            .status("ORDER SUCCESSFULLY PLACED");


    return new ResponseEntity<SaveOrderResponse>(saveOrderResponse, HttpStatus.CREATED);
  }

}
