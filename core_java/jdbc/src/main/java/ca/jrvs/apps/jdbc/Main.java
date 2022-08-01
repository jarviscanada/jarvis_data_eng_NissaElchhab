package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Main {

  public static void main(String[] args) {
    final Logger logger = LoggerFactory.getLogger(Main.class);

    final DbAccessManager dbAccessManager;

    logger.info("core_java/jdbc app starting...");
    switch (args.length) {
      case 0:
        dbAccessManager = new DbAccessManager("hplussport_adm",
            "hplussport_adm_password", "hplussport");
        logger.warn("uses hardwired defaults (will change in the future)");
        break;
      case 2:
        dbAccessManager = new DbAccessManager(args[0], args[1], "hplussport");
        break;
      case 3:
        dbAccessManager = new DbAccessManager(args[0], args[1], args[2]);
        break;
      case 5:
        dbAccessManager = new DbAccessManager(args[0], args[1], args[3], Integer.parseInt(args[2]),
            args[4]);
        break;
      default:
        dbAccessManager = new DbAccessManager("hplussport_adm",
            "hplussport_adm_password", "hplussport");
        logger.error("Wrong number of arguments");
        System.out.println(
            "Usage: jdbc username password #");
        System.out.println("Usage: jdbc username password hostname port database");
        System.exit(255);
    } // switch

    try (Connection connection = dbAccessManager.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
      while (rs.next()) {
        System.out.printf("There are %d records\n", rs.getInt(1));
      }

    } catch (SQLException e) {
      logger.error("");
    }

  }
}