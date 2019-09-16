package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "coupon")
@NamedQueries({
        @NamedQuery(name = "couponBycouponName", query = "select c from CouponEntity c where c.couponName =:couponName"),
        @NamedQuery(name = "couponBycouponId", query = "select c from CouponEntity c where c.uuid =:couponId")

})
public class CouponEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "UUID")
  @Size(max = 200)
  private String uuid;

  @Column(name = "COUPON_NAME")
  @NotNull
  @Size(max = 500)
  private String couponName;

  @Column(name = "PERCENT")
  @NotNull
  private Integer percent;

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

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public Integer getPercent() {
    return percent;
  }

  public void setPercent(Integer percent) {
    this.percent = percent;
  }
}
