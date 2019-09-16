package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "payment")
@NamedQueries({
        @NamedQuery(name = "paymentByPaymentId", query = "select pe from PaymentEntity pe where pe.uuid =:paymentId"),
        @NamedQuery(name = "allPayments", query = "select pe from PaymentEntity pe")
})
public class PaymentEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "UUID")
  @Size(max = 200)
  private String uuid;

  @Column(name = "PAYMENT_NAME")
  @Size(max = 255)
  private String  paymentName;

  public PaymentEntity(){}

  public PaymentEntity(@Size(max = 200) String uuid, @Size(max = 255) String paymentName) {
    super();
    this.uuid = uuid;
    this.paymentName = paymentName;
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

  public String getPaymentName() {
    return paymentName;
  }

  public void setPaymentName(String paymentName) {
    this.paymentName = paymentName;
  }
}
