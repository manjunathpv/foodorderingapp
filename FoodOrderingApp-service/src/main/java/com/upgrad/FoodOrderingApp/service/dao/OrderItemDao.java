package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class OrderItemDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void saveOrderItem(OrderItemEntity orderItemEntity) {
    entityManager.persist(orderItemEntity);
  }
}
