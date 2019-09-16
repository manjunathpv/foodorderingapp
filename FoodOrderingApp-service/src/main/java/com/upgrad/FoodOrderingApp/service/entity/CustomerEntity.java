package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "customer")
@NamedQueries({
        @NamedQuery(name = "customerByUuid", query = "select ce from CustomerEntity ce where ce.uuid =:uuid")
})
public class CustomerEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Integer id;

  @Column(name = "UUID")
  @Size(max = 200)
  private String uuid;

  @Column(name = "FIRSTNAME")
  @NotNull
  @Size(max = 30)
  private String firstname;

  @Column(name = "LASTNAME")
  @NotNull
  @Size(max = 30)
  private String lastname;

  @Column(name = "EMAIL")
  @NotNull
  @Size(max = 50)
  private String email;

  @Column(name = "CONTACT_NUMBER")
  @NotNull
  @Size(max = 30)
  private String contactNumber;

  @Column(name = "PASSWORD")
  @NotNull
  @Size(max = 255)
  private String password;

  @Column(name = "SALT")
  @NotNull
  @Size(max = 255)
  //@ToStringExclude
  private String salt;


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

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CustomerEntity that = (CustomerEntity) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(email, that.email) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(password, that.password) &&
            Objects.equals(salt, that.salt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, uuid, firstname, lastname, email, contactNumber, password, salt);
  }

  @Override
  public String toString() {
    return "CustomerEntity{" +
            "id=" + id +
            ", uuid=" + uuid +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", email='" + email + '\'' +
            ", contactNumber='" + contactNumber + '\'' +
            ", password='" + password + '\'' +
            ", salt='" + salt + '\'' +
            '}';
  }
}
