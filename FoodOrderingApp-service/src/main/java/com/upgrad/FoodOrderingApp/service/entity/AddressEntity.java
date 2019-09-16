package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "address")
@NamedQueries({
        @NamedQuery(name = "addressByAddressId", query = "select ae from AddressEntity ae where ae.uuid =:addressId")

})
public class AddressEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "UUID")
  @Size(max = 200)
  private String uuid;

  @Column(name = "FLAT_BUIL_NUMBER")
  @Size(max = 255)
  private String flatBuilNumber;

  @Column(name = "locality")
  @Size(max = 255)
  private String locality;

  @Column(name = "city")
  @Size(max = 30)
  private String city;

  @Column(name = "pincode")
  @Size(max = 30)
  private String pincode;

  @ManyToOne
  @JoinColumn(name="STATE_ID")
  private StateEntity stateEntity;

  @Column(name="ACTIVE")
  @NotNull
  private Integer active;

  @OneToOne(mappedBy = "addressEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private RestaurantEntity restaurantEntity;

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

  public String getFlatBuilNumber() {
    return flatBuilNumber;
  }

  public void setFlatBuilNumber(String flatBuilNumber) {
    this.flatBuilNumber = flatBuilNumber;
  }

  public String getLocality() {
    return locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }

  public StateEntity getStateEntity() {
    return stateEntity;
  }

  public void setStateEntity(StateEntity stateEntity) {
    this.stateEntity = stateEntity;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  public RestaurantEntity getRestaurantEntity() {
    return restaurantEntity;
  }

  public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
    this.restaurantEntity = restaurantEntity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddressEntity that = (AddressEntity) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(flatBuilNumber, that.flatBuilNumber) &&
            Objects.equals(locality, that.locality) &&
            Objects.equals(city, that.city) &&
            Objects.equals(pincode, that.pincode) &&
            Objects.equals(stateEntity, that.stateEntity) &&
            Objects.equals(active, that.active) &&
            Objects.equals(restaurantEntity, that.restaurantEntity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, uuid, flatBuilNumber, locality, city, pincode, stateEntity, active, restaurantEntity);
  }

  @Override
  public String toString() {
    return "AddressEntity{" +
            "id=" + id +
            ", uuid=" + uuid +
            ", flatBuilNumber='" + flatBuilNumber + '\'' +
            ", locality='" + locality + '\'' +
            ", city='" + city + '\'' +
            ", pincode='" + pincode + '\'' +
            ", stateEntity=" + stateEntity +
            ", active=" + active +
            ", restaurantEntity=" + restaurantEntity +
            '}';
  }

}
