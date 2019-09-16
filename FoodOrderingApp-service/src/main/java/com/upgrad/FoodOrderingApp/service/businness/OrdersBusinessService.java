package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
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

  @Transactional(propagation = Propagation.REQUIRED)
  public OrdersEntity saveOrder(OrdersEntity ordersEntity, final String couponId, final String addressId,
                                OrderItemEntity orderItemEntity, ItemEntity itemEntity, final String paymentId, final String restaurantId){

//    Need to remove the hardcoding
//    =======START=======

    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setUuid(UUID.randomUUID().toString());
    customerEntity.setFirstname("Adarsh");
    customerEntity.setLastname("Pandey");
    customerEntity.setEmail("adarsh@upgrad.com");
    customerEntity.setContactNumber("1111111113");
    customerEntity.setPassword("95070049B59AFCD5A10135A810B10BBA9FC010028AA64C6574DDE85F6DC6009DE");
    customerEntity.setSalt("asdfrtgyhdfrrfbfg5ef45r34f4ttt");

    customerDao.saveCustomer(customerEntity);

//    =======END=======



    ordersEntity.setCustomerEntity(customerEntity);

    CouponEntity couponEntity = couponDao.getCouponById(couponId);
    ordersEntity.setCouponEntity(couponEntity);

    AddressEntity addressEntity = addressDao.getAddressById(addressId);
    ordersEntity.setAddressEntity(addressEntity);

    PaymentEntity paymentEntity = paymentDao.getPaymentById(paymentId);
    ordersEntity.setPaymentEntity(paymentEntity);

    RestaurantEntity restaurantEntity = restaurantDao.getRestaurantById(restaurantId);
    ordersEntity.setRestaurantEntity(restaurantEntity);

    ItemEntity itemEntity1 = itemDao.getItemByUuid(itemEntity.getUuid());

    orderItemEntity.setItemEntity(itemEntity1);

    orderItemEntity.setOrdersEntity(ordersEntity);

    ordersEntity.setOrderItemEntity(orderItemEntity);



    OrdersEntity ordersEntity1 = ordersDao.saveOrder(ordersEntity);
    //ordersEntity1.setOrderItemEntity(orderItemEntity);
    orderItemDao.saveOrderItem(orderItemEntity);

    return  ordersEntity1;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public List<OrdersEntity> getAllPastOrders(String authorization) {

//    Not Needed as such becuase we have UUID for Customer we can directly query Orders Table
    CustomerEntity customerEntity = customerDao.getCustomerById("7d174a25-ba31-45a8-85b4-b06ffc9d5f8f");

    List<OrdersEntity> ordersEntity = ordersDao.getOrdersByCustomerUuid(1);

    return ordersEntity;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public List<OrderItemEntity> getOrderItemFromOrder(String orderUuid) {
    List<OrderItemEntity> orderItemEntities = orderItemDao.getOrderItemByOrder(orderUuid);
    return orderItemEntities;
  }
}
