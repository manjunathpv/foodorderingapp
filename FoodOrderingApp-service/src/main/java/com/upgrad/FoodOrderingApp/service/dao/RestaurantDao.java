package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class RestaurantDao {

  @PersistenceContext
  private EntityManager entityManager;

  public RestaurantEntity getRestaurantById(final String restaurantId){
    try {
      return entityManager.createNamedQuery("restaurantByRestaurantId", RestaurantEntity.class).setParameter("restaurantId", restaurantId).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }
}
