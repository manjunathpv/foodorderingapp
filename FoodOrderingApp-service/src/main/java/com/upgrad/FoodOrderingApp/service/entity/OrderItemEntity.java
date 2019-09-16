package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * OrderItemEntity class contains all the attributes to be mapped to all the fields in 'order_item' table in the database
 */
@Entity
@Table(name = "order_item")
@NamedQueries({
        @NamedQuery(name = "itemsByOrder", query = "select q from OrderItemEntity q where q.orderEntity = :orderEntity"),
})
public class OrderItemEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name="ORDER_ID")
  @NotNull
  private OrderEntity orderEntity;

  @ManyToOne
  @JoinColumn(name = "ITEM_ID")
  @NotNull
  private ItemEntity itemEntity;

  @Column(name = "QUANTITY")
  @NotNull
  private Integer quantity;

  @Column(name = "PRICE")
  @NotNull
  private Integer price;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public OrderEntity getOrdersEntity() {
    return orderEntity;
  }

  public void setOrdersEntity(OrderEntity ordersEntity) {
    this.orderEntity = ordersEntity;
  }

  public ItemEntity getItemEntity() {
    return itemEntity;
  }

  public void setItemEntity(ItemEntity itemEntity) {
    this.itemEntity = itemEntity;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }
}
