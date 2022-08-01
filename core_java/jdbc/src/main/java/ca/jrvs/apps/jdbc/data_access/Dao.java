package ca.jrvs.apps.jdbc.data_access;

import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Dao<T extends Dto> {
  protected static final String CUSTOMER_SEQUENCE = "hp_customer_seq";
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

}
