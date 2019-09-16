package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.common.DateConvertor;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderController {

  @Autowired
  private OrderService couponBusinessService;

  @Autowired
  private OrderService orderService;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private AddressService addressService;

  @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{couponName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CouponDetailsResponse> getCouponByName(@PathVariable("couponName") final String couponName, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, CouponNotFoundException {

    String accessToken = authorization.split("Bearer ")[1];
    CustomerEntity customerEntity = customerService.getCustomer(accessToken);

    final CouponEntity singleCoupon = orderService.getCouponByCouponName(couponName);

    CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse()
            .couponName(singleCoupon.getCouponName())
            .id(UUID.fromString(singleCoupon.getUuid()))
            .percent(singleCoupon.getPercent());

    return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SaveOrderResponse> saveOrder (@RequestHeader("authorization") final String authorization, final SaveOrderRequest orderRequest) throws AuthorizationFailedException,
          CouponNotFoundException, RestaurantNotFoundException, AddressNotFoundException, PaymentMethodNotFoundException, ItemNotFoundException {

    OrderEntity ordersEntity = new OrderEntity();

    ordersEntity.setUuid(UUID.randomUUID().toString());
    ordersEntity.setBill(orderRequest.getBill().doubleValue());
    ordersEntity.setDiscount(orderRequest.getDiscount().doubleValue());
    ordersEntity.setDate(new DateConvertor().convertLocalDateTimeToDate());

    OrderItemEntity orderItemEntity = new OrderItemEntity();
    ItemEntity itemEntity = new ItemEntity();

    for (int i = 0; i < orderRequest.getItemQuantities().size(); i++){
      orderItemEntity.setPrice(orderRequest.getItemQuantities().get(i).getPrice());
      orderItemEntity.setQuantity(orderRequest.getItemQuantities().get(i).getQuantity());
      itemEntity.setUuid(orderRequest.getItemQuantities().get(i).getItemId().toString());
    }

    OrderEntity ordersEntity1 = orderService.saveOrder(ordersEntity,
            orderRequest.getCouponId().toString(),
            orderRequest.getAddressId(),
            orderItemEntity,
            itemEntity,
            orderRequest.getPaymentId().toString(),
            orderRequest.getRestaurantId().toString(), authorization);

    SaveOrderResponse saveOrderResponse = new SaveOrderResponse()
            .id(ordersEntity1.getUuid())
            .status("ORDER SUCCESSFULLY PLACED");


    return new ResponseEntity<SaveOrderResponse>(saveOrderResponse, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/order/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<OrderList>> getAllPastOrders(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException{

    String accessToken = authorization.split("Bearer ")[1];
    CustomerEntity customerEntity = customerService.getCustomer(accessToken);

    final List<OrderEntity> ordersEntityList = orderService.getOrdersByCustomers(customerEntity);
    final List<OrderList> allPastOrdersResponseList = new ArrayList<>();

    for (OrderEntity order: ordersEntityList) {
      String couponUuid = order.getCouponEntity().getUuid();
      CouponEntity couponEntity = orderService.getCouponByCouponId(couponUuid);
      OrderListCoupon orderListCoupon = new OrderListCoupon()
              .couponName(couponEntity.getCouponName())
              .id(UUID.fromString(couponEntity.getUuid()))
              .percent(couponEntity.getPercent());

      String paymentUuid = order.getPaymentEntity().getUuid();
      PaymentEntity paymentEntity = paymentService.getPaymentByUUID(paymentUuid);
      OrderListPayment orderListPayment = new OrderListPayment()
              .id(UUID.fromString(paymentEntity.getUuid()))
              .paymentName(paymentEntity.getPaymentName());

      OrderListCustomer orderListCustomer = new OrderListCustomer()
              .id(UUID.fromString(customerEntity.getUuid()))
              .firstName(customerEntity.getFirstname())
              .lastName(customerEntity.getLastname())
              .emailAddress(customerEntity.getEmail())
              .contactNumber(customerEntity.getContactNumber());

      String addressUuid = order.getAddressEntity().getUuid();
      AddressEntity addressEntity = addressService.getAddressByUuid(addressUuid);
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
      List<OrderItemEntity> orderItemEntities = orderService.getOrderItemFromOrder(order);
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
              .bill(BigDecimal.valueOf(order.getBill()))
              .discount(BigDecimal.valueOf(order.getDiscount()))
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
