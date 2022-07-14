package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.IOException;

class Main {


  public static void main(String[] args) {
    final Getopts getopts = new Getopts(3, args);
    final JavaGrepImpl javaGrep = new JavaGrepImpl();
    javaGrep.setRegex(getopts.getArg());
    javaGrep.setRootPath(getopts.getArg());
    javaGrep.setOutFile(getopts.getArg());

    try {
      javaGrep.process();
    } catch (Exception e) {
     // log
    }
  }

}
