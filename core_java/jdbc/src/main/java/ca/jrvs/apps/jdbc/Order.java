package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.data_access.DTO;
import java.util.Date;

public class Order implements DTO {

  private Long id;
  private Date creationDate;
  private Number totalDue;
  private String status;
  private Customer customer;
  private Salesperson salesperson;

  @Override
  public Long getId() {
    return null;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Number getTotalDue() {
    return totalDue;
  }

  public void setTotalDue(Number totalDue) {
    this.totalDue = totalDue;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Salesperson getSalesperson() {
    return salesperson;
  }

  public void setSalesperson(Salesperson salesperson) {
    this.salesperson = salesperson;
  }
}
