package ca.jrvs.apps.jdbc.data_access;

import ca.jrvs.apps.jdbc.Order;
import java.sql.Connection;
import java.util.List;

public class OrderDAO extends DAO<Order> {

  private static final String FIND_ONE = "SELECT "
      + "c.first_name, c.last_name, c.email, o.order_id, "
      + "o.creation_date, o.total_due, o.status, "
      + "s.first_name, s.last_name, s.email, "
      + "ol.quantity, "
      + "p.code, p.name, p.size, p.variety, p.price "
      + "from orders o "
      + "join customer c on o.customer_id = c.customer_id "
      + "join salesperson s on o.salesperson_id=s.salesperson_id "
      + "join order_item ol on ol.order_id=o.order_id "
      + "join product p on ol.product_id = p.product_id "
      + "where o.order_id = ?";

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
