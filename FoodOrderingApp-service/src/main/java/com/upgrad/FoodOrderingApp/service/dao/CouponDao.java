package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class CouponDao {

  @PersistenceContext
  private EntityManager entityManager;

  public CouponEntity getCouponByName(final String couponName){
    try {
      return entityManager.createNamedQuery("couponBycouponName", CouponEntity.class).setParameter("couponName", couponName).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public CouponEntity getCouponById(final String couponId){
    try {
      return entityManager.createNamedQuery("couponBycouponId", CouponEntity.class).setParameter("couponId", couponId).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }
}
