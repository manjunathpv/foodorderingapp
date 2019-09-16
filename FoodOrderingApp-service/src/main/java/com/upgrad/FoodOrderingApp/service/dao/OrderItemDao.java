package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderItemDao {

  @PersistenceContext
  private EntityManager entityManager;

  public void saveOrderItem(OrderItemEntity orderItemEntity) {
    entityManager.persist(orderItemEntity);
  }

  public List<OrderItemEntity> getItemsByOrder(OrderEntity order) {
    try {
      return entityManager.createNamedQuery("itemsByOrder", OrderItemEntity.class).setParameter("order", order).getResultList();
    } catch (NoResultException nre) {
      return null;
    }
  }
}
