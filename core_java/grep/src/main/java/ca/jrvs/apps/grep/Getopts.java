package ca.jrvs.apps.grep;

import java.util.Collections;
import java.util.List;
import java.util.Map;

class Getopts {

  private String[] positionalArgs;
  private static int nextArgPos;
  public final String NO_NEXT_ARGUMENT = "";
  // TODO change to enums, Maps, and namedArguments as well
  // TODO validation constraints should be associated with the parameter/enum class

  public Getopts(int expectedArgc, String[] positionalArgs) {
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
      ++nextArgPos;
      return this.positionalArgs[nextArgPos];
    } else {
      // log
      return NO_NEXT_ARGUMENT;
    }
  }
}
