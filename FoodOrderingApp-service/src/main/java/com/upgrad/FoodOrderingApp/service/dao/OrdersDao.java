package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrdersDao {

  @PersistenceContext
  private EntityManager entityManager;

  public OrderEntity saveOrder(OrderEntity ordersEntity) {
    entityManager.persist(ordersEntity);
    return ordersEntity;
  }

  public List<OrderEntity> getOrdersByCustomerUuid(final CustomerEntity cust) {
    try {
      return entityManager.createNamedQuery("orderByCustUuid", OrderEntity.class).setParameter("cust", cust).getResultList();
    } catch (NoResultException nre) {
      return null;
    }
  }
}
