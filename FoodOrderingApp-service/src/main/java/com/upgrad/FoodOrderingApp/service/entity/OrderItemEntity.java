package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.criterion.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "order_item")
@NamedQueries({
        @NamedQuery(name = "orderItemByOrder", query = "select oie from OrderItemEntity oie where oie.ordersEntity =:order")
})
public class OrderItemEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name="ORDER_ID")
  private OrdersEntity ordersEntity;

  @OneToOne
  @JoinColumn(name = "ITEM_ID")
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

  public OrdersEntity getOrdersEntity() {
    return ordersEntity;
  }

  public void setOrdersEntity(OrdersEntity ordersEntity) {
    this.ordersEntity = ordersEntity;
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
