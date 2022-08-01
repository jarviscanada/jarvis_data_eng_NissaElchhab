package ca.jrvs.apps.jdbc.data_access;

import ca.jrvs.apps.jdbc.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDao extends Dao<Customer> {

  protected Logger logger = LoggerFactory.getLogger(Dao.class);

  private final static String FIND_ALL = "SELECT "
      + "(customer_id, first_name, last_name, email, phone,address, city, state, zipcode) "
      + "FROM customer";
  private final static String FIND_ONE = "SELECT "
      + "(first_name, last_name, email, phone,address, city, state, zipcode) "
      + "FROM customer WHERE customer_id=?";
  private final static String CREATE = "INSERT INTO "
      + "customer (first_name, last_name, email, phone,address, city, state, zipcode) "
      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
  private final static String UPDATE = "UPDATE customer "
      + "SET first_name=?, last_name=?, email=?, phone=?,address=?, city=?, state=?, zipcode=? "
      + "WHERE customer_id=?";
  private final static String DELETE = "DELETE FROM customer WHERE customer_id=?";

  public CustomerDao(Connection connection) {
    super(connection);
  }

  @Override
  public Customer findById(Long id) {
    Customer customer = new Customer();
    try (PreparedStatement stmt = connection.prepareStatement(FIND_ONE)) {
      stmt.setLong(1, id);
      if (stmt.execute()) {
        ResultSet rs = stmt.getResultSet();
        customer = Customer.BuildFrom(rs);
      } else {
        customer = null;
        return  customer;
      }

    } catch (SQLException e) {
      // log

    }
  }

  @Override
  public List<Customer> findAll() {
    List<Customer> customers = new ArrayList<>();
    try (PreparedStatement stmt = this.connection.prepareStatement(FIND_ALL)) {
      stmt.executeQuery();
      ResultSet rs = stmt.getResultSet();
      if (rs == null) {
        return customers;
      }
      while (rs.next()) {
        logger.debug(rs.toString() + "\n");
        Customer customer = new Customer(); //TODO might be optimized to move this outside of while
        customer = Customer.BuildFrom(rs);
        customers.add(customer);
      }
    } catch (SQLException e) {
      logger.error("CustomerDao#findAll");
      throw new RuntimeException("CustomerDao#findAll", e);
    }
    return customers;
  }

  @Override
  public Customer create(Customer dto) {
    try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
      statement.setString(1, dto.getFirstname());
      statement.setString(2, dto.getLastname());
      statement.setString(3, dto.getEmail());
      statement.setString(4, dto.getPhone());
      statement.setString(5, dto.getAddress());
      statement.setString(6, dto.getCity());
      statement.setString(7, dto.getState());
      statement.setString(8, dto.getZipcode());
      if (statement.execute()) {
        Long id = statement.getResultSet().getLong(1);
        dto.setId(id);
        logger.debug("getResult column index 1: " + id);
        logger.debug(dto.toString());
        return dto;
      } else {
        // log statement.getLargeUpdateCount())
        return null;
      }
    } catch (SQLException e) {
      // log
      throw new RuntimeException("Customer::create error", e);
    }
  }

  @Override
  public Customer update(Customer dto) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }


}
