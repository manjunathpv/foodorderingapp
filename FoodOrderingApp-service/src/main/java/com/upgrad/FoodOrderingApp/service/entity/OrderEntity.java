package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "orderByCustUuid", query = "select oe from OrderEntity oe where oe.customerEntity =:cust")
})
public class OrderEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "UUID")
  @Size(max = 200)
  private String uuid;

  @Column(name = "BILL")
  @NotNull
  private Double bill;

  @ManyToOne
  @JoinColumn(name="COUPON_ID")
  private CouponEntity couponEntity;

  @Column(name = "DISCOUNT")
  @NotNull
  private Double discount;

  @Column
  @NotNull
  private Date date;

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

  public OrderEntity(){}

  public OrderEntity(@Size(max = 200) String uuid, @NotNull Double bill, CouponEntity couponEntity, @NotNull Double discount, @NotNull Date date, @NotNull PaymentEntity paymentEntity, @NotNull CustomerEntity customerEntity, AddressEntity addressEntity, RestaurantEntity restaurantEntity) {
    super();
    this.uuid = uuid;
    this.bill = bill;
    this.couponEntity = couponEntity;
    this.discount = discount;
    this.date = date;
    this.paymentEntity = paymentEntity;
    this.customerEntity = customerEntity;
    this.addressEntity = addressEntity;
    this.restaurantEntity = restaurantEntity;
  }

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

  public Double getBill() {
    return bill;
  }

  public void setBill(Double bill) {
    this.bill = bill;
  }

  public CouponEntity getCouponEntity() {
    return couponEntity;
  }

  public void setCouponEntity(CouponEntity couponEntity) {
    this.couponEntity = couponEntity;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
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
}
