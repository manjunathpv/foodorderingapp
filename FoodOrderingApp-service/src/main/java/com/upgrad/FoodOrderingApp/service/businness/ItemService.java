package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

  @Autowired
  private ItemDao itemDao;

  @Transactional(propagation = Propagation.REQUIRED)
  public ItemEntity getItemByUuid(String ItemUuid) {
    return itemDao.getItemByUuid(ItemUuid);
  }
}
