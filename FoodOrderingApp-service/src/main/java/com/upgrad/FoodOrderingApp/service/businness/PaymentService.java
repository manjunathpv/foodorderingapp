package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

  @Autowired
  private PaymentDao paymentDao;

  @Transactional(propagation = Propagation.REQUIRED)
  public PaymentEntity getPaymentByUUID(String paymentUuid){
    return paymentDao.getPaymentById(paymentUuid);
  }


  public List<PaymentEntity> getAllAvailablePaymentMethods() {
    return paymentDao.getAllAvailablePaymentMethods();
  }
}
