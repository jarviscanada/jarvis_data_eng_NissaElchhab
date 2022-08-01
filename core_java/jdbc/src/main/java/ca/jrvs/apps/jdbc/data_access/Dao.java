package ca.jrvs.apps.jdbc.data_access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Dao<T extends Dto> {
  protected static final String CUSTOMER_SEQUENCE = "hp_customer_seq";
  protected static final String GET_LAST_SEQ_VAL = "SELECT last_value FROM ";
  protected final Connection connection;

  public Dao(Connection connection) {
    super();
    this.connection = connection;
  }

  public abstract T findById(Long id);
  public abstract List<T> findAll();
  public abstract T create(T dto);
  public abstract T update(T dto);
  public abstract void delete(Long id);

  protected Long getLastSeqVal(String sequence) {
    Long key = 0L;
    String sql = GET_LAST_SEQ_VAL+sequence;
    try(Statement stmt = connection.createStatement()) {
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        key = rs.getLong(1);
      }
      return key;
    } catch (SQLException e) {
      throw new RuntimeException("#getLastSeqVal", e);
    }
  }
}
