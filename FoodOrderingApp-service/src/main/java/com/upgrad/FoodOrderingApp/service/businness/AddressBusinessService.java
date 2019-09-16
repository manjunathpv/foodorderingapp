package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressBusinessService {

  @Autowired
  private AddressDao addressDao;

  @Transactional(propagation = Propagation.REQUIRED)
  public AddressEntity getAddressByUuid(String addressUuid) {
    return addressDao.getAddressById(addressUuid);
  }
}
