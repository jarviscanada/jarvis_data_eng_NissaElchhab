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
      case 2:
        dbAccessManager = new DbAccessManager("hplussports_adm",

            "hplussports_adm_password", "hplussports");
        break;
      case 3:
        dbAccessManager = new DbAccessManager(args[0], args[1], args[2]);
        break;
      case 5:
        dbAccessManager = new DbAccessManager(args[0], args[1], args[3], Integer.parseInt(args[2]),
            args[4]);
        break;
      default:
        dbAccessManager = new DbAccessManager("",
            "", "");
        logger.warn("Wrong number or type of arguments. Exiting.");
        System.out.println(
            "Usage: jdbc username password #uses hardwired defaults (will change in the future)");
        System.out.println("Usage: jdbc username password hostname port database");
        System.exit(255);
    }

    try (Connection connection = dbAccessManager.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");

    } catch (SQLException e) {

    }

  }
}