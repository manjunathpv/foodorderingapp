package com.upgrad.FoodOrderingApp.service.businness;

        import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
        import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
        import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
        import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
        import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
        import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
        import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.time.LocalDateTime;
        import java.util.Base64;

@Service
public class CouponBusinessService {

  @Autowired
  private CouponDao couponDao;

  @Autowired
  private CustomerService customerService;

  @Transactional(propagation = Propagation.REQUIRED)
  public CouponEntity getCouponByName(final String couponName, final String authorizationToken) throws AuthorizationFailedException, CouponNotFoundException {

    CouponEntity couponEntity = null;

    String accessToken = authorizationToken.split("Bearer ")[1];
    CustomerEntity customerEntity = customerService.getCustomerAuth(accessToken);

    if(couponName == null){
      throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
    }

    couponEntity = couponDao.getCouponByName(couponName);

    if(couponEntity == null){
      throw new CouponNotFoundException("CPF-001", "No coupon by this name");
    }

    return couponEntity;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public CouponEntity getCouponByUuid(String couponUuid) {
    CouponEntity couponEntity = couponDao.getCouponById(couponUuid);
    return couponEntity;
  }
}
