package ca.jrvs.apps.jdbc.data_access;

import ca.jrvs.apps.jdbc.Order;
import java.sql.Connection;
import java.util.List;

public class OrderDAO extends DAO<Order> {

  public OrderDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Order findById(Long id) {
    return null;
  }

  @Override
  public List<Order> findAll() {
    return null;
  }

  @Override
  public Order create(Order dto) {
    return null;
  }

  @Override
  public Order update(Order dto) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
