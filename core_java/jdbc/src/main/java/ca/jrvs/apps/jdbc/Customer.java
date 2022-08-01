package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.data_access.Dto;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements Dto {

  private Long id;
  private String firstname;
  private String lastname;
  private String email;
  private String phone;
  private String address;
  private String city;
  private String state;
  private String zipcode;

  public Customer() {
  }

  public Customer(String firstname, String lastname, String email, String phone, String address,
      String city, String state, String zipcode) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
  }

  public static Customer BuildFrom(ResultSet customerResultSet) throws SQLException {
    Customer customer = new Customer();
    customer.setId(customerResultSet.getLong("customer_id"));
    customer.setFirstname(customerResultSet.getString("first_name"));
    customer.setLastname(customerResultSet.getString("last_name"));
    customer.setEmail(customerResultSet.getString("email"));
    customer.setPhone(customerResultSet.getString("phone"));
    customer.setAddress(customerResultSet.getString("address"));
    customer.setCity(customerResultSet.getString("city"));
    customer.setState(customerResultSet.getString("state"));
    customer.setZipcode(customerResultSet.getString("zipcode"));
    return customer;
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", firstname='" + firstname + '\'' +
        ", lastname='" + lastname + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", address='" + address + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zipcode='" + zipcode + '\'' +
        '}';
  }
}
