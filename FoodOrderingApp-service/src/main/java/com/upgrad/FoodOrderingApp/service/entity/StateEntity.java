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

  public StateEntity(@Size(max = 200) String uuid, @Size(max = 30) String stateName) {
    this.uuid = uuid;
    this.stateName = stateName;
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

  public String getStateName() {
    return stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

}
