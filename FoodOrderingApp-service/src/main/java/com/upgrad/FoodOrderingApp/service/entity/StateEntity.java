package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "state")
public class StateEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "UUID")
  @Size(max = 200)
  private String uuid;

  @Column(name = "STATE_NAME")
  @Size(max = 30)
  private String stateName;

  @OneToOne(mappedBy = "stateEntity")
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

  public String getStateName() {
    return stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
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
    StateEntity that = (StateEntity) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(stateName, that.stateName) &&
            Objects.equals(addressEntity, that.addressEntity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, uuid, stateName, addressEntity);
  }

  @Override
  public String toString() {
    return "StateEntity{" +
            "id=" + id +
            ", uuid=" + uuid +
            ", stateName='" + stateName + '\'' +
            ", addressEntity=" + addressEntity +
            '}';
  }
}
