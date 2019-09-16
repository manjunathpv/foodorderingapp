package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrdersBusinessService {

  @Autowired
  private CouponDao couponDao;

  @Autowired
  private AddressDao addressDao;

  @Autowired
  private PaymentDao paymentDao;

  @Autowired
  private RestaurantDao restaurantDao;

  @Autowired
  private ItemDao itemDao;

  @Autowired
  private OrdersDao ordersDao;

  @Autowired
  private OrderItemDao orderItemDao;

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private CustomerService customerService;

  @Transactional(propagation = Propagation.REQUIRED)
  public OrdersEntity saveOrder(OrdersEntity ordersEntity, final String couponId, final String addressId,
                                OrderItemEntity orderItemEntity, ItemEntity itemEntity, final String paymentId, final String restaurantId, final String authorization) throws AuthorizationFailedException,
          CouponNotFoundException, AddressNotFoundException, PaymentMethodNotFoundException, RestaurantNotFoundException, ItemNotFoundException {

    String accessToken = authorization.split("Bearer ")[1];
    CustomerEntity customerEntity = customerService.getCustomer(accessToken);

    ordersEntity.setCustomerEntity(customerEntity);

    CouponEntity couponEntity = couponDao.getCouponById(couponId);
    if(couponEntity == null)
      throw new CouponNotFoundException("CPF-002", "No coupon by this id");
    ordersEntity.setCouponEntity(couponEntity);

    AddressEntity addressEntity = addressDao.getAddressById(addressId);
    if(addressEntity == null)
      throw new AddressNotFoundException("ANF-003","No address by this id");

    ordersEntity.setAddressEntity(addressEntity);

    PaymentEntity paymentEntity = paymentDao.getPaymentById(paymentId);
    if(paymentEntity == null)
      throw new PaymentMethodNotFoundException("PNF-002","No payment method found by this id");
    ordersEntity.setPaymentEntity(paymentEntity);

    RestaurantEntity restaurantEntity = restaurantDao.getRestaurantById(restaurantId);
    if(restaurantEntity == null)
      throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
    ordersEntity.setRestaurantEntity(restaurantEntity);

    ItemEntity itemEntity1 = itemDao.getItemByUuid(itemEntity.getUuid());
    if(itemEntity1 == null)
      throw new ItemNotFoundException("INF-003", "No item by this id exist");
    orderItemEntity.setItemEntity(itemEntity1);

    orderItemEntity.setOrdersEntity(ordersEntity);
    ordersEntity.setOrderItemEntity(orderItemEntity);



    OrdersEntity ordersEntity1 = ordersDao.saveOrder(ordersEntity);
    //ordersEntity1.setOrderItemEntity(orderItemEntity);
    orderItemDao.saveOrderItem(orderItemEntity);

    return  ordersEntity1;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public List<OrdersEntity> getAllPastOrders(String authorization) throws AuthorizationFailedException {

    String accessToken = authorization.split("Bearer ")[1];
    CustomerEntity customerEntity = customerService.getCustomer(accessToken);
    List<OrdersEntity> ordersEntity = ordersDao.getOrdersByCustomerUuid(customerEntity);

    return ordersEntity;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public List<OrderItemEntity> getOrderItemFromOrder(OrdersEntity order) {
    List<OrderItemEntity> orderItemEntities = orderItemDao.getOrderItemByOrder(order);
    return orderItemEntities;
  }
}
