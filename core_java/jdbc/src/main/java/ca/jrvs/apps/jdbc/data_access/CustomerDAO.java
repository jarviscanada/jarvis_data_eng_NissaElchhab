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

public class CustomerDAO extends DAO<Customer> {

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
  protected Logger logger = LoggerFactory.getLogger(DAO.class);

  public CustomerDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Customer findById(Long id) {
    Customer customer = new Customer();
    try (PreparedStatement stmt = this.connection.prepareStatement(FIND_ONE)) {
      stmt.setLong(1, id);
      if (stmt.execute()) {
        ResultSet rs = stmt.getResultSet();
        customer = Customer.BuildFrom(rs);
      } else {
        customer = null;
      }
    } catch (SQLException e) {
      logger.error(this.getClass().toString() + "#findById");
      throw new RuntimeException("findById", e);
    }
    return customer;
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
      logger.error(this.getClass().toString() + "#findAll");
      throw new RuntimeException("findAll", e);
    }
    return customers;
  }

  @Override
  public Customer create(Customer dto) {
    try (PreparedStatement statement = this.connection.prepareStatement(CREATE)) {
      statement.setString(1, dto.getFirstname());
      statement.setString(2, dto.getLastname());
      statement.setString(3, dto.getEmail());
      statement.setString(4, dto.getPhone());
      statement.setString(5, dto.getAddress());
      statement.setString(6, dto.getCity());
      statement.setString(7, dto.getState());
      statement.setString(8, dto.getZipcode());
      //Alternative
      // ResultSet rs = statement.executeQuery();
      //return Customer.BuildFrom(rs);
      statement.execute();
      Long id = this.getLastSeqVal(CUSTOMER_SEQUENCE);
      return this.findById(id);
    } catch (SQLException e) {
      logger.error(this.getClass().toString() + "#create");
      throw new RuntimeException("create", e);
    }
  }

  @Override
  public Customer update(Customer dto) {
    try (PreparedStatement stmt = this.connection.prepareStatement(UPDATE)) {
      stmt.setString(1, dto.getFirstname());
      stmt.setString(2, dto.getLastname());
      stmt.setString(3, dto.getEmail());
      stmt.setString(4, dto.getPhone());
      stmt.setString(5, dto.getAddress());
      stmt.setString(6, dto.getCity());
      stmt.setString(7, dto.getState());
      stmt.setString(8, dto.getZipcode());
      stmt.setLong(9, dto.getId());
      stmt.execute();
      return this.findById(dto.getId());
    } catch (SQLException e) {
      logger.error(this.getClass().toString() + "#update");
      throw new RuntimeException("update", e);
    }
  }

  @Override
  public void delete(Long id) {
    try (PreparedStatement stmt = this.connection.prepareStatement(DELETE)) {
      stmt.setLong(1, id);
      stmt.execute();
    } catch (SQLException e) {
      logger.error(this.getClass().toString() + "#delete");
      throw new RuntimeException("delete", e);
    }
  }


}
