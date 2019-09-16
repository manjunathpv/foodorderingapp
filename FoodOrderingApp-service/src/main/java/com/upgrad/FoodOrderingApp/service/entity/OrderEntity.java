package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "orderByCustUuid", query = "select oe from OrdersEntity oe where oe.customerEntity =:cust")
})
public class OrdersEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "UUID")
  @Size(max = 200)
  private String uuid;

  @Column(name = "BILL")
  @NotNull
  private BigDecimal bill;

  @ManyToOne
  @JoinColumn(name="COUPON_ID")
  private CouponEntity couponEntity;

  @Column(name = "DISCOUNT")
  @NotNull
  private BigDecimal discount;

  @Column
  @NotNull
  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "PAYMENT_ID")
  @NotNull
  private PaymentEntity paymentEntity;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID")
  @NotNull
  private CustomerEntity customerEntity;

  @ManyToOne(cascade=CascadeType.ALL)
  @JoinColumn(name = "ADDRESS_ID")
  private AddressEntity addressEntity;

  @ManyToOne
  @JoinColumn(name = "RESTAURANT_ID")
  private RestaurantEntity restaurantEntity;

  @OneToOne(mappedBy = "ordersEntity",cascade = CascadeType.ALL, orphanRemoval = true)
  private OrderItemEntity orderItemEntity;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public BigDecimal getBill() {
    return bill;
  }

  public void setBill(BigDecimal bill) {
    this.bill = bill;
  }

  public CouponEntity getCouponEntity() {
    return couponEntity;
  }

  public void setCouponEntity(CouponEntity couponEntity) {
    this.couponEntity = couponEntity;
  }

  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public PaymentEntity getPaymentEntity() {
    return paymentEntity;
  }

  public void setPaymentEntity(PaymentEntity paymentEntity) {
    this.paymentEntity = paymentEntity;
  }

  public CustomerEntity getCustomerEntity() {
    return customerEntity;
  }

  public void setCustomerEntity(CustomerEntity customerEntity) {
    this.customerEntity = customerEntity;
  }

  public AddressEntity getAddressEntity() {
    return addressEntity;
  }

  public void setAddressEntity(AddressEntity addressEntity) {
    this.addressEntity = addressEntity;
  }

  public RestaurantEntity getRestaurantEntity() {
    return restaurantEntity;
  }

  public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
    this.restaurantEntity = restaurantEntity;
  }

  public OrderItemEntity getOrderItemEntity() {
    return orderItemEntity;
  }

  public void setOrderItemEntity(OrderItemEntity orderItemEntity) {
    this.orderItemEntity = orderItemEntity;
  }
}
