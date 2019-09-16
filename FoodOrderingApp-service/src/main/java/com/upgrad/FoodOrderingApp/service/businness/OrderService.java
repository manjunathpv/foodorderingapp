package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

  @Autowired
  private CouponDao couponDao;

  @Autowired
  private AddressDao addressDao;

  @Autowired
  private PaymentDao paymentDao;

  @Autowired
  private RestaurantDao restaurantDao;

  @Autowired
  private OrdersDao ordersDao;

  @Autowired
  private OrderItemDao orderItemDao;

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ItemService itemService;

  @Transactional(propagation = Propagation.REQUIRED)
  public OrderEntity saveOrder(OrderEntity orderEntity, final String couponId, final String addressId,
                                OrderItemEntity orderItemEntity, ItemEntity itemEntity, final String paymentId, final String restaurantId, final String authorization) throws AuthorizationFailedException,
          CouponNotFoundException, AddressNotFoundException, PaymentMethodNotFoundException, RestaurantNotFoundException, ItemNotFoundException {

    String accessToken = authorization.split("Bearer ")[1];
    CustomerEntity customerEntity = customerService.getCustomer(accessToken);

    orderEntity.setCustomerEntity(customerEntity);

    CouponEntity couponEntity = couponDao.getCouponById(couponId);
    if(couponEntity == null)
      throw new CouponNotFoundException("CPF-002", "No coupon by this id");
    orderEntity.setCouponEntity(couponEntity);

    AddressEntity addressEntity = addressDao.getAddressById(addressId);
    if(addressEntity == null)
      throw new AddressNotFoundException("ANF-003","No address by this id");

    orderEntity.setAddressEntity(addressEntity);

    PaymentEntity paymentEntity = paymentDao.getPaymentById(paymentId);
    if(paymentEntity == null)
      throw new PaymentMethodNotFoundException("PNF-002","No payment method found by this id");
    orderEntity.setPaymentEntity(paymentEntity);

    RestaurantEntity restaurantEntity = restaurantDao.getRestaurantById(restaurantId);
    if(restaurantEntity == null)
      throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
    orderEntity.setRestaurantEntity(restaurantEntity);



    ItemEntity itemEntity1 = itemService.getItemByUuid(itemEntity.getUuid());
    if(itemEntity1 == null)
      throw new ItemNotFoundException("INF-003", "No item by this id exist");
    orderItemEntity.setItemEntity(itemEntity1);

    orderItemEntity.setOrdersEntity(orderEntity);
    orderEntity.setOrderItemEntity(orderItemEntity);



    OrderEntity ordersEntity1 = ordersDao.saveOrder(orderEntity);

    saveOrderItem(orderItemEntity);

    return  ordersEntity1;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void saveOrderItem(final OrderItemEntity saveOrderItem) throws AuthorizationFailedException {
    orderItemDao.saveOrderItem(saveOrderItem);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public List<OrderEntity> getOrdersByCustomers(final CustomerEntity customerEntity) throws AuthorizationFailedException {
    List<OrderEntity> ordersEntity = ordersDao.getOrdersByCustomerUuid(customerEntity);
    return ordersEntity;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public List<OrderItemEntity> getOrderItemFromOrder(OrderEntity order) {
    List<OrderItemEntity> orderItemEntities = orderItemDao.getOrderItemByOrder(order);
    return orderItemEntities;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public CouponEntity getCouponByCouponId(String couponUuid) {
    CouponEntity couponEntity = couponDao.getCouponById(couponUuid);
    return couponEntity;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public CouponEntity getCouponByCouponName(final String couponName) throws CouponNotFoundException {

    if(couponName == null){
      throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
    }

    CouponEntity couponEntity = couponDao.getCouponByName(couponName);

    if(couponEntity == null){
      throw new CouponNotFoundException("CPF-001", "No coupon by this name");
    }

    return couponEntity;
  }
}
