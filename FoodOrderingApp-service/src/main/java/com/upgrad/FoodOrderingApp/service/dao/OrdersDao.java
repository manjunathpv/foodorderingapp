package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrdersDao {

  @PersistenceContext
  private EntityManager entityManager;

  public OrdersEntity saveOrder(OrdersEntity ordersEntity) {
    entityManager.persist(ordersEntity);
    return ordersEntity;
  }

  public List<OrdersEntity> getOrdersByCustomerUuid(final CustomerEntity cust) {
    try {
      return entityManager.createNamedQuery("orderByCustUuid", OrdersEntity.class).setParameter("cust", cust).getResultList();
    } catch (NoResultException nre) {
      return null;
    }
  }
}
