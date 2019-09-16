package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

//OrderDao class provides the database access to all the endpoints under order controller

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* method to fetch orders based on address */
    public List<OrdersEntity> getOrdersByAddress(AddressEntity addressEntity) {
        try {
            return entityManager.createNamedQuery("ordersByAddress", OrdersEntity.class).setParameter("address", addressEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /* Method to create new order */
    public OrdersEntity createOrder(OrdersEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }

    /*Method to fetch orders based on the customer object*/
    public List<OrdersEntity> getOrdersByCustomers(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery("ordersByCustomer", OrdersEntity.class).setParameter("customer", customerEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /* Method to fetch Orders based on restaurant object */
    public List<OrdersEntity> getOrdersByRestaurant(RestaurantEntity restaurantEntity) {
        try {
            return entityManager.createNamedQuery("ordersByRestaurant", OrdersEntity.class).setParameter("restaurant", restaurantEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}