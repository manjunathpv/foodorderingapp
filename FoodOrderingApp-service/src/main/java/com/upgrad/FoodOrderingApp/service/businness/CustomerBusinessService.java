package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerBusinessService {

  @Autowired
  private CustomerDao customerDao;

  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerEntity getCustomerByUuid(String customerUuid) {
    return customerDao.getCustomerByUUID(customerUuid);
  }
}
