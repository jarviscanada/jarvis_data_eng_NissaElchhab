package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.data_access.DTO;

public class OrderItem implements DTO {

  private Long id;
  private Order order;
  private Product product;
  private int quantity;

  @Override
  public Long getId() {
    return null;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }


}
