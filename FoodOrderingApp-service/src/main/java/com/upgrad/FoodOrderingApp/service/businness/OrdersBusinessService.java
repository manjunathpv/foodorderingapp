package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public OrdersEntity saveOrder(OrdersEntity ordersEntity, final String couponId, final String addressId,
                                OrderItemEntity orderItemEntity, ItemEntity itemEntity, final String paymentId, final String restaurantId){

//    Need to remove the hardcoding
//    =======START=======

    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setUuid("7d174a25-ba31-45a8-85b4-b06ffc9d5f8f");
    customerEntity.setFirstname("Upgrad");
    customerEntity.setLastname("SDE");
    customerEntity.setEmail("sde@upgrad.com");
    customerEntity.setContactNumber("1111111111");
    customerEntity.setPassword("95070049B59AFCD5A10135A810B10BBA9FC010028AA64C6574DDE85F6DC6008D");
    customerEntity.setSalt("asdfrtgyhdfrrfbfg5ef45r34f4t");

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

    orderItemDao.saveOrderItem(orderItemEntity);

    OrdersEntity ordersEntity1 = ordersDao.saveOrder(ordersEntity);
    ordersEntity1.setOrderItemEntity(orderItemEntity);

    return  ordersEntity1;
  }
}
