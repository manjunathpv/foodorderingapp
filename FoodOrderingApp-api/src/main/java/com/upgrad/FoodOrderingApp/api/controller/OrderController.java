package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderController {

  @Autowired
  private CouponBusinessService couponBusinessService;

  @Autowired
  private OrdersBusinessService ordersBusinessService;

  @Autowired
  private PaymentBusinessService paymentBusinessService;

  @Autowired
  private CustomerBusinessService customerBusinessService;

  @Autowired
  private AddressBusinessService addressBusinessService;

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

  @RequestMapping(method = RequestMethod.GET, path = "/order/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<OrderList>> getAllPastOrders(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException{

    final List<OrdersEntity> ordersEntityList = ordersBusinessService.getAllPastOrders(authorization);
    final List<OrderList> allPastOrdersResponseList = new ArrayList<>();

    for (OrdersEntity order: ordersEntityList) {
      String couponUuid = order.getCouponEntity().getUuid();
      CouponEntity couponEntity = couponBusinessService.getCouponByUuid(couponUuid);
      OrderListCoupon orderListCoupon = new OrderListCoupon()
              .couponName(couponEntity.getCouponName())
              .id(UUID.fromString(couponEntity.getUuid()))
              .percent(couponEntity.getPercent());

      String paymentUuid = order.getPaymentEntity().getUuid();
      PaymentEntity paymentEntity = paymentBusinessService.getPaymentByUuid(paymentUuid);
      OrderListPayment orderListPayment = new OrderListPayment()
              .id(UUID.fromString(paymentEntity.getUuid()))
              .paymentName(paymentEntity.getPaymentName());

      String customerUuid = "1c40d816-082b-4660-96a3-a3aa36fd97a0";
      CustomerEntity customerEntity = customerBusinessService.getCustomerByUuid(customerUuid);
      OrderListCustomer orderListCustomer = new OrderListCustomer()
              .id(UUID.fromString(customerEntity.getUuid()))
              .firstName(customerEntity.getFirstname())
              .lastName(customerEntity.getLastname())
              .emailAddress(customerEntity.getEmail())
              .contactNumber(customerEntity.getContactNumber());

      String addressUuid = order.getAddressEntity().getUuid();
      AddressEntity addressEntity = addressBusinessService.getAddressByUuid(addressUuid);
      OrderListAddressState orderListAddressState = new OrderListAddressState()
              .id(UUID.fromString(addressEntity.getState().getUuid()))
              .stateName(addressEntity.getState().getStateName());
      OrderListAddress orderListAddress = new OrderListAddress()
              .id(UUID.fromString(addressEntity.getUuid()))
              .flatBuildingName(addressEntity.getFlatBuilNo())
              .locality(addressEntity.getLocality())
              .city(addressEntity.getCity())
              .pincode(addressEntity.getPincode())
              .state(orderListAddressState);

//      String orderUuid = order.getUuid();
      List<OrderItemEntity> orderItemEntities = ordersBusinessService.getOrderItemFromOrder(order);
      List<ItemQuantityResponse> itemQuantityResponseList = new ArrayList<>();
      for (OrderItemEntity orderItem: orderItemEntities) {

        ItemQuantityResponseItem itemQuantityResponseItem = new ItemQuantityResponseItem()
                .id(UUID.fromString(orderItem.getItemEntity().getUuid()))
                .itemName(orderItem.getItemEntity().getItemName())
                .itemPrice(orderItem.getItemEntity().getPrice())
                .type(ItemQuantityResponseItem.TypeEnum.valueOf(orderItem.getItemEntity().getType().equals(0)?"VEG":"NON_VEG"));


        ItemQuantityResponse itemQuantityResponse = new ItemQuantityResponse().quantity(orderItem.getQuantity())
                .price(orderItem.getPrice()).item(itemQuantityResponseItem);
        itemQuantityResponseList.add(itemQuantityResponse);
      }

      OrderList orderList = new OrderList()
              .id(UUID.fromString(order.getUuid()))
              .bill(order.getBill())
              .discount(order.getDiscount())
              .date(order.getDate().toString())
              .payment(orderListPayment)
              .customer(orderListCustomer)
              .address(orderListAddress)
              .itemQuantities(itemQuantityResponseList);
      allPastOrdersResponseList.add(orderList);
    }
    return new ResponseEntity<List<OrderList>>(allPastOrdersResponseList, HttpStatus.OK);
  }
}
