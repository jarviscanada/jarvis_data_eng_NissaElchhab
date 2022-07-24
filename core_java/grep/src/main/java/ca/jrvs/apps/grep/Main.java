package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    final Getopts getopts = new Getopts(3, args);
    final JavaGrepStream javaGrep = new JavaGrepStreamImpl();
    javaGrep.setRegex(getopts.getArg());
    javaGrep.setRootPath(getopts.getArg());
    javaGrep.setOutFile(getopts.getArg());

    try {
      javaGrep.process();
    } catch (Exception e) {
      logger.error("JavaGrep exception: " + " toString: " + javaGrep.toString());
    }
  }

}
