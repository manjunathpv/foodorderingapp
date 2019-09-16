package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class AddressDao {

  @PersistenceContext
  private EntityManager entityManager;


  public AddressEntity createAddress(AddressEntity addressEntity) {
    entityManager.persist(addressEntity);
    return addressEntity;
  }

  public AddressEntity getAddressById(final String addressId){
    try {
      return entityManager.createNamedQuery("addressByAddressId", AddressEntity.class).setParameter("addressId", addressId).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public AddressEntity updateAddressEntity(AddressEntity addressEntity) {
    return entityManager.merge(addressEntity);
  }

  public AddressEntity deleteAddressEntity(AddressEntity addressEntity) {
    entityManager.remove(addressEntity);
    return addressEntity;
  }
}
