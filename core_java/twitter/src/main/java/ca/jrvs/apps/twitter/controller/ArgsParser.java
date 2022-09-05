package ca.jrvs.apps.twitter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ArgsParser {

  private static final Logger logger = LoggerFactory.getLogger(ArgsParser.class);
  private static int nextArgPos;
  public static final String COORDX_SEPARATOR = ":";
  public final String NO_NEXT_ARGUMENT = "";
  private String[] positionalArgs;
  // TODO change to enums, Maps, and namedArguments as well
  // TODO validation constraints should be associated with the parameter/enum class

  public ArgsParser(int expectedArgc, String[] positionalArgs) {
    if (positionalArgs.length != expectedArgc) {
      throw new IllegalArgumentException(
          "Argument count:" + positionalArgs.length + " is invalid");
    }
    if (!isValid(positionalArgs)) {
      throw new IllegalArgumentException("Argument(s) invalid");
    }
    this.positionalArgs = positionalArgs;
    nextArgPos = 0;
  }

  /**
   * validates argument list
   *
   * @param argv argument vector
   * @return true if arguments are  valid
   */
  boolean isValid(String[] argv) {
    return true;
  }

  /**
   * returns next arg from the positional args
   *
   * @return
   */
  String getArg() {
    if (nextArgPos < this.positionalArgs.length) {
      logger.debug("nextArgPos: " + nextArgPos + " ||| positionalArgs: "
          + positionalArgs[nextArgPos].toString());
      return this.positionalArgs[nextArgPos++];
    } else {
      logger.warn("Reached the end of positional arguments: No Next argument.");
      return NO_NEXT_ARGUMENT;
    }
  }
}
