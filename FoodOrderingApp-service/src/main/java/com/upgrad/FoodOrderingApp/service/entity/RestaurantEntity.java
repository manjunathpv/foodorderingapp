package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "restaurant")
@NamedQueries({
        @NamedQuery(name = "restaurantByRestaurantId", query = "select re from RestaurantEntity re where re.uuid =:restaurantId")

})
public class RestaurantEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "UUID")
  @Size(max = 200)
  private String uuid;

  @Column(name = "RESTAURANT_NAME")
  @Size(max = 50)
  private String restaurantName;

  @Column(name = "PHOTO_URL")
  @Size(max = 255)
  private String photoUrl;

  @Column(name = "CUSTOMER_RATING")
  @NotNull
  private BigDecimal customerRating;

  @Column(name = "AVERAGE_PRICE_FOR_TWO")
  @NotNull
  private Integer averagePriceForTwo;

  @Column(name = "NUMBER_OF_CUSTOMERS_RATED")
  @NotNull
  private Integer numberOfCustomersRated;

  @OneToOne
  @JoinColumn(name="ADDRESS_ID")
  private AddressEntity addressEntity;

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

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public BigDecimal getCustomerRating() {
    return customerRating;
  }

  public void setCustomerRating(BigDecimal customerRating) {
    this.customerRating = customerRating;
  }

  public Integer getAveragePriceForTwo() {
    return averagePriceForTwo;
  }

  public void setAveragePriceForTwo(Integer averagePriceForTwo) {
    this.averagePriceForTwo = averagePriceForTwo;
  }

  public Integer getNumberOfCustomersRated() {
    return numberOfCustomersRated;
  }

  public void setNumberOfCustomersRated(Integer numberOfCustomersRated) {
    this.numberOfCustomersRated = numberOfCustomersRated;
  }

  public AddressEntity getAddressEntity() {
    return addressEntity;
  }

  public void setAddressEntity(AddressEntity addressEntity) {
    this.addressEntity = addressEntity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RestaurantEntity that = (RestaurantEntity) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(restaurantName, that.restaurantName) &&
            Objects.equals(photoUrl, that.photoUrl) &&
            Objects.equals(customerRating, that.customerRating) &&
            Objects.equals(averagePriceForTwo, that.averagePriceForTwo) &&
            Objects.equals(numberOfCustomersRated, that.numberOfCustomersRated) &&
            Objects.equals(addressEntity, that.addressEntity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, uuid, restaurantName, photoUrl, customerRating, averagePriceForTwo, numberOfCustomersRated, addressEntity);
  }

  @Override
  public String toString() {
    return "RestaurantEntity{" +
            "id=" + id +
            ", uuid=" + uuid +
            ", restaurantName='" + restaurantName + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            ", customerRating=" + customerRating +
            ", averagePriceForTwo=" + averagePriceForTwo +
            ", numberOfCustomersRated=" + numberOfCustomersRated +
            ", addressEntity=" + addressEntity +
            '}';
  }

}
