package ca.jrvs.apps.jdbc.data_access;

import java.sql.Connection;
import java.util.List;

public abstract class Dao<T extends Dto> {
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
