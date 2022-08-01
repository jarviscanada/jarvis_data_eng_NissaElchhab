package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbAccessManager {

  private final String url;
  private final Properties loginInfo;
  private final Logger logger = LoggerFactory.getLogger(DbAccessManager.class);

  public DbAccessManager(String username, String password, String hostname, int port,
      String databaseName) {
    this.url = "jdbc:postgresql://" + hostname + ":" + port + "/" + databaseName;
    this.loginInfo = new Properties();
    this.loginInfo.setProperty("user", username);
    this.loginInfo.setProperty("password", password);
    logger.info("Initializing DbAccessManager with URL: " + this.url);
  }

  public DbAccessManager(String username, String password, String databaseName) {
    this(username, password, "localhost", 5432, databaseName);
  }


  public Connection getConnection() throws SQLException {
    logger.info("getConnection for URL: " + this.url);
    return DriverManager.getConnection(this.url, this.loginInfo);
  }

}
