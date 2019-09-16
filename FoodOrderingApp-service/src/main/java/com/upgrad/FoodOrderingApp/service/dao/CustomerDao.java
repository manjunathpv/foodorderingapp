package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDao {

  @PersistenceContext
  private EntityManager entityManager;

  public CustomerEntity getCustomerById(final String uuid){
    try {
      return entityManager.createNamedQuery("customerByUuid", CustomerEntity.class).setParameter("uuid", uuid).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public void saveCustomer(CustomerEntity customerEntity) {
    entityManager.persist(customerEntity);
  }
}
