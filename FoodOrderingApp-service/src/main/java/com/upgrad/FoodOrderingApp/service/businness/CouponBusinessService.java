package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CouponBusinessService {

  @Autowired
  private CouponDao couponDao;

  @Transactional(propagation = Propagation.REQUIRED)
  public CouponEntity getCouponByName(final String couponName, final String authorizationToken) throws AuthorizationFailedException, CouponNotFoundException {

    CouponEntity couponEntity = null;
    String customerAuthEntity = null;
//    userDao.getUserByAccessToken(authorizationToken);

    // Validate if customer is signed in or not using access token
//    if (customerAuthEntity == null) {
//      throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
//    }

    // Validate if customer has signed out using access token
//    if (customerAuthEntity.getLogoutAt() != null) {
//      throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
//    }

    // Validate if customer's session has timed-out
//    if(customerAuthEntity.getExpiresAt() < LocalDateTime.now()){
//      throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
//    }

    if(couponName == null){
      throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
    }

    couponEntity = couponDao.getCouponByName(couponName);

    if(couponEntity == null){
      throw new CouponNotFoundException("CPF-001", "No coupon by this name");
    }

    return couponEntity;
  }

}
